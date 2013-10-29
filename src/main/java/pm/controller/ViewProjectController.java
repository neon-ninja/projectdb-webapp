package pm.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import pm.db.ProjectDao;
import pm.pojo.APLink;
import pm.pojo.ProjectWrapper;
import pm.pojo.RPLink;

public class ViewProjectController extends AbstractController {
	
	private Log log = LogFactory.getLog(ViewProjectController.class.getName()); 
	private ProjectDao projectDao;

	public ModelAndView handleRequestInternal(HttpServletRequest request,
		HttpServletResponse response) throws Exception {
		Integer projectId = Integer.valueOf(request.getParameter("id"));
    	ModelAndView mav = new ModelAndView("viewproject");
		ProjectWrapper pw = projectDao.getProjectWrapperById(projectId);
		mav.addObject("pw", pw);
		Date now = new Date();
    	DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		if (!pw.getProject().getEndDate().trim().equals("")) {
			boolean expired = now.after(df.parse(pw.getProject().getEndDate()));
			mav.addObject("expired",expired);
		}
		for (RPLink r:pw.getRpLinks()) {
			if (!r.getResearcher().getEndDate().trim().equals("")) {
				if (now.after(df.parse(r.getResearcher().getEndDate()))) {
					r.getResearcher().setFullName(r.getResearcher().getFullName() + " (expired)");
				}
			}
		}
		for (APLink a:pw.getApLinks()) {
			if (a.getAdviser().getEndDate()!=null && !a.getAdviser().getEndDate().trim().equals("")) {
				if (now.after(df.parse(a.getAdviser().getEndDate()))) {
					a.getAdviser().setFullName(a.getAdviser().getFullName() + " (expired)");
				}
			}
		}
		return mav;
	}
	
	public void setProjectDao(ProjectDao projectDao) {
		this.projectDao = projectDao;
	}

}
