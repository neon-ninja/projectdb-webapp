package pm.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import pm.db.ProjectDao;

public class DeleteResearchOutputController extends AbstractController {
	
	private Log log = LogFactory.getLog(DeleteResearchOutputController.class.getName()); 
	private ProjectDao projectDao;
	private String successView;
	private String proxy;

	public ModelAndView handleRequestInternal(HttpServletRequest request,
		HttpServletResponse response) throws Exception {
    	ModelAndView mav = new ModelAndView(this.successView);
        Integer researchOutputId = Integer.valueOf(request.getParameter("researchOutputId"));
    	Integer projectId = Integer.valueOf(request.getParameter("projectId"));
		mav.addObject("id", projectId);
		mav.addObject("proxy", this.proxy);
    	projectDao.deleteResearchOutput(projectId, researchOutputId);
		return mav;
	}
	
	public void setProjectDao(ProjectDao projectDao) {
		this.projectDao = projectDao;
	}

	public void setSuccessView(String successView) {
		this.successView = successView;
	}

	public void setProxy(String proxy) {
		this.proxy = proxy;
	}

}
