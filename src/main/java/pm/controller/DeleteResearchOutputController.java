package pm.controller;

import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import pm.db.ProjectDao;
import pm.pojo.ProjectWrapper;
import pm.pojo.ResearchOutput;
import pm.temp.TempProjectManager;

public class DeleteResearchOutputController extends AbstractController {
	
	private Log log = LogFactory.getLog(DeleteResearchOutputController.class.getName()); 
	private ProjectDao projectDao;
	private TempProjectManager tempProjectManager;
	private String proxy;

	public ModelAndView handleRequestInternal(HttpServletRequest request,
		HttpServletResponse response) throws Exception {
    	ModelAndView mav = new ModelAndView();
        Integer researchOutputId = Integer.valueOf(request.getParameter("researchOutputId"));
    	Integer projectId = Integer.valueOf(request.getParameter("projectId"));
    	ProjectWrapper pw = this.tempProjectManager.get(projectId);
    	List<ResearchOutput> tmp = new LinkedList<ResearchOutput>();
        for (ResearchOutput ro: pw.getResearchOutputs()) {
        	if (!ro.getId().equals(researchOutputId)) {
        		tmp.add(ro);
        	}
        }
        pw.setResearchOutputs(tmp);
    	this.tempProjectManager.update(projectId, pw);
		mav.setViewName("redirect");
		mav.addObject("pathAndQuerystring", "editproject?id=" + projectId + "#outputs");
		mav.addObject("proxy", this.proxy);
		return mav;
	}
	
	public void setProjectDao(ProjectDao projectDao) {
		this.projectDao = projectDao;
	}

	public void setProxy(String proxy) {
		this.proxy = proxy;
	}

	public void setTempProjectManager(TempProjectManager tempProjectManager) {
		this.tempProjectManager = tempProjectManager;
	}

}
