package pm.controller;

import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

import pm.db.ProjectDao;
import pm.pojo.Adviser;
import pm.util.AffiliationUtil;

public class EditAdviserController extends SimpleFormController {
	
	private static Log log = LogFactory.getLog(Thread.currentThread().getClass()); 
	private ProjectDao projectDao;
	private String proxy;
	private AffiliationUtil affiliationUtil;

	@Override
	public ModelAndView onSubmit(Object o) throws Exception {
		Adviser a = (Adviser) o;
		String valid = isAdviserValid(a);
		if (valid.equals("true")) {
			String affiliationString = a.getInstitution();
			a.setInstitution(this.affiliationUtil.getInstitutionFromAffiliationString(affiliationString));
			a.setDivision(this.affiliationUtil.getDivisionFromAffiliationString(affiliationString));
			a.setDepartment(this.affiliationUtil.getDepartmentFromAffiliationString(affiliationString));
	        this.projectDao.updateAdviser(a);
	    	ModelAndView mav = new ModelAndView(super.getSuccessView());
			mav.setViewName("redirect");
			mav.addObject("pathAndQuerystring", "viewadviser?id=" + a.getId());
			mav.addObject("proxy", this.proxy);
			return mav;
		} else {
			ModelAndView mav = new ModelAndView();
			mav.addObject("adviser",a);
			mav.addObject("error",valid);
			mav.getModelMap().put("affiliations", this.affiliationUtil.getAffiliationStrings());
			return mav;
		}
	}
	
	private String isAdviserValid(Adviser a) throws Exception {
		if (a.getFullName().trim().equals("")) {
			return "Adviser name cannot be empty";
		}
		return "true";
	}
	
	@Override
	protected Object formBackingObject(HttpServletRequest request) throws Exception {
		Integer id = Integer.valueOf(request.getParameter("id"));
		Adviser a = this.projectDao.getAdviserById(id);
  		a.setInstitution(affiliationUtil.createAffiliationString(
  		    a.getInstitution(), a.getDivision(), a.getDepartment()));
		return a;
	}
	
	@Override
    protected Map<String,Object> referenceData(HttpServletRequest request) throws Exception {
		ModelMap modelMap = new ModelMap();
		modelMap.put("affiliations", this.affiliationUtil.getAffiliationStrings());
		return modelMap;
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
