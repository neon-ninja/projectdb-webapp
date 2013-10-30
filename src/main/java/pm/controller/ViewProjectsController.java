package pm.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

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
    	List<Project> ps = projectDao.getProjects();
    	List<Project> filtered = new LinkedList<Project>();
    	
    	String q = null;
    	if (request.getParameter("query")!=null) {
    		q = request.getParameter("query").toLowerCase();
    	}
    	
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
        	
        	if (q!=null) {
        		if (p.getName().toLowerCase().contains(q) || p.getDescription().toLowerCase().contains(q) || 
        				p.getHostInstitution().toLowerCase().contains(q) || p.getNotes().toLowerCase().contains(q) ||
        				p.getProjectCode().toLowerCase().contains(q) || p.getProjectTypeName().toLowerCase().contains(q) ||
        				p.getRequirements().toLowerCase().contains(q) || p.getTodo()!=null && p.getTodo().toLowerCase().contains(q))
        			filtered.add(p);
        	}
    	}
    	
    	if (q==null) {
    		mav.addObject("projects", ps);
    	} else {
    		mav.addObject("projects", filtered);
    		mav.addObject("query", q);
    	}
		return mav;
	}
	
	public void setProjectDao(ProjectDao projectDao) {
		this.projectDao = projectDao;
	}

}
