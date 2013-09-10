package pm.controller;

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
		return mav;
	}
	
	public void setProjectDao(ProjectDao projectDao) {
		this.projectDao = projectDao;
	}

}
