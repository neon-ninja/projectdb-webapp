package pm.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

import pm.db.ProjectDao;
import pm.pojo.Advisor;
import pm.pojo.Attachment;
import pm.pojo.FollowUp;
import pm.util.Util;

public class CreateFollowUpController extends SimpleFormController {

	private static Log log = LogFactory.getLog(Thread.currentThread().getClass()); 
	private ProjectDao projectDao;
	
	@Override
	public ModelAndView onSubmit(Object o) throws ServletException {
		FollowUp f = (FollowUp) o;
    	Integer pid = f.getProjectId(); 
    	ModelAndView mav = new ModelAndView(super.getSuccessView());
    	mav.addObject("id", pid);
		try {
			Integer followUpId = this.projectDao.createFollowUp(f);
			if ((f.getAttachmentDescription() != null && f.getAttachmentDescription() != "") ||
				(f.getAttachmentLink() != null && f.getAttachmentLink() != "")) {
				Attachment a = new Attachment();
				a.setDate(f.getDate());
				a.setDescription(f.getAttachmentDescription());
				a.setLink(f.getAttachmentLink());
				a.setFollowUpId(followUpId);
				a.setProjectId(f.getProjectId());
				this.projectDao.createAttachment(a);
			}
			new Util().addProjectInfosToMav(mav, this.projectDao, pid);
		} catch (Exception e) {
        	throw new ServletException(e);
        }
		return mav;
	}

	@Override
    protected Map referenceData(HttpServletRequest request) throws Exception {
		ModelMap modelMap = new ModelMap();
		Integer pid = Integer.valueOf(request.getParameter("id"));
		List<Advisor> advisorsTmp = this.projectDao.getAllAdvisors();
		Map<Integer,String> advisors = new LinkedHashMap<Integer,String>();
		if (advisorsTmp != null) {
			for (Advisor a : advisorsTmp) {
				advisors.put(a.getId(),a.getFullName());
			}
		}
		modelMap.put("pid", pid);
        modelMap.put("advisors", advisors);
        return modelMap;
    }
	
	@Override
	protected Object formBackingObject(HttpServletRequest request) throws ServletException {
		FollowUp f = new FollowUp();
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		f.setDate(df.format(new Date()));
		return f;
	}

	public void setProjectDao(ProjectDao projectDao) {
		this.projectDao = projectDao;
	}

}
