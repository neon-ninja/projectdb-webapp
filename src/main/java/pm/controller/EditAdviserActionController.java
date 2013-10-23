package pm.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

import pm.pojo.AdviserAction;
import pm.pojo.ProjectWrapper;
import pm.temp.TempProjectManager;

public class EditAdviserActionController extends SimpleFormController {

	private Log log = LogFactory.getLog(EditAdviserActionController.class.getName());
	private TempProjectManager tempProjectManager;
	private String proxy;
	
	@Override
	public ModelAndView onSubmit(Object o) throws Exception {
		AdviserAction aa = (AdviserAction) o;
    	Integer projectId = aa.getProjectId(); 
    	ModelAndView mav = new ModelAndView();
    	ProjectWrapper pw = this.tempProjectManager.get(projectId);
    	for (int i=0;i<pw.getAdviserActions().size();i++) {
    		if (pw.getAdviserActions().get(i).getId().equals(aa.getId())) {
    			pw.getAdviserActions().set(i, aa);
    		}
    	}
    	this.tempProjectManager.update(projectId, pw);
		mav.setViewName("redirect");
		mav.addObject("pathAndQuerystring", "editproject?id=" + projectId + "#adviseractions");
		mav.addObject("proxy", this.proxy);
		return mav;
	}

	@Override
    protected Map referenceData(HttpServletRequest request) throws Exception {
		ModelMap modelMap = new ModelMap();
		Integer pid = Integer.valueOf(request.getParameter("adviserActionId"));
		modelMap.put("pid", pid);
        return modelMap;
    }
	
	@Override
	protected Object formBackingObject(HttpServletRequest request) throws Exception {
		Integer projectId = Integer.valueOf(request.getParameter("projectId"));
		Integer id = Integer.valueOf(request.getParameter("adviserActionId"));
		ProjectWrapper pw = this.tempProjectManager.get(projectId);
		for (AdviserAction aa:pw.getAdviserActions()) {
			if (aa.getId().equals(id)) return aa;
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
