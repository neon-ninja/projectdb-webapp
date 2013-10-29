package pm.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import pm.db.ProjectDao;
import pm.pojo.ProjectWrapper;

public class ViewProjectController extends AbstractController {
	
	private Log log = LogFactory.getLog(ViewProjectController.class.getName()); 
	private ProjectDao projectDao;

	public ModelAndView handleRequestInternal(HttpServletRequest request,
		HttpServletResponse response) throws Exception {
		Integer projectId = Integer.valueOf(request.getParameter("id"));
    	ModelAndView mav = new ModelAndView("viewproject");
		ProjectWrapper pw = projectDao.getProjectWrapperById(projectId);
		mav.addObject("pw", pw);
		if (!pw.getProject().getEndDate().trim().equals("")) {
			Date now = new Date();
	    	DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			boolean expired = now.after(df.parse(pw.getProject().getEndDate()));
			mav.addObject("expired",expired);
		}
		return mav;
	}
	
	public void setProjectDao(ProjectDao projectDao) {
		this.projectDao = projectDao;
	}

}
