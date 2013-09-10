package pm.controller;

import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import pm.pojo.FollowUp;
import pm.pojo.ProjectWrapper;
import pm.temp.TempProjectManager;

public class DeleteFollowUpController extends AbstractController {
	
	private Log log = LogFactory.getLog(DeleteFollowUpController.class.getName()); 
	private TempProjectManager tempProjectManager;
	private String proxy;

	public ModelAndView handleRequestInternal(HttpServletRequest request,
		HttpServletResponse response) throws Exception {
    	ModelAndView mav = new ModelAndView();
        Integer followUpId = Integer.valueOf(request.getParameter("followUpId"));
    	Integer projectId = Integer.valueOf(request.getParameter("projectId"));
    	ProjectWrapper pw = this.tempProjectManager.get(projectId);
    	List<FollowUp> tmp = new LinkedList<FollowUp>();
        for (FollowUp f: pw.getFollowUps()) {
        	if (!f.getId().equals(followUpId)) {
        		tmp.add(f);
        	}
        }
        pw.setFollowUps(tmp);
    	this.tempProjectManager.update(projectId, pw);
		mav.setViewName("redirect");
		mav.addObject("pathAndQuerystring", "editproject?id=" + projectId + "#followups");
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
