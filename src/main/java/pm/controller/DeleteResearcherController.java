package pm.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import pm.db.ProjectDao;
import pm.pojo.Researcher;

public class DeleteResearcherController extends AbstractController {
	
	private Log log = LogFactory.getLog(DeleteResearcherController.class.getName()); 
	private ProjectDao projectDao;

	public ModelAndView handleRequestInternal(HttpServletRequest request,
		HttpServletResponse response) throws Exception {
    	ModelAndView mav = new ModelAndView("viewresearchers");
    	projectDao.deleteResearcher(Integer.valueOf(request.getParameter("id")));
    	List<Researcher> rl = projectDao.getAllResearchers();
    	mav.addObject("researchers", rl);
		return mav;
	}
	
	public void setProjectDao(ProjectDao projectDao) {
		this.projectDao = projectDao;
	}

}
