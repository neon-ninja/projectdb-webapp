package pm.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

import pm.db.ProjectDao;
import pm.pojo.Advisor;

public class CreateAdvisorController extends SimpleFormController {

	private Log log = LogFactory.getLog(CreateAdvisorController.class.getName()); 
	private ProjectDao projectDao;
	private String profileDefaultPicture;
	
	@Override
	public ModelAndView onSubmit(Object a) throws ServletException {
		Advisor advisor = (Advisor) a;
    	ModelAndView mav = new ModelAndView(super.getSuccessView());
		try {
            advisor.setId(this.projectDao.createAdvisor(advisor));
		} catch (Exception e) {
        	throw new ServletException(e);
        }
		mav.addObject("advisor", advisor);
		return mav;
	}
	
	@Override
	protected Object formBackingObject(HttpServletRequest request) throws ServletException {
		Advisor a = new Advisor();
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		a.setStartDate(df.format(new Date()));
		a.setPictureUrl(this.profileDefaultPicture);
		return a;
	}

	public void setProjectDao(ProjectDao projectDao) {
		this.projectDao = projectDao;
	}

	public void setProfileDefaultPicture(String profileDefaultPicture) {
		this.profileDefaultPicture = profileDefaultPicture;
	}
	
}
