package pm.controller;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import pm.db.ProjectDao;

public class DeleteResearcherFromProjectController extends AbstractController {
	
	private Log log = LogFactory.getLog(DeleteResearcherFromProjectController.class.getName()); 
	private ProjectDao projectDao;

	public ModelAndView handleRequestInternal(HttpServletRequest request,
		HttpServletResponse response) throws Exception {
    	ModelAndView mav = new ModelAndView("redirectToEditProject");
		try {
			Integer researcherId = Integer.valueOf(request.getParameter("researcherId"));
			Integer projectId = Integer.valueOf(request.getParameter("projectId"));
			mav.addObject("id", projectId);
            this.projectDao.deleteResearcherFromProject(researcherId, projectId);
		} catch (Exception e) {
        	throw new ServletException(e);
        }
		return mav;
	}
	
	public void setProjectDao(ProjectDao projectDao) {
		this.projectDao = projectDao;
	}

}
