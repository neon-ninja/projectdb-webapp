package pm.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

import pm.db.ProjectDao;
import pm.pojo.APLink;
import pm.pojo.Adviser;
import pm.pojo.AdviserAction;
import pm.pojo.Attachment;
import pm.pojo.ProjectWrapper;
import pm.temp.TempProjectManager;

public class CreateAdviserActionController extends SimpleFormController {

	private Log log = LogFactory.getLog(CreateAdviserActionController.class.getName()); 
	private ProjectDao projectDao;
	private TempProjectManager tempProjectManager;
	private String proxy;
	private Random random = new Random();	
	
	@Override
	public ModelAndView onSubmit(Object o) throws Exception {
		AdviserAction aa = (AdviserAction) o;
    	Integer projectId = aa.getProjectId(); 
    	ModelAndView mav = new ModelAndView();
    	ProjectWrapper pw = this.tempProjectManager.get(projectId);
    	aa.setId(random.nextInt());
    	aa.setAdviserName(this.projectDao.getAdviserById(aa.getAdviserId()).getFullName());
    	if (!aa.getAttachmentDescription().trim().isEmpty() || !aa.getAttachmentLink().trim().isEmpty()) {
        	Attachment a = new Attachment();
        	a.setId(random.nextInt());
        	a.setDate(aa.getDate());
        	a.setDescription(aa.getAttachmentDescription());
        	a.setAdviserActionId(aa.getId());
        	a.setLink(aa.getAttachmentLink());
        	a.setProjectId(aa.getProjectId());    		
            aa.setAttachmentDescription(null);
            aa.setAttachmentLink(null);
            aa.getAttachments().add(a);
    	}
        pw.getAdviserActions().add(aa);
    	this.tempProjectManager.update(projectId, pw);
		mav.setViewName("redirect");
		mav.addObject("pathAndQuerystring", "editproject?id=" + projectId + "#adviseractions");
		mav.addObject("proxy", this.proxy);
		return mav;
	}

	@Override
    protected Map referenceData(HttpServletRequest request) throws Exception {
		ModelMap modelMap = new ModelMap();
		Integer pid = Integer.valueOf(request.getParameter("id"));
		List<APLink> apLinks =  this.tempProjectManager.get(pid).getApLinks();
		Map<Integer,String> advisers = new LinkedHashMap<Integer,String>();
		if (apLinks != null) {
			for (APLink ap : apLinks) {
				advisers.put(ap.getAdviserId(),ap.getAdviser().getFullName());
			}
		}
		modelMap.put("pid", pid);
        modelMap.put("aOnProject", advisers);
        return modelMap;
    }
	
	@Override
	protected Object formBackingObject(HttpServletRequest request) throws Exception {
		AdviserAction aa = new AdviserAction();
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		aa.setDate(df.format(new Date()));
		return aa;
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

}
