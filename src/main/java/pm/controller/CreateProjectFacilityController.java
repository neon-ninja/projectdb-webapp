package pm.controller;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

import pm.db.ProjectDao;
import pm.pojo.Facility;
import pm.pojo.ProjectFacility;
import pm.pojo.ProjectWrapper;
import pm.temp.TempProjectManager;

public class CreateProjectFacilityController extends SimpleFormController {

	private Log log = LogFactory.getLog(CreateProjectFacilityController.class.getName()); 
	private ProjectDao projectDao;
	private TempProjectManager tempProjectManager;
	private String proxy;
	
	@Override
	public ModelAndView onSubmit(Object o) throws Exception {
		ProjectFacility pf = (ProjectFacility) o;
    	Integer projectId = pf.getProjectId(); 
    	ModelAndView mav = new ModelAndView();
    	ProjectWrapper pw = this.tempProjectManager.get(projectId);
    	pf.setFacilityName(this.projectDao.getFacilityById(pf.getFacilityId()).getName());
    	pw.getProjectFacilities().add(pf);
    	this.tempProjectManager.update(projectId, pw);
		mav.setViewName("redirect");
		mav.addObject("pathAndQuerystring", "editproject?id=" + projectId + "#facilities");
		mav.addObject("proxy", this.proxy);
		return mav;
	}

	@Override
    protected Map referenceData(HttpServletRequest request) throws Exception {
		ModelMap modelMap = new ModelMap();
		Integer pid = Integer.valueOf(request.getParameter("id"));
    	ProjectWrapper pw = this.tempProjectManager.get(pid);
    	List<Integer> l = new LinkedList<Integer>();
    	for (ProjectFacility pf: pw.getProjectFacilities()) {
    		l.add(pf.getFacilityId());
    	}
    	List<Facility> fs = this.projectDao.getFacilitiesNotOnList(l);
		HashMap<Integer,String> facilitiesNotOnProject = new LinkedHashMap<Integer, String>();
		if (fs != null) {
			for (Facility f: fs) {
				facilitiesNotOnProject.put(f.getId(), f.getName());
			}
		}
		modelMap.put("pid", pid);
        modelMap.put("pfNotOnProject", facilitiesNotOnProject);
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
