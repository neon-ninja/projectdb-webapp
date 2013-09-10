package pm.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

import pm.db.ProjectDao;
import pm.pojo.Adviser;

public class CreateAdviserController extends SimpleFormController {

	private Log log = LogFactory.getLog(CreateAdviserController.class.getName()); 
	private ProjectDao projectDao;
	private String profileDefaultPicture;
	
	@Override
	public ModelAndView onSubmit(Object a) throws Exception {
		Adviser adviser = (Adviser) a;
    	ModelAndView mav = new ModelAndView(super.getSuccessView());
        adviser.setId(this.projectDao.createAdviser(adviser));
		mav.addObject("adviser", adviser);
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

	public void setProjectDao(ProjectDao projectDao) {
		this.projectDao = projectDao;
	}

	public void setProfileDefaultPicture(String profileDefaultPicture) {
		this.profileDefaultPicture = profileDefaultPicture;
	}
	
}
