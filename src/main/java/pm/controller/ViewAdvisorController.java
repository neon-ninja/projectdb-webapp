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
import pm.pojo.Advisor;
import pm.pojo.Project;

public class ViewAdvisorController extends AbstractController {
	
	private static Log log = LogFactory.getLog(Thread.currentThread().getClass()); 
	private ProjectDao projectDao;

	public ModelAndView handleRequestInternal(HttpServletRequest request,
		HttpServletResponse response) throws Exception {
    	ModelAndView mav = new ModelAndView("viewadvisor");
    	Advisor a = projectDao.getAdvisorById(Integer.valueOf(request.getParameter("id")));
    	List<Project> ps = projectDao.getAllProjectsForAdvisorId(a.getId());

    	// mark projects as due if a review or follow-up is due
    	Date now = new Date();
    	DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
    	for (Project p: ps) {
        	String fuDate = p.getNextFollowUpDate();
        	String rDate = p.getNextReviewDate();
    		if (!fuDate.equals("") && now.after(df.parse(fuDate))) {
    			if (now.after(df.parse(fuDate))) {
    				p.setNextFollowUpDate(fuDate + " (due)");
        		}
        	}
    		if (!rDate.equals("") && now.after(df.parse(rDate))) {
            	if (now.after(df.parse(rDate))) {
            		p.setNextReviewDate(rDate + " (due)");
            	}    			
    		}
    	}

    	mav.addObject("advisor", a);
    	mav.addObject("projects", projectDao.getAllProjectsForAdvisorId(a.getId()));
		return mav;
	}
	
	public void setProjectDao(ProjectDao projectDao) {
		this.projectDao = projectDao;
	}

}
