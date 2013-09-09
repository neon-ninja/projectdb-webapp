package pm.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import pm.db.ProjectDao;

public class DeleteFollowUpController extends AbstractController {
	
	private Log log = LogFactory.getLog(DeleteFollowUpController.class.getName()); 
	private ProjectDao projectDao;
	private String successView;
	private String proxy;

	public ModelAndView handleRequestInternal(HttpServletRequest request,
		HttpServletResponse response) throws Exception {
    	ModelAndView mav = new ModelAndView(this.successView);
        Integer followUpId = Integer.valueOf(request.getParameter("followUpId"));
    	Integer projectId = Integer.valueOf(request.getParameter("projectId"));
		mav.addObject("id", projectId);
		mav.addObject("proxy", this.proxy);
    	projectDao.deleteFollowUp(projectId, followUpId);
		return mav;
	}
	
	public void setProjectDao(ProjectDao projectDao) {
		this.projectDao = projectDao;
	}

	public void setSuccessView(String successView) {
		this.successView = successView;
	}
	
	public void setProxy(String proxy) {
		this.proxy = proxy;
	}


}
