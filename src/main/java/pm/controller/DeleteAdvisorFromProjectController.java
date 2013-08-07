package pm.controller;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import pm.db.ProjectDao;
import pm.util.Util;

public class DeleteAdvisorFromProjectController extends AbstractController {
	
	private static Log log = LogFactory.getLog(Thread.currentThread().getClass()); 
	private ProjectDao projectDao;

	public ModelAndView handleRequestInternal(HttpServletRequest request,
		HttpServletResponse response) throws Exception {
    	ModelAndView mav = new ModelAndView("redirectToEditProject");
		try {
			Integer advisorId = Integer.valueOf(request.getParameter("advisorId"));
			Integer projectId = Integer.valueOf(request.getParameter("projectId"));
			mav.addObject("id", projectId);
            this.projectDao.deleteAdvisorFromProject(advisorId, projectId);
            new Util().addProjectInfosToMav(mav, this.projectDao, projectId);
		} catch (Exception e) {
        	throw new ServletException(e);
        }
		return mav;
	}
	
	public void setProjectDao(ProjectDao projectDao) {
		this.projectDao = projectDao;
	}

}
