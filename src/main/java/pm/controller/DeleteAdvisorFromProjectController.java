package pm.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import pm.db.ProjectDao;
import pm.util.Util;

public class DeleteAdvisorFromProjectController extends AbstractController {
	
	private Log log = LogFactory.getLog(DeleteAdvisorFromProjectController.class.getName());
	private String successView;
	private String proxy;
	private ProjectDao projectDao;

	public ModelAndView handleRequestInternal(HttpServletRequest request,
		HttpServletResponse response) throws Exception {
    	ModelAndView mav = new ModelAndView(this.successView);
		Integer advisorId = Integer.valueOf(request.getParameter("advisorId"));
		Integer projectId = Integer.valueOf(request.getParameter("projectId"));
		mav.addObject("id", projectId);
		mav.addObject("proxy", this.proxy);
        this.projectDao.deleteAdvisorFromProject(projectId, advisorId);
        new Util().addProjectInfosToMav(mav, this.projectDao, projectId);
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
