package pm.controller;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

import pm.db.ProjectDao;
import pm.pojo.Advisor;

public class EditAdvisorController extends SimpleFormController {
	
	private static Log log = LogFactory.getLog(Thread.currentThread().getClass()); 
	private ProjectDao projectDao;

	@Override
	public ModelAndView onSubmit(Object a) throws Exception {
		Advisor advisor = (Advisor) a;
    	ModelAndView mav = new ModelAndView(super.getSuccessView());
		try {
            this.projectDao.updateAdvisor(advisor);
		} catch (Exception e) {
        	throw new ServletException(e);
        }
		mav.addObject("advisor", advisor);
		mav.addObject("projects", projectDao.getAllProjectsForAdvisorId(advisor.getId()));
		return mav;
	}
	
	@Override
	protected Object formBackingObject(HttpServletRequest request) throws ServletException {
		Advisor a = null;
		Integer id = Integer.valueOf(request.getParameter("id"));
		try {
  		    a = this.projectDao.getAdvisorById(id);
		} catch (Exception e) {
			throw new ServletException(e);
		}
		return a;
	}
	
	public void setProjectDao(ProjectDao projectDao) {
		this.projectDao = projectDao;
	}

}
