package pm.controller;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

import pm.db.ProjectDao;
import pm.pojo.ProjectWrapper;
import pm.pojo.ResearchOutput;
import pm.pojo.ResearchOutputType;
import pm.temp.TempProjectManager;

public class EditResearchOutputController extends SimpleFormController {

	private Log log = LogFactory.getLog(EditResearchOutputController.class.getName()); 
	private ProjectDao projectDao;
	private TempProjectManager tempProjectManager;
	private String proxy;
	
	@Override
	public ModelAndView onSubmit(Object o) throws Exception {
		ResearchOutput r = (ResearchOutput) o;
		r.setType(this.projectDao.getResearchOutputTypeById(r.getTypeId()).getName());
		Integer projectId = r.getProjectId();
    	ModelAndView mav = new ModelAndView(super.getSuccessView());
    	ProjectWrapper pw = this.tempProjectManager.get(projectId);
    	for (int i=0;i<pw.getResearchOutputs().size();i++) {
    		if (pw.getResearchOutputs().get(i).getId().equals(r.getId())) {
    			pw.getResearchOutputs().set(i, r);
    		}
    	}
    	this.tempProjectManager.update(projectId, pw);
		mav.setViewName("redirect");
		mav.addObject("pathAndQuerystring", "editproject?id=" + projectId + "#outputs");
		mav.addObject("proxy", this.proxy);
		return mav;
	}

	@Override
	protected Object formBackingObject(HttpServletRequest request) throws Exception {
		Integer projectId = Integer.valueOf(request.getParameter("projectId"));
		Integer id = Integer.valueOf(request.getParameter("researchOutputId"));
		ProjectWrapper pw = this.tempProjectManager.get(projectId);
		for (ResearchOutput ro:pw.getResearchOutputs()) {
			if (ro.getId().equals(id)) return ro;
		}
		return null;
	}

	@Override
    protected Map referenceData(HttpServletRequest request) throws Exception {
		ModelMap modelMap = new ModelMap();
		List<ResearchOutputType> researchOutputTypesTmp = this.projectDao.getResearchOutputTypes();
		HashMap<Integer,String> researchOutputTypes = new LinkedHashMap<Integer, String>();
		if (researchOutputTypesTmp != null) {
			for (ResearchOutputType rot: researchOutputTypesTmp) {
				researchOutputTypes.put(rot.getId(), rot.getName());
			}
		}
		modelMap.put("pid", Integer.valueOf(request.getParameter("projectId")));
		modelMap.put("researchOutputTypes", researchOutputTypes);
        return modelMap;
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
