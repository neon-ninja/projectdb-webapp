package pm.controller;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

import pm.db.ProjectDao;
import pm.pojo.InstitutionalRole;
import pm.pojo.Researcher;
import pm.util.AffiliationUtil;

public class EditResearcherController extends SimpleFormController {
	
	private Log log = LogFactory.getLog(EditResearcherController.class.getName()); 
	private ProjectDao projectDao;
	private String proxy;
	private AffiliationUtil affiliationUtil;

	@Override
	public ModelAndView onSubmit(Object o) throws Exception {
		Researcher r = (Researcher) o;
		String valid = isResearcherValid(r);
		if (valid.equals("true")) {
			String affiliationString = r.getInstitution();
			r.setInstitution(this.affiliationUtil.getInstitutionFromAffiliationString(affiliationString));
			r.setDivision(this.affiliationUtil.getDivisionFromAffiliationString(affiliationString));
			r.setDepartment(this.affiliationUtil.getDepartmentFromAffiliationString(affiliationString));
	        this.projectDao.updateResearcher(r);
	    	ModelAndView mav = new ModelAndView(super.getSuccessView());
			mav.setViewName("redirect");
			mav.addObject("pathAndQuerystring", "viewresearcher?id=" + r.getId());
			mav.addObject("proxy", this.proxy);
			return mav;
		} else {
			ModelAndView mav = new ModelAndView();
			mav.addObject("researcher", r);
			mav.addObject("error", valid);
			List<InstitutionalRole> iRolesTmp = this.projectDao.getInstitutionalRoles();
			HashMap<Integer,String> iRoles = new LinkedHashMap<Integer, String>();
			if (iRolesTmp != null) {
				for (InstitutionalRole ir: iRolesTmp) {
					iRoles.put(ir.getId(), ir.getName());
				}
			}
			mav.getModelMap().put("institutionalRoles", iRoles);
			mav.getModelMap().put("affiliations", this.affiliationUtil.getAffiliationStrings());
			return mav;
		}
	}
	
	private String isResearcherValid(Researcher r) throws Exception {
		if (r.getFullName().trim().equals("")) {
			return "Researcher name cannot be empty";
		}
		return "true";
	}
	
	@Override
    protected Map<String,Object> referenceData(HttpServletRequest request) throws Exception {
		ModelMap modelMap = new ModelMap();
		List<InstitutionalRole> iRolesTmp = this.projectDao.getInstitutionalRoles();
		HashMap<Integer,String> iRoles = new LinkedHashMap<Integer, String>();
		if (iRolesTmp != null) {
			for (InstitutionalRole ir: iRolesTmp) {
				iRoles.put(ir.getId(), ir.getName());
			}
		}
		modelMap.put("affiliations", this.affiliationUtil.getAffiliationStrings());
		modelMap.put("institutionalRoles", iRoles);
        return modelMap;
	}

	@Override
	protected Object formBackingObject(HttpServletRequest request) throws Exception {
		Integer id = Integer.valueOf(request.getParameter("id"));
  		Researcher r = this.projectDao.getResearcherById(id);
		r.setInstitution(affiliationUtil.createAffiliationString(
	        r.getInstitution(), r.getDivision(), r.getDepartment()));
  		return r;
	}
	
	public void setProjectDao(ProjectDao projectDao) {
		this.projectDao = projectDao;
	}

	public void setProxy(String proxy) {
		this.proxy = proxy;
	}

	public void setAffiliationUtil(AffiliationUtil affiliationUtil) {
		this.affiliationUtil = affiliationUtil;
	}
	
}
