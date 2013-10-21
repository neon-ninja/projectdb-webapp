package pm.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import pm.db.ProjectDao;
import pm.pojo.APLink;
import pm.pojo.Adviser;
import pm.pojo.Project;

public class ViewAdviserController extends AbstractController {
	
	private Log log = LogFactory.getLog(ViewAdviserController.class.getName()); 
	private ProjectDao projectDao;

	public ModelAndView handleRequestInternal(HttpServletRequest request,
		HttpServletResponse response) throws Exception {
    	ModelAndView mav = new ModelAndView("viewadviser");
    	Adviser a = projectDao.getAdviserById(Integer.valueOf(request.getParameter("id")));
    	List<Project> ps = projectDao.getProjectsForAdviserId(a.getId());
    	Map<Integer,String> ar = new HashMap<Integer,String>();

    	// mark projects as due if a review or follow-up is due
    	Date now = new Date();
    	DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
    	for (Project p: ps) {
    		for (APLink l: projectDao.getProjectWrapperById(p.getId()).getApLinks()) {
    			if (l.getAdviserId().equals(Integer.valueOf(request.getParameter("id")))) {
    				ar.put(p.getId(),l.getAdviserRoleName());
    			}
    		}
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
    	
    	mav.addObject("adviserRole", ar);
    	mav.addObject("adviser", a);
    	mav.addObject("projects", projectDao.getProjectsForAdviserId(a.getId()));
		return mav;
	}
	
	public void setProjectDao(ProjectDao projectDao) {
		this.projectDao = projectDao;
	}

}
