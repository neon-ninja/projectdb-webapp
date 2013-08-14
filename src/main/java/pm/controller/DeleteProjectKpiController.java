package pm.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import pm.db.ProjectDao;

public class DeleteProjectKpiController extends AbstractController {
	
	private Log log = LogFactory.getLog(DeleteProjectKpiController.class.getName()); 
	private ProjectDao projectDao;

	public ModelAndView handleRequestInternal(HttpServletRequest request,
		HttpServletResponse response) throws Exception {
    	ModelAndView mav = new ModelAndView("redirectToEditProject");
        Integer id = Integer.valueOf(request.getParameter("id"));
    	Integer projectId = Integer.valueOf(request.getParameter("projectId"));
		mav.addObject("id", projectId);
    	projectDao.deleteProjectKpi(id);
		return mav;
	}
	
	public void setProjectDao(ProjectDao projectDao) {
		this.projectDao = projectDao;
	}

}
