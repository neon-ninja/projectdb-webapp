package pm.controller;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

import pm.db.ProjectDao;
import pm.pojo.APLink;
import pm.pojo.Adviser;
import pm.pojo.AdviserRole;
import pm.pojo.ProjectKpi;
import pm.pojo.ProjectWrapper;
import pm.temp.TempProjectManager;

public class EditAPLinkController extends SimpleFormController {

	private Log log = LogFactory.getLog(EditAPLinkController.class.getName()); 
	private ProjectDao projectDao;
	private TempProjectManager tempProjectManager;
	private String proxy;
	
	@Override
	public ModelAndView onSubmit(Object o) throws Exception {
		APLink apLink = (APLink) o;
    	ModelAndView mav = new ModelAndView(super.getSuccessView());
    	Integer projectId = apLink.getProjectId();
    	ProjectWrapper pw = this.tempProjectManager.get(projectId);
    	apLink.setAdviser(this.projectDao.getAdviserById(apLink.getAdviserId()));
    	apLink.setAdviserRoleName(this.projectDao.getAdviserRoleById(apLink.getAdviserRoleId()).getName());
    	for (int i=0;i<pw.getApLinks().size();i++) {
    		if (pw.getApLinks().get(i).getAdviserId().equals(apLink.getAdviserId())) {
    			pw.getApLinks().set(i, apLink);
    		}
    	}
    	this.tempProjectManager.update(projectId, pw);
		mav.setViewName("redirect");
		mav.addObject("pathAndQuerystring", "editproject?id=" + projectId + "#advisers");
		mav.addObject("proxy", this.proxy);
		return mav;
	}

	@Override
    protected Map referenceData(HttpServletRequest request) throws Exception {
		ModelMap modelMap = new ModelMap();
		List<AdviserRole> aRolesTmp = this.projectDao.getAdviserRoles();
		HashMap<Integer,String> adviserRoles = new LinkedHashMap<Integer, String>();
		if (aRolesTmp != null) {
			for (AdviserRole ar: aRolesTmp) {
				adviserRoles.put(ar.getId(), ar.getName());
			}
		}
        modelMap.put("adviserRoles", adviserRoles);
        return modelMap;
    }
	
	@Override
	protected Object formBackingObject(HttpServletRequest request) throws Exception {
		Integer projectId = Integer.valueOf(request.getParameter("projectId"));
		Integer id = Integer.valueOf(request.getParameter("adviserId"));
		ProjectWrapper pw = this.tempProjectManager.get(projectId);
		for (APLink a:pw.getApLinks()) {
			if (a.getAdviserId().equals(id)) return a;
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
}
