package pm.controller;

import java.util.Random;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.ui.ModelMap;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

import pm.authz.AuthzAspect;
import pm.db.ProjectDao;
import pm.pojo.Adviser;
import pm.pojo.Kpi;
import pm.pojo.ProjectKpi;
import pm.pojo.ProjectWrapper;
import pm.temp.TempProjectManager;

public class EditProjectKpiController extends SimpleFormController {

	private Log log = LogFactory.getLog(EditProjectKpiController.class.getName()); 
	private ProjectDao projectDao;
	private TempProjectManager tempProjectManager;
	private String proxy;
	private Random random = new Random();
	private AuthzAspect authzAspect;
	
	@Override
	public ModelAndView onSubmit(Object o) throws Exception {
		ProjectKpi pk = (ProjectKpi) o;
		Integer projectId = pk.getProjectId();
    	ModelAndView mav = new ModelAndView();
    	ProjectWrapper pw = this.tempProjectManager.get(projectId);
    	for (int i=0;i<pw.getProjectKpis().size();i++) {
    		if (pw.getProjectKpis().get(i).getId().equals(pk.getId())) {
    			pw.getProjectKpis().set(i, pk);
    		}
    	}
    	this.tempProjectManager.update(projectId, pw);
		mav.setViewName("redirect");
		mav.addObject("pathAndQuerystring", "editproject?id=" + projectId + "#kpis");
		mav.addObject("proxy", this.proxy);
		return mav;
	}

	@Override
	protected Object formBackingObject(HttpServletRequest request) throws Exception {
		Integer projectId = Integer.valueOf(request.getParameter("projectId"));
		Integer id = Integer.valueOf(request.getParameter("id"));
		ProjectWrapper pw = this.tempProjectManager.get(projectId);
		for (ProjectKpi pk:pw.getProjectKpis()) {
			if (pk.getId().equals(id)) return pk;
		}
		return null;
	}
	
	@Override
    protected Map referenceData(HttpServletRequest request) throws Exception {
		ModelMap modelMap = new ModelMap();
		
		List<Kpi> kpis = new LinkedList<Kpi>();
		kpis = this.projectDao.getKpis();

		Map<Integer,String> tmpkpis = new HashMap<Integer,String>();
		for (Kpi kpi: kpis) {
			tmpkpis.put(kpi.getId(), kpi.getType() + "-" + kpi.getId() + ": " + kpi.getTitle());
		}
		modelMap.put("kpis", tmpkpis);
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

	public void setAuthzAspect(AuthzAspect authzAspect) {
		this.authzAspect = authzAspect;
	}

}
