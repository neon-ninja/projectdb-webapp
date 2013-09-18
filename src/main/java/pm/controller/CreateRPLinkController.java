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
import pm.pojo.RPLink;
import pm.pojo.Researcher;
import pm.pojo.ResearcherRole;
import pm.pojo.ProjectWrapper;
import pm.temp.TempProjectManager;

public class CreateRPLinkController extends SimpleFormController {

	private Log log = LogFactory.getLog(CreateRPLinkController.class.getName()); 
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
    	pw.getRpLinks().add(rpLink);
    	this.tempProjectManager.update(projectId, pw);
		mav.setViewName("redirect");
		mav.addObject("pathAndQuerystring", "editproject?id=" + projectId + "#researchers");
		mav.addObject("proxy", this.proxy);
		return mav;
	}

	@Override
    protected Map referenceData(HttpServletRequest request) throws Exception {
		ModelMap modelMap = new ModelMap();
		Integer pid = Integer.valueOf(request.getParameter("id"));
    	ProjectWrapper pw = this.tempProjectManager.get(pid);
    	List<Integer> l = new LinkedList<Integer>();
    	for (RPLink r: pw.getRpLinks()) {
    		l.add(r.getResearcherId());
    	}
		List<Researcher> notOnProject = this.projectDao.getResearchersNotOnList(l);
		List<ResearcherRole> rRolesTmp = this.projectDao.getResearcherRoles();
		HashMap<Integer,String> researcherRoles = new LinkedHashMap<Integer, String>();
		if (rRolesTmp != null) {
			for (ResearcherRole ar: rRolesTmp) {
				researcherRoles.put(ar.getId(), ar.getName());
			}
		}
		Map<Integer,String> rNotOnProject = new LinkedHashMap<Integer,String>();
		if (notOnProject != null) {
			for (Researcher a : notOnProject) {
				rNotOnProject.put(a.getId(), a.getFullName());
			}
		}
		modelMap.put("pid", pid);
        modelMap.put("rNotOnProject", rNotOnProject);
        modelMap.put("researcherRoles", researcherRoles);
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
