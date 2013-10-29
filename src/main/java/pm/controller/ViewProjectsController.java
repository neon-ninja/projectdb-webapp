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
    	
    	Map<String,String[]> params = request.getParameterMap();
    	
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
        	
        	boolean valid = false;
        	for (Map.Entry<String,String[]> e : params.entrySet()) {
        		String v = e.getValue()[0].toLowerCase();
        		// This provides capability to filter by multiple fields
        		switch (e.getKey()) {
        		case "query":
        			if (p.getName().toLowerCase().contains(v) || p.getDescription().toLowerCase().contains(v) || 
        				p.getHostInstitution().toLowerCase().contains(v) || p.getNotes().toLowerCase().contains(v) ||
        				p.getProjectCode().toLowerCase().contains(v) || p.getProjectTypeName().toLowerCase().contains(v) ||
        				p.getRequirements().toLowerCase().contains(v) || p.getTodo()!=null && p.getTodo().toLowerCase().contains(v))
        				valid = true;
        			break;
        		}
        				
        	}
        	if (valid) filtered.add(p);
        	
    	}
    	
    	if (params.isEmpty()) {
    		mav.addObject("projects", ps);
    	} else {
    		mav.addObject("projects", filtered);
    	}
    	if (request.getParameter("query")!=null) {
    		mav.addObject("query",request.getParameter("query").toLowerCase());
    	}
		return mav;
	}
	
	public void setProjectDao(ProjectDao projectDao) {
		this.projectDao = projectDao;
	}

}
