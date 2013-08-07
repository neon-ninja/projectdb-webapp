package pm.controller;

import java.util.HashMap;
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
import pm.pojo.Facility;
import pm.pojo.ProjectFacility;
import pm.util.Util;

public class CreateProjectFacilityController extends SimpleFormController {

	private static Log log = LogFactory.getLog(Thread.currentThread().getClass()); 
	private ProjectDao projectDao;
	
	@Override
	public ModelAndView onSubmit(Object o) throws ServletException {
		ProjectFacility pf = (ProjectFacility) o;
    	Integer pid = pf.getProjectId(); 
    	ModelAndView mav = new ModelAndView(super.getSuccessView());
    	mav.addObject("id", pid);
		try {
			this.projectDao.createProjectFacility(pf);
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
		List<Facility> fNotOnProjectTmp = this.projectDao.getAllFacilitiesNotOnProject(pid);
		HashMap<Integer,String> facilitiesNotOnProject = new LinkedHashMap<Integer, String>();
		if (fNotOnProjectTmp != null) {
			for (Facility f: fNotOnProjectTmp) {
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

}
