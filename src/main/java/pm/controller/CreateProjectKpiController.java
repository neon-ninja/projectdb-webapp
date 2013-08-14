package pm.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
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
import pm.pojo.Kpi;
import pm.pojo.ProjectKpi;
import pm.util.Util;

public class CreateProjectKpiController extends SimpleFormController {

	private Log log = LogFactory.getLog(CreateProjectKpiController.class.getName()); 
	private ProjectDao projectDao;
	
	@Override
	public ModelAndView onSubmit(Object o) throws ServletException {
		ProjectKpi pk = (ProjectKpi) o;
		Integer pid = pk.getProjectId();
    	ModelAndView mav = new ModelAndView(super.getSuccessView());
		mav.addObject("id", pid);
		try {
			this.projectDao.createProjectKpi(pk);
			new Util().addProjectInfosToMav(mav, this.projectDao, pid);
		} catch (Exception e) {
        	throw new ServletException(e);
        }
		return mav;
	}

	@Override
	protected Object formBackingObject(HttpServletRequest request) throws ServletException {
		ProjectKpi k = new ProjectKpi();
		k.setProjectId(Integer.getInteger(request.getParameter("id")));
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		k.setDate(df.format(new Date()));
		return k;
	}

	@Override
    protected Map referenceData(HttpServletRequest request) throws Exception {
		ModelMap modelMap = new ModelMap();
		Integer pid = Integer.valueOf(request.getParameter("id"));
		
		List<Kpi> kpis = new LinkedList<Kpi>();
		kpis = this.projectDao.getAllKpis();

		Map<Integer,String> tmpkpis = new HashMap<Integer,String>();
		for (Kpi kpi: kpis) {
			tmpkpis.put(kpi.getId(), kpi.getType() + "-" + kpi.getId() + ": " + kpi.getTitle());
		}
		
		List<Advisor> advisorsTmp = this.projectDao.getAllAdvisors();
		Map<Integer,String> advisors = new LinkedHashMap<Integer,String>();
		if (advisorsTmp != null) {
			for (Advisor a : advisorsTmp) {
				advisors.put(a.getId(),a.getFullName());
			}
		}
        modelMap.put("advisors", advisors);
		modelMap.put("kpis", tmpkpis);
		modelMap.put("projectId", request.getParameter("id"));
        return modelMap;
    }

	public void setProjectDao(ProjectDao projectDao) {
		this.projectDao = projectDao;
	}

}
