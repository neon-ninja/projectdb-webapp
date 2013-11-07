package pm.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.ui.ModelMap;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;

import pm.db.ProjectDao;
import pm.pojo.Adviser;
import pm.pojo.Attachment;
import pm.pojo.ProjectWrapper;
import pm.pojo.Review;
import pm.temp.TempProjectManager;

public class CreateReviewController extends GlobalController {

	private Log log = LogFactory.getLog(CreateReviewController.class.getName()); 
	private ProjectDao projectDao;
	private TempProjectManager tempProjectManager;
	private String proxy;
	private String remoteUserHeader;
	private Random random = new Random();
	
	@Override
	public ModelAndView onSubmit(Object o) throws Exception {
		Review r = (Review) o;
    	Integer projectId = r.getProjectId(); 
    	ModelAndView mav = new ModelAndView();
    	ProjectWrapper pw = this.tempProjectManager.get(projectId);
    	r.setId(random.nextInt());
    	r.setAdviserName(this.projectDao.getAdviserById(r.getAdviserId()).getFullName());
    	if (!r.getAttachmentDescription().trim().isEmpty() || !r.getAttachmentLink().trim().isEmpty()) {
        	Attachment a = new Attachment();
        	a.setId(random.nextInt());
        	a.setDate(r.getDate());
        	a.setDescription(r.getAttachmentDescription());
        	a.setReviewId(r.getId());
        	a.setLink(r.getAttachmentLink());
        	a.setProjectId(r.getProjectId());    		
            r.setAttachmentDescription(null);
            r.setAttachmentLink(null);
            r.getAttachments().add(a);
    	}
        pw.getReviews().add(r);
        // Set next Review to a year from now
        Date now = new Date();
        Date nextReview = new Date(now.getTime() + TimeUnit.DAYS.toMillis(365));
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        pw.getProject().setNextReviewDate(format.format(nextReview));
    	this.tempProjectManager.update(projectId, pw);
		mav.setViewName("redirect");
		mav.addObject("pathAndQuerystring", "editproject?id=" + projectId + "#reviews");
		mav.addObject("proxy", this.proxy);
		return mav;
	}

	@Override
    protected Map<String,Object> referenceData(HttpServletRequest request) throws Exception {
		ModelMap modelMap = new ModelMap();
		Integer pid = Integer.valueOf(request.getParameter("id"));
		Adviser a =  this.projectDao.getAdviserByTuakiriUniqueId(this.getTuakiriUniqueIdFromRequest());
		modelMap.put("pid", pid);
        modelMap.put("adviserId", a.getId());
        return modelMap;
    }
	
	@Override
	protected Object formBackingObject(HttpServletRequest request) throws Exception {
		Review r = new Review();
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		r.setDate(df.format(new Date()));
		return r;
	}
}
