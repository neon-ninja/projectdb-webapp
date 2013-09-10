package pm.controller;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

import pm.db.ProjectDao;
import pm.pojo.Adviser;

public class EditAdviserController extends SimpleFormController {
	
	private static Log log = LogFactory.getLog(Thread.currentThread().getClass()); 
	private ProjectDao projectDao;
	private String proxy;

	@Override
	public ModelAndView onSubmit(Object a) throws Exception {
		Adviser adviser = (Adviser) a;
    	ModelAndView mav = new ModelAndView(super.getSuccessView());
        this.projectDao.updateAdviser(adviser);
		mav.setViewName("redirect");
		mav.addObject("pathAndQuerystring", "viewadviser?id=" + adviser.getId());
		mav.addObject("proxy", this.proxy);
		return mav;
	}
	
	@Override
	protected Object formBackingObject(HttpServletRequest request) throws Exception {
		Adviser a = null;
		Integer id = Integer.valueOf(request.getParameter("id"));
		try {
  		    a = this.projectDao.getAdviserById(id);
		} catch (Exception e) {
			throw new ServletException(e);
		}
		return a;
	}
	
	public void setProjectDao(ProjectDao projectDao) {
		this.projectDao = projectDao;
	}

	public void setProxy(String proxy) {
		this.proxy = proxy;
	}
	

}
