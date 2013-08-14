package pm.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import pm.db.ProjectDao;
import pm.pojo.Project;

public class ViewProjectsController extends AbstractController {
	
	private Log log = LogFactory.getLog(ViewProjectsController.class.getName()); 
	private ProjectDao projectDao;

	public ModelAndView handleRequestInternal(HttpServletRequest request,
		HttpServletResponse response) throws Exception {
    	ModelAndView mav = new ModelAndView("viewprojects");
    	List<Project> ps = projectDao.getAllProjects();
    	
    	// mark projects as due if a review or follow-up is due
    	Date now = new Date();
    	DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
    	for (Project p: ps) {
    		String nextFollowUpDate = p.getNextFollowUpDate().trim();
    		String nextReviewDate = p.getNextReviewDate().trim();
        	if (!nextFollowUpDate.equals("") && now.after(df.parse(p.getNextFollowUpDate()))) {
        		p.setNextFollowUpDate(p.getNextFollowUpDate() + " (due)");
        	}
        	if (!nextReviewDate.equals("") && now.after(df.parse(p.getNextReviewDate()))) {
        		p.setNextReviewDate(p.getNextReviewDate() + " (due)");
        	}
    	}

    	mav.addObject("projects", ps);
		return mav;
	}
	
	public void setProjectDao(ProjectDao projectDao) {
		this.projectDao = projectDao;
	}

}
