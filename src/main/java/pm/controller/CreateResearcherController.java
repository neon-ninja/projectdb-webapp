package pm.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
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
import pm.pojo.Adviser;
import pm.pojo.InstitutionalRole;
import pm.pojo.Researcher;
import pm.util.AffiliationUtil;

public class CreateResearcherController extends SimpleFormController {

	private Log log = LogFactory.getLog(CreateResearcherController.class.getName()); 
	private ProjectDao projectDao;
	private String profileDefaultPicture;
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
			Integer id = this.projectDao.createResearcher(r);
			r = this.projectDao.getResearcherById(id);
	    	ModelAndView mav = new ModelAndView(super.getSuccessView());
			mav.addObject("researcher", r);
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
		for (Researcher other:projectDao.getResearchers()) {
			if (r.getFullName().equals(other.getFullName())) {
				return r.getFullName() + " already exists in the database";
			}
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
        modelMap.put("institutionalRoles", iRoles);
		modelMap.put("affiliations", this.affiliationUtil.getAffiliationStrings());
        return modelMap;
	}
	
	@Override
	protected Object formBackingObject(HttpServletRequest request) throws Exception {
		Researcher r = new Researcher();
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		r.setStartDate(df.format(new Date()));
		r.setPictureUrl(this.profileDefaultPicture);
		return r;
	}

	public void setProjectDao(ProjectDao projectDao) {
		this.projectDao = projectDao;
	}

	public void setProfileDefaultPicture(String profileDefaultPicture) {
		this.profileDefaultPicture = profileDefaultPicture;
	}
	
	public void setAffiliationUtil(AffiliationUtil affiliationUtil) {
		this.affiliationUtil = affiliationUtil;
	}
	
}
