package pm.controller;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.ModelAndView;

import pm.db.ProjectDao;
import pm.pojo.ProjectWrapper;
import pm.pojo.RPLink;
import pm.pojo.ResearcherRole;
import pm.temp.TempProjectManager;

public class EditRPLinkController extends GlobalController {

	private Log log = LogFactory.getLog(EditRPLinkController.class.getName()); 
	private ProjectDao projectDao;
	private TempProjectManager tempProjectManager;
	private String proxy;
	
	@Override
	public ModelAndView onSubmit(Object o) throws Exception {
		RPLink rpLink = (RPLink) o;
    	ModelAndView mav = new ModelAndView(super.getSuccessView());
    	Integer projectId = rpLink.getProjectId();
    	ProjectWrapper pw = this.tempProjectManager.get(projectId);
    	rpLink.setResearcher(this.projectDao.getResearcherById(rpLink.getResearcherId()));
    	rpLink.setResearcherRoleName(this.projectDao.getResearcherRoleById(rpLink.getResearcherRoleId()).getName());
    	for (int i=0;i<pw.getRpLinks().size();i++) {
    		if (pw.getRpLinks().get(i).getResearcherId().equals(rpLink.getResearcherId())) {
    			pw.getRpLinks().set(i, rpLink);
    		}
    	}
    	this.tempProjectManager.update(projectId, pw);
		mav.setViewName("redirect");
		mav.addObject("pathAndQuerystring", "editproject?id=" + projectId + "#researchers");
		mav.addObject("proxy", this.proxy);
		return mav;
	}

	@Override
    protected Map<String,Object> referenceData(HttpServletRequest request) throws Exception {
		ModelMap modelMap = new ModelMap();
		List<ResearcherRole> rRolesTmp = this.projectDao.getResearcherRoles();
		HashMap<Integer,String> researcherRoles = new LinkedHashMap<Integer, String>();
		if (rRolesTmp != null) {
			for (ResearcherRole ar: rRolesTmp) {
				researcherRoles.put(ar.getId(), ar.getName());
			}
		}
        modelMap.put("researcherRoles", researcherRoles);
        return modelMap;
    }
	
	@Override
	protected Object formBackingObject(HttpServletRequest request) throws Exception {
		Integer projectId = Integer.valueOf(request.getParameter("projectId"));
		Integer id = Integer.valueOf(request.getParameter("researcherId"));
		ProjectWrapper pw = this.tempProjectManager.get(projectId);
		for (RPLink r:pw.getRpLinks()) {
			if (r.getResearcherId().equals(id)) return r;
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
