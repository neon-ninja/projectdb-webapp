package pm.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import pm.pojo.Adviser;
import pm.pojo.ProjectWrapper;
import pm.pojo.ResearchOutput;
import pm.pojo.ResearchOutputType;

@Controller
public class ResearchOutputController extends GlobalController {
	@RequestMapping(value = "viewresearchoutput", method = RequestMethod.GET)
	public ModelAndView viewresearchoutput() throws Exception {
    	ModelAndView mav = new ModelAndView();
    	mav.addObject("researchOutputs", projectDao.getResearchOutput());
		return mav;
	}
	@RequestMapping(value = "deleteresearchoutput", method = RequestMethod.GET)
	public RedirectView delete(Integer id, Integer projectId) throws Exception {
		ProjectWrapper pw = this.tempProjectManager.get(projectId);
    	List<ResearchOutput> tmp = new LinkedList<ResearchOutput>();
        for (ResearchOutput ro: pw.getResearchOutputs()) {
        	if (!ro.getId().equals(id)) {
        		tmp.add(ro);
        	}
        }
        pw.setResearchOutputs(tmp);
    	this.tempProjectManager.update(projectId, pw);
    	return new RedirectView("editproject?id=" + projectId + "#outputs");
	}
	@RequestMapping(value = "editresearchoutput", method = RequestMethod.GET)
	public ModelAndView edit(Integer id, Integer projectId) throws Exception {
		ResearchOutput r = new ResearchOutput();
		ProjectWrapper pw = this.tempProjectManager.get(projectId);
		if (id!=null) {
			for (ResearchOutput ro:pw.getResearchOutputs()) {
				if (ro.getId().equals(id)) r = ro;
			}
		} else {
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			r.setDate(df.format(new Date()));
		}
		List<ResearchOutputType> researchOutputTypesTmp = this.projectDao.getResearchOutputTypes();
		HashMap<Integer,String> researchOutputTypes = new LinkedHashMap<Integer, String>();
		if (researchOutputTypesTmp != null) {
			for (ResearchOutputType rot: researchOutputTypesTmp) {
				researchOutputTypes.put(rot.getId(), rot.getName());
			}
		}
		ModelAndView mav = new ModelAndView();
		mav.addObject("researchOutput", r);
		mav.addObject("adviserId", r.getAdviserId());
		mav.addObject("researchOutputTypes", researchOutputTypes);
		return mav;
	}
	@RequestMapping(value = "editresearchoutput", method = RequestMethod.POST)
	public RedirectView editPost(ResearchOutput r) throws Exception {
		Integer projectId = r.getProjectId();
    	ProjectWrapper pw = this.tempProjectManager.get(projectId);
    	r.setType(this.projectDao.getResearchOutputTypeById(r.getTypeId()).getName());
		if (r.getId()==null) {
			Adviser a = this.projectDao.getAdviserByTuakiriUniqueId(this.getTuakiriUniqueIdFromRequest());
			r.setAdviserId(a.getId());
	    	r.setAdviserName(a.getFullName());
	    	r.setId(random.nextInt());
	    	pw.getResearchOutputs().add(r);
		} else {
			for (int i=0;i<pw.getResearchOutputs().size();i++) {
	    		if (pw.getResearchOutputs().get(i).getId().equals(r.getId())) {
	    			r.setAdviserName(this.projectDao.getAdviserById(r.getAdviserId()).getFullName());
	    			pw.getResearchOutputs().set(i, r);
	    		}
	    	}
		}
    	
    	this.tempProjectManager.update(projectId, pw);
		return new RedirectView("editproject?id=" + projectId + "#outputs");
	}
}
