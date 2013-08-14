package pm.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import pm.db.ProjectDao;
import pm.pojo.Project;

public class DeleteProjectController extends AbstractController {
	
	private Log log = LogFactory.getLog(DeleteProjectController.class.getName()); 
	private ProjectDao projectDao;

	public ModelAndView handleRequestInternal(HttpServletRequest request,
		HttpServletResponse response) throws Exception {
    	ModelAndView mav = new ModelAndView("viewprojects");
    	projectDao.deleteProject(Integer.valueOf(request.getParameter("id")));
    	List<Project> pl = projectDao.getAllProjects();
    	mav.addObject("projects", pl);
		return mav;
	}
	
	public void setProjectDao(ProjectDao projectDao) {
		this.projectDao = projectDao;
	}

}
