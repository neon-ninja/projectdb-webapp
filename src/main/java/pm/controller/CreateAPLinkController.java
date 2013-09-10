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
import pm.pojo.ProjectWrapper;
import pm.temp.TempProjectManager;

public class CreateAPLinkController extends SimpleFormController {

	private Log log = LogFactory.getLog(CreateAPLinkController.class.getName()); 
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
    	pw.getApLinks().add(apLink);
    	this.tempProjectManager.update(projectId, pw);
		mav.setViewName("redirect");
		mav.addObject("pathAndQuerystring", "editproject?id=" + projectId + "#advisers");
		mav.addObject("proxy", this.proxy);
		return mav;
	}

	@Override
    protected Map referenceData(HttpServletRequest request) throws Exception {
		ModelMap modelMap = new ModelMap();
		Integer pid = Integer.valueOf(request.getParameter("id"));
    	ProjectWrapper pw = this.tempProjectManager.get(pid);
    	List<Integer> l = new LinkedList<Integer>();
    	for (APLink a: pw.getApLinks()) {
    		l.add(a.getAdviserId());
    	}
		List<Adviser> notOnProject = this.projectDao.getAdvisersNotOnList(l);
		List<AdviserRole> aRolesTmp = this.projectDao.getAdviserRoles();
		HashMap<Integer,String> adviserRoles = new LinkedHashMap<Integer, String>();
		if (aRolesTmp != null) {
			for (AdviserRole ar: aRolesTmp) {
				adviserRoles.put(ar.getId(), ar.getName());
			}
		}
		Map<Integer,String> aNotOnProject = new LinkedHashMap<Integer,String>();
		if (notOnProject != null) {
			for (Adviser a : notOnProject) {
				aNotOnProject.put(a.getId(), a.getFullName());
			}
		}
		modelMap.put("pid", pid);
        modelMap.put("aNotOnProject", aNotOnProject);
        modelMap.put("adviserRoles", adviserRoles);
        return modelMap;
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
