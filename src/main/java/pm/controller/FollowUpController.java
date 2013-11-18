package pm.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import pm.pojo.Adviser;
import pm.pojo.FollowUp;
import pm.pojo.ProjectWrapper;

@Controller
public class FollowUpController extends GlobalController {
	
	@RequestMapping(value = "deletefollowup", method = RequestMethod.GET)
	public RedirectView delete(Integer id, Integer projectId) throws Exception {
		ProjectWrapper pw = this.tempProjectManager.get(projectId);
    	List<FollowUp> tmp = new LinkedList<FollowUp>();
        for (FollowUp f: pw.getFollowUps()) {
        	if (!f.getId().equals(id)) {
        		tmp.add(f);
        	}
        }
        pw.setFollowUps(tmp);
    	this.tempProjectManager.update(projectId, pw);
		return new RedirectView("editproject?id=" + projectId + "#followups");
	}

	@RequestMapping(value = "editfollowup", method = RequestMethod.GET)
    protected ModelAndView edit(Integer id, Integer projectId) throws Exception {
		ModelAndView mav = new ModelAndView();
		ProjectWrapper pw = this.tempProjectManager.get(projectId);
		Adviser a =  this.projectDao.getAdviserByTuakiriUniqueId(this.getTuakiriUniqueIdFromRequest());
		FollowUp f = new FollowUp();
		f.setAdviserId(a.getId());
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		f.setDate(df.format(new Date()));
		for (FollowUp fu:pw.getFollowUps()) {
			if (fu.getId().equals(id)) f = fu;
		}
		mav.addObject("adviserId", f.getAdviserId());
		mav.addObject("followUp",f);
		return mav;
	}
	@RequestMapping(value = "editfollowup", method = RequestMethod.POST)
	public RedirectView editPost(FollowUp f) throws Exception {
    	Integer projectId = f.getProjectId(); 
    	ProjectWrapper pw = this.tempProjectManager.get(projectId);
    	// Set next FollowUp to 3 months from now
        Date now = new Date();
        Date nextFollowUp = new Date(now.getTime() + TimeUnit.DAYS.toMillis(30*3));
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        pw.getProject().setNextFollowUpDate(format.format(nextFollowUp));
        f.setAdviserName(this.projectDao.getAdviserById(f.getAdviserId()).getFullName());
    	if (f.getId()==null) {
    		f.setId(random.nextInt());
        	pw.getFollowUps().add(f);
    	} else {
        	for (int i=0;i<pw.getFollowUps().size();i++) {
        		if (pw.getFollowUps().get(i).getId().equals(f.getId())) {
        			f.setAttachments(pw.getFollowUps().get(i).getAttachments());
        			pw.getFollowUps().set(i, f);
        		}
        	}
    	}
    	this.tempProjectManager.update(projectId, pw);
		return new RedirectView("editproject?id=" + projectId + "#followups");
	}
}
