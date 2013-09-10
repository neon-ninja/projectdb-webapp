package pm.controller;

import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import pm.pojo.AdviserAction;
import pm.pojo.Attachment;
import pm.pojo.FollowUp;
import pm.pojo.ProjectWrapper;
import pm.pojo.Review;
import pm.temp.TempProjectManager;

public class DeleteAttachmentController extends AbstractController {
	
	private Log log = LogFactory.getLog(DeleteAttachmentController.class.getName()); 
	private TempProjectManager tempProjectManager;
	private String proxy;

	public ModelAndView handleRequestInternal(HttpServletRequest request,
		HttpServletResponse response) throws Exception {
    	ModelAndView mav = new ModelAndView();
        Integer attachmentId = Integer.valueOf(request.getParameter("attachmentId"));
    	Integer projectId = Integer.valueOf(request.getParameter("projectId"));
    	ProjectWrapper pw = this.tempProjectManager.get(projectId);
    	String anchor = "";
    	
    	boolean found = false;
        for (Review r: pw.getReviews()) {
        	List<Attachment> tmp = new LinkedList<Attachment>();
        	for (Attachment a: r.getAttachments()) {
        		if (a.getId().equals(attachmentId)) {
            		found = true;
            		anchor="#reviews";
            	} else {
            		tmp.add(a);
            	}
        	}
        	r.setAttachments(tmp);
        }
        
        if (!found) {
            for (FollowUp f: pw.getFollowUps()) {
            	List<Attachment> tmp = new LinkedList<Attachment>();
            	for (Attachment a: f.getAttachments()) {
                	if (a.getId().equals(attachmentId)) {
                		found = true;
                		anchor="#followups";
                	} else {
                		tmp.add(a);
                	}
            	}
            	f.setAttachments(tmp);
            }
        }

        if (!found) {
            for (AdviserAction aa: pw.getAdviserActions()) {
            	List<Attachment> tmp = new LinkedList<Attachment>();
            	for (Attachment a: aa.getAttachments()) {
                	if (a.getId().equals(attachmentId)) {
                		found = true;
                		anchor="#adviseractions";
                	} else {
                		tmp.add(a);
                	}
            	}
            	aa.setAttachments(tmp);
            }
        }

    	this.tempProjectManager.update(projectId, pw);
		mav.setViewName("redirect");
		mav.addObject("pathAndQuerystring", "editproject?id=" + projectId + anchor);
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
