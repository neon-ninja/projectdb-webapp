package pm.controller;

import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import pm.pojo.AdviserAction;
import pm.pojo.ProjectWrapper;
import pm.temp.TempProjectManager;

public class DeleteAdviserActionController extends AbstractController {
	
	private Log log = LogFactory.getLog(DeleteAdviserActionController.class.getName());
	private TempProjectManager tempProjectManager;
	private String proxy;

	public ModelAndView handleRequestInternal(HttpServletRequest request,
		HttpServletResponse response) throws Exception {
    	ModelAndView mav = new ModelAndView();
        Integer adviserActionId = Integer.valueOf(request.getParameter("adviserActionId"));
    	Integer projectId = Integer.valueOf(request.getParameter("projectId"));
    	ProjectWrapper pw = this.tempProjectManager.get(projectId);
    	List<AdviserAction> tmp = new LinkedList<AdviserAction>();
        for (AdviserAction aa: pw.getAdviserActions()) {
        	if (!aa.getId().equals(adviserActionId)) {
        		tmp.add(aa);
        	}
        }
        pw.setAdviserActions(tmp);
    	this.tempProjectManager.update(projectId, pw);
		mav.setViewName("redirect");
		mav.addObject("pathAndQuerystring", "editproject?id=" + projectId + "#adviseractions");
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
