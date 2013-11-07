package pm.controller;

import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.GlobalController;

import pm.db.ProjectDao;
import pm.pojo.ProjectFacility;
import pm.pojo.ProjectWrapper;
import pm.temp.TempProjectManager;

public class DeleteProjectFacilityController extends GlobalController {
	
	private Log log = LogFactory.getLog(DeleteProjectFacilityController.class.getName()); 
	private ProjectDao projectDao;
	private TempProjectManager tempProjectManager;
	private String proxy;

	public ModelAndView handleRequestInternal(HttpServletRequest request,
		HttpServletResponse response) throws Exception {
    	ModelAndView mav = new ModelAndView();
        Integer facilityId = Integer.valueOf(request.getParameter("facilityId"));
    	Integer projectId = Integer.valueOf(request.getParameter("projectId"));
    	ProjectWrapper pw = this.tempProjectManager.get(projectId);
    	List<ProjectFacility> tmp = new LinkedList<ProjectFacility>();
        for (ProjectFacility pf: pw.getProjectFacilities()) {
        	if (!pf.getFacilityId().equals(facilityId)) {
        		tmp.add(pf);
        	}
        }
        pw.setProjectFacilities(tmp);
    	this.tempProjectManager.update(projectId, pw);
		mav.setViewName("redirect");
		mav.addObject("pathAndQuerystring", "editproject?id=" + projectId + "#facilities");
		mav.addObject("proxy", this.proxy);
		return mav;
	}
	
	public void setProjectDao(ProjectDao projectDao) {
		this.projectDao = projectDao;
	}

	public void setProxy(String proxy) {
		this.proxy = proxy;
	}

	public void setTempProjectManager(TempProjectManager tempProjectManager) {
		this.tempProjectManager = tempProjectManager;
	}

}
