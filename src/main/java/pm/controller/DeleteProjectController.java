package pm.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import pm.authz.AuthzAspect;
import pm.db.ProjectDao;
import pm.pojo.Project;
import pm.temp.TempProjectManager;

public class DeleteProjectController extends AbstractController {
	
	private Log log = LogFactory.getLog(DeleteProjectController.class.getName()); 
	private ProjectDao projectDao;
	private TempProjectManager tempProjectManager;
	private AuthzAspect authzAspect;	

	public ModelAndView handleRequestInternal(HttpServletRequest request,
		HttpServletResponse response) throws Exception {
    	ModelAndView mav = new ModelAndView("viewprojects");
    	Integer pid = Integer.valueOf(request.getParameter("id"));
		this.authzAspect.verifyUserIsAdviserOnProject(pid);
        this.tempProjectManager.unregister(pid);
    	this.projectDao.deleteProjectWrapper(pid);
    	List<Project> pl = projectDao.getProjects();
    	mav.addObject("projects", pl);
		return mav;
	}
	
	public void setProjectDao(ProjectDao projectDao) {
		this.projectDao = projectDao;
	}

	public void setTempProjectManager(TempProjectManager tempProjectManager) {
		this.tempProjectManager = tempProjectManager;
	}

	public void setAuthzAspect(AuthzAspect authzAspect) {
		this.authzAspect = authzAspect;
	}

}
