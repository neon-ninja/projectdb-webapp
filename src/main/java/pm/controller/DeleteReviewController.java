package pm.controller;

import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import pm.db.ProjectDao;
import pm.pojo.ProjectWrapper;
import pm.pojo.ResearchOutput;
import pm.pojo.Review;
import pm.temp.TempProjectManager;

public class DeleteReviewController extends AbstractController {
	
	private Log log = LogFactory.getLog(DeleteReviewController.class.getName()); 
	private TempProjectManager tempProjectManager;
	private String proxy;

	public ModelAndView handleRequestInternal(HttpServletRequest request,
		HttpServletResponse response) throws Exception {
    	ModelAndView mav = new ModelAndView();
        Integer reviewId = Integer.valueOf(request.getParameter("reviewId"));
    	Integer projectId = Integer.valueOf(request.getParameter("projectId"));
    	ProjectWrapper pw = this.tempProjectManager.get(projectId);
    	List<Review> tmp = new LinkedList<Review>();
        for (Review r: pw.getReviews()) {
        	if (!r.getId().equals(reviewId)) {
        		tmp.add(r);
        	}
        }
        pw.setReviews(tmp);
    	this.tempProjectManager.update(projectId, pw);
		mav.setViewName("redirect");
		mav.addObject("pathAndQuerystring", "editproject?id=" + projectId + "#reviews");
		mav.addObject("proxy", this.proxy);
		return mav;
	}
	
	public void setProxy(String proxy) {
		this.proxy = proxy;
	}

	public void setTempProjectManager(TempProjectManager tempProjectManager) {
		this.tempProjectManager = tempProjectManager;
	}

}
