package pm.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import pm.db.ProjectDao;

public class DeleteResearcherController extends AbstractController {
	
	private Log log = LogFactory.getLog(DeleteResearcherController.class.getName()); 
	private ProjectDao projectDao;
	private String proxy;

	public ModelAndView handleRequestInternal(HttpServletRequest request,
		HttpServletResponse response) throws Exception {
		Integer id = Integer.getInteger(request.getParameter("id"));
    	ModelAndView mav = new ModelAndView();
    	this.projectDao.deleteResearcher(id);
		mav.setViewName("redirect");
		mav.addObject("pathAndQuerystring", "viewresearchers");
		mav.addObject("proxy", this.proxy);
		return mav;    	
	}
	
	public void setProjectDao(ProjectDao projectDao) {
		this.projectDao = projectDao;
	}

	public void setProxy(String proxy) {
		this.proxy = proxy;
	}

}
