package pm.controller;

import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.GlobalController;

import pm.pojo.ProjectWrapper;
import pm.pojo.RPLink;
import pm.temp.TempProjectManager;

public class DeleteRPLinkController extends GlobalController {
	
	private Log log = LogFactory.getLog(DeleteAPLinkController.class.getName());
	private String proxy;
	private TempProjectManager tempProjectManager;

	public ModelAndView handleRequestInternal(HttpServletRequest request,
		HttpServletResponse response) throws Exception {
    	ModelAndView mav = new ModelAndView();
		Integer researcherId = Integer.valueOf(request.getParameter("researcherId"));
		Integer projectId = Integer.valueOf(request.getParameter("projectId"));
    	ProjectWrapper pw = this.tempProjectManager.get(projectId);
    	List<RPLink> tmp = new LinkedList<RPLink>();
        for (RPLink al: pw.getRpLinks()) {
        	if (!al.getResearcherId().equals(researcherId) || !al.getProjectId().equals(projectId)) {
        		tmp.add(al);
        	}
        }
        pw.setRpLinks(tmp);
    	this.tempProjectManager.update(projectId, pw);
		mav.setViewName("redirect");
		mav.addObject("pathAndQuerystring", "editproject?id=" + projectId + "#researchers");
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
