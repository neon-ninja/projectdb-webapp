package pm.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

import pm.db.ProjectDao;
import pm.pojo.Adviser;
import pm.util.AffiliationUtil;

public class CreateAdviserController extends SimpleFormController {

	private Log log = LogFactory.getLog(CreateAdviserController.class.getName()); 
	private ProjectDao projectDao;
	private String profileDefaultPicture;
	private AffiliationUtil affiliationUtil;
	
	@Override
	public ModelAndView onSubmit(Object o) throws Exception {
		Adviser a = (Adviser) o;
		String affiliationString = a.getInstitution();
		a.setInstitution(this.affiliationUtil.getInstitutionFromAffiliationString(affiliationString));
		a.setDivision(this.affiliationUtil.getDivisionFromAffiliationString(affiliationString));
		a.setDepartment(this.affiliationUtil.getDepartmentFromAffiliationString(affiliationString));
        a.setId(this.projectDao.createAdviser(a));
    	ModelAndView mav = new ModelAndView(super.getSuccessView());
		mav.addObject("adviser", a);
		return mav;
	}
	
	@Override
	protected Object formBackingObject(HttpServletRequest request) throws Exception {
		Adviser a = new Adviser();
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		a.setStartDate(df.format(new Date()));
		a.setPictureUrl(this.profileDefaultPicture);
		return a;
	}

	@Override
    protected Map referenceData(HttpServletRequest request) throws Exception {
		ModelMap modelMap = new ModelMap();
		modelMap.put("affiliations", this.affiliationUtil.getAffiliationStrings());
		return modelMap;
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
