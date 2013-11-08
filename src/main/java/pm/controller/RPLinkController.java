package pm.controller;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import pm.pojo.APLink;
import pm.pojo.ProjectWrapper;
import pm.pojo.RPLink;
import pm.pojo.Researcher;
import pm.pojo.ResearcherRole;

@Controller
public class RPLinkController extends GlobalController {

	@RequestMapping(value = "/editrplink", method = RequestMethod.GET)
    protected ModelAndView edit(Integer projectId, Integer rid) throws Exception {
		ModelAndView mav = new ModelAndView("editRPLink");
    	ProjectWrapper pw = this.tempProjectManager.get(projectId);
    	RPLink rpLink = new RPLink();
    	mav.addObject("action", "Create");
    	if (rid!=null) {
	    	for (RPLink r:pw.getRpLinks()) {
				if (r.getResearcherId().equals(rid)) {
					rpLink = r;
					mav.addObject("action", "Edit");
				}
			}
    	}
		List<ResearcherRole> rRolesTmp = this.projectDao.getResearcherRoles();
		HashMap<Integer,String> researcherRoles = new LinkedHashMap<Integer, String>();
		if (rRolesTmp != null) {
			for (ResearcherRole rr: rRolesTmp) {
				researcherRoles.put(rr.getId(), rr.getName());
			}
		}
		List<Researcher> researchersTmp = this.projectDao.getResearchers();
		HashMap<Integer,String> researchers = new LinkedHashMap<Integer, String>();
		if (researchersTmp != null) {
			for (Researcher r: researchersTmp) {
				researchers.put(r.getId(), r.getFullName());
			}
		}
		mav.addObject("rpLink", rpLink);
		mav.addObject("researchers", researchers);
		mav.addObject("researcherRoles", researcherRoles);
        return mav;
    }
	
	@RequestMapping(value = "/editrplink", method = RequestMethod.POST)
	public RedirectView editPost(RPLink rpLink, Integer rid) throws Exception {
    	Integer projectId = rpLink.getProjectId();
    	ProjectWrapper pw = this.tempProjectManager.get(projectId);
    	rpLink.setResearcher(this.projectDao.getResearcherById(rpLink.getResearcherId()));
    	rpLink.setResearcherRoleName(this.projectDao.getResearcherRoleById(rpLink.getResearcherRoleId()).getName());
    	boolean found = false;
    	for (int i=0;i<pw.getRpLinks().size();i++) {
    		Integer currentResearcherId = pw.getRpLinks().get(i).getResearcherId();
    		// Either we modified an existing adviser, or we changed an adviser record to a new adviser (original adviser id (aid) != apLink.adviserId)
    		if (currentResearcherId.equals(rpLink.getResearcherId()) || currentResearcherId.equals(rid)) {
    			found = true;
    			pw.getRpLinks().set(i, rpLink);
    		}
    	}
    	if (!found) pw.getRpLinks().add(rpLink);
    	this.tempProjectManager.update(projectId, pw);
		return new RedirectView("editproject?id=" + projectId + "#researchers");
	}
	
	@RequestMapping(value = "/deleterplink", method = RequestMethod.GET)
    protected RedirectView delete(Integer projectId, Integer rid) throws Exception {
		ProjectWrapper pw = this.tempProjectManager.get(projectId);
		List<RPLink> tmp = new LinkedList<RPLink>();
	    for (RPLink rl: pw.getRpLinks()) {
	    	if (!rl.getResearcherId().equals(rid) || !rl.getProjectId().equals(projectId)) {
	    		tmp.add(rl);
	    	}
	    }
	    pw.setRpLinks(tmp);
		this.tempProjectManager.update(projectId, pw);
		return new RedirectView("editproject?id=" + projectId + "#researchers");
	}
}
