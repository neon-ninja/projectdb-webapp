package pm.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import pm.db.ProjectDao;
import pm.pojo.Adviser;
import pm.pojo.AdviserAction;
import pm.pojo.Attachment;
import pm.pojo.ProjectWrapper;
import pm.temp.TempProjectManager;

@Controller
public class CreateAdviserActionController extends GlobalController {

	private Log log = LogFactory.getLog(CreateAdviserActionController.class.getName());
	@Autowired
	private ProjectDao projectDao;
	@Autowired
	private TempProjectManager tempProjectManager;
	@Autowired
	private String proxy;
	@Autowired
	private String remoteUserHeader;
	private Random random = new Random();	
	
	@RequestMapping(value = "/createadviseraction", method = RequestMethod.POST)
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

	@RequestMapping(value = "/createadviseraction", method = RequestMethod.GET)
    protected ModelAndView formBackingObject(@RequestParam(value = "id", required = true) Integer pid) throws Exception {
		ModelAndView mav = new ModelAndView();
		Adviser a =  this.projectDao.getAdviserByTuakiriUniqueId(getTuakiriUniqueIdFromRequest(remoteUserHeader));
		AdviserAction aa = new AdviserAction();
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		aa.setDate(df.format(new Date()));
		mav.addObject("pid", pid);
		mav.addObject("adviserId", a.getId());
		mav.addObject("adviserAction",aa);
        return mav;
    }
}
