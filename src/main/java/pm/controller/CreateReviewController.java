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
import pm.pojo.Review;
import pm.util.Util;

public class CreateReviewController extends SimpleFormController {

	private static Log log = LogFactory.getLog(Thread.currentThread().getClass()); 
	private ProjectDao projectDao;
	
	@Override
	public ModelAndView onSubmit(Object o) throws ServletException {
		Review r = (Review) o;
    	Integer pid = r.getProjectId(); 
    	ModelAndView mav = new ModelAndView(super.getSuccessView());
    	mav.addObject("id", pid);
		try {
			Integer reviewId = this.projectDao.createReview(r);
			if ((r.getAttachmentDescription() != null && r.getAttachmentDescription() != "") ||
					(r.getAttachmentLink() != null && r.getAttachmentLink() != "")) {
					Attachment a = new Attachment();
					a.setDate(r.getDate());
					a.setDescription(r.getAttachmentDescription());
					a.setLink(r.getAttachmentLink());
					a.setReviewId(reviewId);
					a.setProjectId(r.getProjectId());
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
		Review r = new Review();
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		r.setDate(df.format(new Date()));
		return r;
	}

	public void setProjectDao(ProjectDao projectDao) {
		this.projectDao = projectDao;
	}

}
