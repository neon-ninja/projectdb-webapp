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

import pm.pojo.Facility;
import pm.pojo.ProjectFacility;
import pm.pojo.ProjectWrapper;

@Controller
public class ProjectFacilityController extends GlobalController {
	
	@RequestMapping(value = "deleteprojectfacility", method = RequestMethod.GET)
	public RedirectView delete(Integer fid, Integer projectId) throws Exception {
		ProjectWrapper pw = this.tempProjectManager.get(projectId);
    	List<ProjectFacility> tmp = new LinkedList<ProjectFacility>();
        for (ProjectFacility pf: pw.getProjectFacilities()) {
        	if (!pf.getFacilityId().equals(fid)) {
        		tmp.add(pf);
        	}
        }
        pw.setProjectFacilities(tmp);
    	this.tempProjectManager.update(projectId, pw);
		return new RedirectView("editproject?id=" + projectId + "#facilities");
	}
	
	@RequestMapping(value = "editprojectfacility", method = RequestMethod.GET)
    public ModelAndView edit(Integer fid, Integer projectId) throws Exception {
		ModelAndView mav = new ModelAndView();
    	ProjectWrapper pw = this.tempProjectManager.get(projectId);
    	HashMap<Integer,String> facilities = new LinkedHashMap<Integer, String>();
    	for (Facility f: this.projectDao.getFacilities()) {
    		facilities.put(f.getId(), f.getName());
    	}
    	ProjectFacility pf = new ProjectFacility();
    	for (ProjectFacility pfc : pw.getProjectFacilities()) {
    		if (pfc.getFacilityId().equals(fid)) {
    			pf = pfc;
    		}
    	}
        mav.addObject("facilities", facilities);
        mav.addObject("projectFacility", pf);
        return mav;
    }
	
	@RequestMapping(value = "editprojectfacility", method = RequestMethod.POST)
	public RedirectView editPost(ProjectFacility pf, Integer fid) throws Exception {
    	Integer projectId = pf.getProjectId();
    	ProjectWrapper pw = this.tempProjectManager.get(projectId);
    	pf.setFacilityName(this.projectDao.getFacilityById(pf.getFacilityId()).getName());
    	if (fid==null) {
    		pw.getProjectFacilities().add(pf);
    	} else {
    		for (int i=0; i<pw.getProjectFacilities().size(); i++) {
    			if (pw.getProjectFacilities().get(i).getFacilityId().equals(fid)) { 
    				pw.getProjectFacilities().set(i, pf);
    			}
    		}
    	}
    	this.tempProjectManager.update(projectId, pw);
		return new RedirectView("editproject?id=" + projectId + "#facilities");
	}
}
