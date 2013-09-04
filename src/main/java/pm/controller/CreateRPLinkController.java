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
import org.springframework.web.servlet.mvc.SimpleFormController;

import pm.db.ProjectDao;
import pm.pojo.RPLink;
import pm.pojo.Researcher;
import pm.pojo.ResearcherRole;
import pm.util.Util;

public class CreateRPLinkController extends SimpleFormController {

	private Log log = LogFactory.getLog(CreateRPLinkController.class.getName()); 
	private ProjectDao projectDao;
	private String proxy;
	
	@Override
	public ModelAndView onSubmit(Object o) throws Exception {
		RPLink rpLink = (RPLink) o;
    	ModelAndView mav = new ModelAndView(super.getSuccessView());
    	Integer projectId = rpLink.getProjectId();
		mav.addObject("id", projectId);
		mav.addObject("proxy", this.proxy);
		this.projectDao.createRPLink(projectId, rpLink);
		new Util().addProjectInfosToMav(mav, this.projectDao, projectId);
		return mav;
	}

	@Override
    protected Map referenceData(HttpServletRequest request) throws Exception {
		ModelMap modelMap = new ModelMap();
		Integer pid = Integer.valueOf(request.getParameter("id"));
		List<Researcher> notOnProject = this.projectDao.getAllResearchersNotOnProject(pid);
		List<ResearcherRole> rRolesTmp = this.projectDao.getAllResearcherRoles();
		HashMap<Integer,String> researcherRoles = new LinkedHashMap<Integer, String>();
		if (rRolesTmp != null) {
			for (ResearcherRole rr: rRolesTmp) {
				if (!rr.getName().equals("PI")) {
					researcherRoles.put(rr.getId(), rr.getName());
				}
			}
		}
		Map<Integer,String> rNotOnProject = new LinkedHashMap<Integer,String>();
		if (notOnProject != null) {
			for (Researcher r : notOnProject) {
				rNotOnProject.put(r.getId(), r.getFullName());
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

	public void setProxy(String proxy) {
		this.proxy = proxy;
	}

}
