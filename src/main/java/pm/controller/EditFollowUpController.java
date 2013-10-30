package pm.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

import pm.pojo.FollowUp;
import pm.pojo.ProjectWrapper;
import pm.temp.TempProjectManager;

public class EditFollowUpController extends SimpleFormController {

	private Log log = LogFactory.getLog(EditFollowUpController.class.getName()); 
	private TempProjectManager tempProjectManager;
	private String proxy;
	
	@Override
	public ModelAndView onSubmit(Object o) throws Exception {
		FollowUp f = (FollowUp) o;
    	Integer projectId = f.getProjectId(); 
    	ModelAndView mav = new ModelAndView();
    	ProjectWrapper pw = this.tempProjectManager.get(projectId);
    	for (int i=0;i<pw.getFollowUps().size();i++) {
    		if (pw.getFollowUps().get(i).getId().equals(f.getId())) {
    			pw.getFollowUps().set(i, f);
    		}
    	}
    	this.tempProjectManager.update(projectId, pw);
		mav.setViewName("redirect");
		mav.addObject("pathAndQuerystring", "editproject?id=" + projectId + "#followups");
		mav.addObject("proxy", this.proxy);
		return mav;
	}

	@Override
    protected Map<String,Object> referenceData(HttpServletRequest request) throws Exception {
		ModelMap modelMap = new ModelMap();
		Integer pid = Integer.valueOf(request.getParameter("followUpId"));
		modelMap.put("pid", pid);
        return modelMap;
    }
	
	@Override
	protected Object formBackingObject(HttpServletRequest request) throws Exception {
		Integer projectId = Integer.valueOf(request.getParameter("projectId"));
		Integer id = Integer.valueOf(request.getParameter("followUpId"));
		ProjectWrapper pw = this.tempProjectManager.get(projectId);
		for (FollowUp f:pw.getFollowUps()) {
			if (f.getId().equals(id)) return f;
		}
		return null;
	}

	public void setTempProjectManager(TempProjectManager tempProjectManager) {
		this.tempProjectManager = tempProjectManager;
	}

	public void setProxy(String proxy) {
		this.proxy = proxy;
	}
	
}
