package pm.controller;

import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.GlobalController;

import pm.pojo.APLink;
import pm.pojo.ProjectWrapper;
import pm.temp.TempProjectManager;

public class DeleteAPLinkController extends GlobalController {
	
	private Log log = LogFactory.getLog(DeleteAPLinkController.class.getName());
	private String proxy;
	private TempProjectManager tempProjectManager;

	public ModelAndView handleRequestInternal(HttpServletRequest request,
		HttpServletResponse response) throws Exception {
    	ModelAndView mav = new ModelAndView();
		Integer adviserId = Integer.valueOf(request.getParameter("adviserId"));
		Integer projectId = Integer.valueOf(request.getParameter("projectId"));
    	ProjectWrapper pw = this.tempProjectManager.get(projectId);
    	List<APLink> tmp = new LinkedList<APLink>();
        for (APLink al: pw.getApLinks()) {
        	if (!al.getAdviserId().equals(adviserId) || !al.getProjectId().equals(projectId)) {
        		tmp.add(al);
        	}
        }
        pw.setApLinks(tmp);
    	this.tempProjectManager.update(projectId, pw);
		mav.setViewName("redirect");
		mav.addObject("pathAndQuerystring", "editproject?id=" + projectId + "#advisers");
		mav.addObject("proxy", this.proxy);
		return mav;
	}
	
	public void setProxy(String proxy) {
		this.proxy = proxy;
	}

	public void setTempProjectManager(TempProjectManager tempProjectManager) {
		this.tempProjectManager = tempProjectManager;
	}

}
