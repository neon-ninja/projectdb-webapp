package pm.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

import pm.db.ProjectDao;
import pm.pojo.AdviserAction;
import pm.pojo.Attachment;
import pm.pojo.FollowUp;
import pm.pojo.ProjectWrapper;
import pm.pojo.Review;
import pm.temp.TempProjectManager;

public class CreateAttachmentController extends SimpleFormController {

	private Log log = LogFactory.getLog(CreateAttachmentController.class.getName()); 
	private TempProjectManager tempProjectManager;
	private String proxy;
	private Random random = new Random();

	@Override
	public ModelAndView onSubmit(Object o) throws Exception {
		Attachment a = (Attachment) o;
    	Integer projectId = a.getProjectId(); 
    	String anchor = "";
    	ModelAndView mav = new ModelAndView();
    	ProjectWrapper pw = this.tempProjectManager.get(projectId);
    	a.setId(random.nextInt());
    	if (a.getReviewId() != null) {
            for (Review r: pw.getReviews()) {
            	if (r.getId().equals(a.getReviewId())) {
            		r.getAttachments().add(a);
            	}
            }
            anchor = "#reviews";
    	} else if (a.getFollowUpId() != null) {
            for (FollowUp fu: pw.getFollowUps()) {
            	if (fu.getId().equals(a.getFollowUpId())) {
            		fu.getAttachments().add(a);
            	}
            }
            anchor = "#followups";
    	} else if (a.getAdviserActionId() != null) {
            for (AdviserAction aa: pw.getAdviserActions()) {
            	if (aa.getId().equals(a.getAdviserActionId())) {
            		aa.getAttachments().add(a);
            	}
            }	
            anchor = "#adviseractions";
    	}
    	this.tempProjectManager.update(projectId, pw);
		mav.setViewName("redirect");
		mav.addObject("pathAndQuerystring", "editproject?id=" + projectId + anchor);
		mav.addObject("proxy", this.proxy);
		return mav;
	}

	@Override
	protected Object formBackingObject(HttpServletRequest request) throws Exception {
		Attachment a = new Attachment();
		a.setProjectId(Integer.valueOf(request.getParameter("id")));
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		a.setDate(df.format(new Date()));
		return a;
	}

	@Override
    protected Map<String,Object> referenceData(HttpServletRequest request) throws Exception {
		ModelMap modelMap = new ModelMap();
		modelMap.put("projectId", request.getParameter("id"));
        return modelMap;
    }

	public void setTempProjectManager(TempProjectManager tempProjectManager) {
		this.tempProjectManager = tempProjectManager;
	}

	public void setProxy(String proxy) {
		this.proxy = proxy;
	}
}
