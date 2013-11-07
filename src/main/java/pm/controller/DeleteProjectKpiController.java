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
import pm.pojo.ProjectKpi;
import pm.pojo.ProjectWrapper;
import pm.temp.TempProjectManager;

public class DeleteProjectKpiController extends GlobalController {
	
	private Log log = LogFactory.getLog(DeleteProjectKpiController.class.getName()); 
	private ProjectDao projectDao;
	private TempProjectManager tempProjectManager;
	private String proxy;

	public ModelAndView handleRequestInternal(HttpServletRequest request,
		HttpServletResponse response) throws Exception {
    	ModelAndView mav = new ModelAndView();
        Integer id = Integer.valueOf(request.getParameter("id"));
    	Integer projectId = Integer.valueOf(request.getParameter("projectId"));
    	ProjectWrapper pw = this.tempProjectManager.get(projectId);
    	List<ProjectKpi> tmp = new LinkedList<ProjectKpi>();
        for (ProjectKpi pk: pw.getProjectKpis()) {
        	if (!pk.getId().equals(id)) {
        		tmp.add(pk);
        	}
        }
        pw.setProjectKpis(tmp);
    	this.tempProjectManager.update(projectId, pw);
		mav.setViewName("redirect");
		mav.addObject("pathAndQuerystring", "editproject?id=" + projectId + "#kpis");
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
