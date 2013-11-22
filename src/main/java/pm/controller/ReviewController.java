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
import pm.pojo.ProjectWrapper;
import pm.pojo.Review;

@Controller
public class ReviewController extends GlobalController {
	
	@RequestMapping(value = "deletereview", method = RequestMethod.GET)
	public RedirectView delete(Integer id, Integer projectId) throws Exception {
		ProjectWrapper pw = this.tempProjectManager.get(projectId);
    	List<Review> tmp = new LinkedList<Review>();
        for (Review r: pw.getReviews()) {
        	if (!r.getId().equals(id)) {
        		tmp.add(r);
        	}
        }
        pw.setReviews(tmp);
    	this.tempProjectManager.update(projectId, pw);
		return new RedirectView ("editproject?id=" + projectId + "#reviews");
	}
	
	@RequestMapping(value = "editreview", method = RequestMethod.GET)
	public ModelAndView edit(Integer id, Integer projectId) throws Exception {
		ProjectWrapper pw = this.tempProjectManager.get(projectId);
		ModelAndView mav = new ModelAndView();
		Adviser a =  this.projectDao.getAdviserByTuakiriUniqueId(this.getTuakiriUniqueIdFromRequest());
		Review r = new Review();
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		r.setDate(df.format(new Date()));
		r.setAdviserId(a.getId());
		for (Review rv:pw.getReviews()) {
			if (rv.getId().equals(id)) r = rv;
		}
		mav.addObject("review", r);
		mav.addObject("adviserId", a.getId());
		return mav;
	}
		
	@RequestMapping(value = "editreview", method = RequestMethod.POST)
	public RedirectView editPost(Review r) throws Exception {
    	Integer projectId = r.getProjectId(); 
    	ProjectWrapper pw = this.tempProjectManager.get(projectId);
        // Set next Review to a year from now
        Date now = new Date();
        Date nextReview = new Date(now.getTime() + TimeUnit.DAYS.toMillis(365));
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        pw.getProject().setNextReviewDate(format.format(nextReview));
        r.setAdviserName(this.projectDao.getAdviserById(r.getAdviserId()).getFullName());
        if (r.getId()==null) {
    		r.setId(random.nextInt());
    		pw.getReviews().add(r);
    	} else {
        	for (int i=0;i<pw.getReviews().size();i++) {
        		if (pw.getReviews().get(i).getId().equals(r.getId())) {
        			r.setAttachments(pw.getReviews().get(i).getAttachments());
        			pw.getReviews().set(i, r);
        		}
        	}
    	}
    	this.tempProjectManager.update(projectId, pw);
		return new RedirectView ("editproject?id=" + projectId + "#reviews");
	}
}
