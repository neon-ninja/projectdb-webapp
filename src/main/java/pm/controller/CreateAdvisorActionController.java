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
import pm.pojo.AdvisorAction;
import pm.pojo.Attachment;
import pm.util.Util;

public class CreateAdvisorActionController extends SimpleFormController {

	private Log log = LogFactory.getLog(CreateAdvisorActionController.class.getName()); 
	private ProjectDao projectDao;
	private String proxy;
	
	@Override
	public ModelAndView onSubmit(Object o) throws Exception {
		AdvisorAction aa = (AdvisorAction) o;
    	Integer projectId = aa.getProjectId(); 
    	ModelAndView mav = new ModelAndView(super.getSuccessView());
    	mav.addObject("id", projectId);
    	mav.addObject("proxy", this.proxy);
		Integer advisorActionId = this.projectDao.createAdvisorAction(projectId, aa);
		if ((aa.getAttachmentDescription() != null && aa.getAttachmentDescription() != "") ||
				(aa.getAttachmentLink() != null && aa.getAttachmentLink() != "")) {
			Attachment a = new Attachment();
			a.setDate(aa.getDate());
			a.setDescription(aa.getAttachmentDescription());
			a.setLink(aa.getAttachmentLink());
			a.setAdvisorActionId(advisorActionId);
			a.setProjectId(aa.getProjectId());
			this.projectDao.createAttachment(projectId, a);
		}
		new Util().addProjectInfosToMav(mav, this.projectDao, aa.getProjectId());
		return mav;
	}

	@Override
    protected Map referenceData(HttpServletRequest request) throws Exception {
		ModelMap modelMap = new ModelMap();
		Integer pid = Integer.valueOf(request.getParameter("id"));
		List<Advisor> onProject = this.projectDao.getAllAdvisorsOnProject(pid);
		Map<Integer,String> aOnProject = new LinkedHashMap<Integer,String>();
		if (aOnProject != null) {
			for (Advisor a : onProject) {
				aOnProject.put(a.getId(),a.getFullName());
			}
		}
		modelMap.put("pid", pid);
        modelMap.put("aOnProject", aOnProject);
        return modelMap;
    }
	
	@Override
	protected Object formBackingObject(HttpServletRequest request) throws ServletException {
		AdvisorAction aa = new AdvisorAction();
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		aa.setDate(df.format(new Date()));
		return aa;
	}

	public void setProjectDao(ProjectDao projectDao) {
		this.projectDao = projectDao;
	}

	public void setProxy(String proxy) {
		this.proxy = proxy;
	}

}
