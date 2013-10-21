package pm.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.ui.ModelMap;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

import pm.authz.AuthzAspect;
import pm.db.ProjectDao;
import pm.pojo.Adviser;
import pm.pojo.Attachment;
import pm.pojo.FollowUp;
import pm.pojo.ProjectWrapper;
import pm.pojo.Review;
import pm.temp.TempProjectManager;

public class EditFollowUpController extends SimpleFormController {

	private Log log = LogFactory.getLog(EditFollowUpController.class.getName()); 
	private ProjectDao projectDao;
	private TempProjectManager tempProjectManager;
	private String proxy;
	private String remoteUserHeader;
	private Random random = new Random();
	private AuthzAspect authzAspect;
	
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
    protected Map referenceData(HttpServletRequest request) throws Exception {
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

	public void setProjectDao(ProjectDao projectDao) {
		this.projectDao = projectDao;
	}

	public void setTempProjectManager(TempProjectManager tempProjectManager) {
		this.tempProjectManager = tempProjectManager;
	}

	public void setProxy(String proxy) {
		this.proxy = proxy;
	}
	
    public void setRemoteUserHeader(String remoteUserHeader) {
		this.remoteUserHeader = remoteUserHeader;
	}

	private String getTuakiriUniqueIdFromRequest() {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		String user = (String) request.getAttribute(this.remoteUserHeader);
		if (user == null) {
			user = "NULL";
		}
		return user;
	}

	public void setAuthzAspect(AuthzAspect authzAspect) {
		this.authzAspect = authzAspect;
	}
}
