package pm.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

import pm.db.ProjectDao;
import pm.pojo.Attachment;
import pm.util.Util;

public class CreateAttachmentController extends SimpleFormController {

	private Log log = LogFactory.getLog(CreateAttachmentController.class.getName()); 
	private ProjectDao projectDao;
	
	@Override
	public ModelAndView onSubmit(Object o) throws ServletException {
		Attachment a = (Attachment) o;
		Integer pid = a.getProjectId();
    	ModelAndView mav = new ModelAndView(super.getSuccessView());
		mav.addObject("id", pid);
		try {
			this.projectDao.createAttachment(a);
			new Util().addProjectInfosToMav(mav, this.projectDao, pid);
		} catch (Exception e) {
        	throw new ServletException(e);
        }
		return mav;
	}

	@Override
	protected Object formBackingObject(HttpServletRequest request) throws ServletException {
		Attachment a = new Attachment();
		a.setProjectId(Integer.getInteger(request.getParameter("id")));
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		a.setDate(df.format(new Date()));
		return a;
	}

	@Override
    protected Map referenceData(HttpServletRequest request) throws Exception {
		ModelMap modelMap = new ModelMap();
		modelMap.put("projectId", request.getParameter("id"));
        return modelMap;
    }

	public void setProjectDao(ProjectDao projectDao) {
		this.projectDao = projectDao;
	}

}
