package pm.controller;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import pm.db.ProjectDao;
import pm.pojo.APLink;
import pm.pojo.Adviser;
import pm.pojo.AdviserRole;
import pm.pojo.ProjectWrapper;
import pm.temp.TempProjectManager;

public class CreateAPLinkController extends GlobalController {

	private Log log = LogFactory.getLog(CreateAPLinkController.class.getName()); 
	@Autowired
	private ProjectDao projectDao;
	@Autowired
	private TempProjectManager tempProjectManager;
	@Autowired
	private String proxy;
	
	@RequestMapping(value = "/createaplink", method = RequestMethod.POST)
	public ModelAndView onSubmit(@ModelAttribute("aplink") APLink apLink) throws Exception {
    	ModelAndView mav = new ModelAndView();
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

	@RequestMapping(value = "/createaplink", method = RequestMethod.GET)
    protected ModelAndView referenceData(@RequestParam(value = "id", required = true) Integer pid) throws Exception {
		ModelAndView mav = new ModelAndView();
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
		mav.addObject("pid", pid);
		mav.addObject("aNotOnProject", aNotOnProject);
		mav.addObject("adviserRoles", adviserRoles);
        return mav;
    }
}
