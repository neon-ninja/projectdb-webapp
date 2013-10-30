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
import org.springframework.web.servlet.mvc.SimpleFormController;

import pm.db.ProjectDao;
import pm.pojo.Adviser;
import pm.pojo.Attachment;
import pm.pojo.FollowUp;
import pm.pojo.ProjectWrapper;
import pm.temp.TempProjectManager;

public class CreateFollowUpController extends SimpleFormController {

	private Log log = LogFactory.getLog(CreateFollowUpController.class.getName()); 
	private ProjectDao projectDao;
	private TempProjectManager tempProjectManager;
	private String proxy;
	private String remoteUserHeader;
	private Random random = new Random();
	
	@Override
	public ModelAndView onSubmit(Object o) throws Exception {
		FollowUp f = (FollowUp) o;
    	Integer projectId = f.getProjectId(); 
    	ModelAndView mav = new ModelAndView();
    	ProjectWrapper pw = this.tempProjectManager.get(projectId);
    	f.setId(random.nextInt());
    	f.setAdviserName(this.projectDao.getAdviserById(f.getAdviserId()).getFullName());
    	if (!f.getAttachmentDescription().trim().isEmpty() || !f.getAttachmentLink().trim().isEmpty()) {
        	Attachment a = new Attachment();
        	a.setId(random.nextInt());
        	a.setDate(f.getDate());
        	a.setDescription(f.getAttachmentDescription());
        	a.setFollowUpId(f.getId());
        	a.setLink(f.getAttachmentLink());
        	a.setProjectId(f.getProjectId());    		
            f.setAttachmentDescription(null);
            f.setAttachmentLink(null);
            f.getAttachments().add(a);
    	}
        pw.getFollowUps().add(f);
        // Set next FollowUp to 3 months from now
        Date now = new Date();
        Date nextFollowUp = new Date(now.getTime() + TimeUnit.DAYS.toMillis(30*3));
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        pw.getProject().setNextFollowUpDate(format.format(nextFollowUp));
    	this.tempProjectManager.update(projectId, pw);
		mav.setViewName("redirect");
		mav.addObject("pathAndQuerystring", "editproject?id=" + projectId + "#followups");
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
		FollowUp f = new FollowUp();
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		f.setDate(df.format(new Date()));
		return f;
	}

	public void setProjectDao(ProjectDao projectDao) {
		this.projectDao = projectDao;
	}

	public void setTempProjectManager(TempProjectManager tempProjectManager) {
		this.tempProjectManager = tempProjectManager;
	}

	public void setProxy(String proxy) {
		this.proxy = proxy;
	}
	
    public void setRemoteUserHeader(String remoteUserHeader) {
		this.remoteUserHeader = remoteUserHeader;
	}

	private String getTuakiriUniqueIdFromRequest() {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		String user = (String) request.getAttribute(this.remoteUserHeader);
		if (user == null) {
			user = "NULL";
		}
		return user;
	}
}
