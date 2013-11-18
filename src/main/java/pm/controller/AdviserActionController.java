package pm.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import pm.pojo.Adviser;
import pm.pojo.AdviserAction;
import pm.pojo.ProjectWrapper;

@Controller
public class AdviserActionController extends GlobalController {
	
	@RequestMapping(value = "deleteadviseraction", method = RequestMethod.GET)
	public RedirectView delete(Integer id, Integer projectId) throws Exception {
		ProjectWrapper pw = this.tempProjectManager.get(projectId);
    	List<AdviserAction> tmp = new LinkedList<AdviserAction>();
        for (AdviserAction aa: pw.getAdviserActions()) {
        	if (!aa.getId().equals(id)) {
        		tmp.add(aa);
        	}
        }
        pw.setAdviserActions(tmp);
    	this.tempProjectManager.update(projectId, pw);
		return new RedirectView("editproject?id=" + projectId + "#adviseractions");
	}
	
	@RequestMapping(value = "editadviseraction", method = RequestMethod.GET)
	public ModelAndView edit(Integer id, Integer projectId) throws Exception {
		ProjectWrapper pw = this.tempProjectManager.get(projectId);
		ModelAndView mav = new ModelAndView();
		AdviserAction aa = new AdviserAction();
		Adviser a =  this.projectDao.getAdviserByTuakiriUniqueId(getTuakiriUniqueIdFromRequest());
		aa.setAdviserId(a.getId());
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		aa.setDate(df.format(new Date()));
		for (AdviserAction aac:pw.getAdviserActions()) {
			if (aac.getId().equals(id)) aa = aac;
		}
		mav.addObject("adviserAction",aa);
		mav.addObject("adviserId",aa.getAdviserId());
		return mav;
	}

	@RequestMapping(value = "editadviseraction", method = RequestMethod.POST)
	public RedirectView editPost(AdviserAction aa) throws Exception {
    	Integer projectId = aa.getProjectId();
    	ProjectWrapper pw = this.tempProjectManager.get(projectId);
    	aa.setAdviserName(this.projectDao.getAdviserById(aa.getAdviserId()).getFullName());
    	if (aa.getId()==null) {
    		aa.setId(random.nextInt());
    		pw.getAdviserActions().add(aa);
    	} else {
	    	for (int i=0;i<pw.getAdviserActions().size();i++) {
	    		if (pw.getAdviserActions().get(i).getId().equals(aa.getId())) {
	    			aa.setAttachments(pw.getAdviserActions().get(i).getAttachments());
	    			pw.getAdviserActions().set(i, aa);
	    		}
	    	}
    	}
    	this.tempProjectManager.update(projectId, pw);
		return new RedirectView("editproject?id=" + projectId + "#adviseractions");
	}
}
