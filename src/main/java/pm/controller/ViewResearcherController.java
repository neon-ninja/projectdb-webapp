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
import pm.pojo.Researcher;

public class ViewResearcherController extends AbstractController {
	
	private static Log log = LogFactory.getLog(Thread.currentThread().getClass()); 
	private ProjectDao projectDao;

	public ModelAndView handleRequestInternal(HttpServletRequest request,
		HttpServletResponse response) throws Exception {
    	ModelAndView mav = new ModelAndView("viewresearcher");
    	Researcher r = projectDao.getResearcherById(Integer.valueOf(request.getParameter("id")));
    	List<Project> ps = projectDao.getAllProjectsForResearcherId(r.getId());
    	mav.addObject("researcher", r);
    	mav.addObject("projects", ps);
		return mav;
	}
	
	public void setProjectDao(ProjectDao projectDao) {
		this.projectDao = projectDao;
	}

}
