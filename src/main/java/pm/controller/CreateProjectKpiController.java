package pm.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.ui.ModelMap;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;

import pm.db.ProjectDao;
import pm.pojo.Adviser;
import pm.pojo.Kpi;
import pm.pojo.KpiCode;
import pm.pojo.ProjectKpi;
import pm.pojo.ProjectWrapper;
import pm.temp.TempProjectManager;

public class CreateProjectKpiController extends GlobalController {

	private Log log = LogFactory.getLog(CreateProjectKpiController.class.getName()); 
	private ProjectDao projectDao;
	private TempProjectManager tempProjectManager;
	private String proxy;
	private String remoteUserHeader;
	private Random random = new Random();
	
	@Override
	public ModelAndView onSubmit(Object o) throws Exception {
		ProjectKpi pk = (ProjectKpi) o;
		Integer projectId = pk.getProjectId();
    	ModelAndView mav = new ModelAndView();
    	ProjectWrapper pw = this.tempProjectManager.get(projectId);
    	pk.setId(random.nextInt());
    	pk.setKpiTitle(this.projectDao.getKpiById(pk.getKpiId()).getTitle());
    	pk.setKpiType(this.projectDao.getKpiById(pk.getKpiId()).getType());
    	if (pk.getKpiId().equals(9)) {
    		pk.setCodeName(this.projectDao.getKpiCodeNameById(pk.getCode()));
    	} else {
    		pk.setCode(0);
    	}
    	pk.setAdviserName(this.projectDao.getAdviserById(pk.getAdviserId()).getFullName());
    	pw.getProjectKpis().add(pk);
    	this.tempProjectManager.update(projectId, pw);
		mav.setViewName("redirect");
		mav.addObject("pathAndQuerystring", "editproject?id=" + projectId + "#kpis");
		mav.addObject("proxy", this.proxy);
		return mav;
	}

	@Override
	protected Object formBackingObject(HttpServletRequest request) throws Exception {
		ProjectKpi k = new ProjectKpi();
		k.setProjectId(Integer.valueOf(request.getParameter("id")));
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		k.setDate(df.format(new Date()));
		return k;
	}

	@Override
    protected Map<String,Object> referenceData(HttpServletRequest request) throws Exception {
		ModelMap modelMap = new ModelMap();
		
		List<Kpi> kpis = new LinkedList<Kpi>();
		kpis = this.projectDao.getKpis();

		Map<Integer,String> tmpkpis = new HashMap<Integer,String>();
		for (Kpi kpi: kpis) {
			tmpkpis.put(kpi.getId(), kpi.getType() + "-" + kpi.getId() + ": " + kpi.getTitle());
		}
		
		List<KpiCode> codes = this.projectDao.getKpiCodes();

		Map<Integer,String> tmpcodes = new HashMap<Integer,String>();
		for (KpiCode c: codes) {
			tmpcodes.put(c.getId(), c.getCode());
		}
		
		Adviser a =  this.projectDao.getAdviserByTuakiriUniqueId(this.getTuakiriUniqueIdFromRequest());
        modelMap.put("adviserId", a.getId());
		modelMap.put("kpis", tmpkpis);
		modelMap.put("codes", tmpcodes);
		modelMap.put("projectId", request.getParameter("id"));
        return modelMap;
    }

}
