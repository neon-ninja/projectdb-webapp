package pm.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import pm.pojo.Adviser;
import pm.pojo.Kpi;
import pm.pojo.KpiCode;
import pm.pojo.ProjectKpi;
import pm.pojo.ProjectWrapper;

@Controller
public class ProjectKpisController extends GlobalController {
	@RequestMapping(value = "viewprojectkpis", method = RequestMethod.GET)
	public ModelAndView viewprojectkpis() throws Exception {
    	ModelAndView mav = new ModelAndView();
    	mav.addObject("projectKpis", projectDao.getProjectKpis());
		return mav;
	}
	@RequestMapping(value = "editprojectkpi", method = RequestMethod.GET)
	public ModelAndView edit(Integer projectId, Integer id) throws Exception {
		ProjectWrapper pw = this.tempProjectManager.get(projectId);
		ProjectKpi k = new ProjectKpi();
		k.setProjectId(projectId);
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		k.setDate(df.format(new Date()));
		ModelAndView mav = new ModelAndView();
		for (ProjectKpi pk:pw.getProjectKpis()) {
			if (pk.getId().equals(id)) k = pk;
		}
		List<Kpi> kpis = new LinkedList<Kpi>();
		// Types (NESI-8, NESI-9)
		kpis = this.projectDao.getKpis();
		Map<Integer,String> tmpkpis = new HashMap<Integer,String>();
		for (Kpi kpi: kpis) {
			tmpkpis.put(kpi.getId(), kpi.getType() + "-" + kpi.getId() + ": " + kpi.getTitle());
		}
		// For NESI-9 : throughput, cpucores etc
		List<KpiCode> codes = this.projectDao.getKpiCodes();
		Map<Integer,String> tmpcodes = new HashMap<Integer,String>();
		for (KpiCode c: codes) {
			tmpcodes.put(c.getId(), c.getCode());
		}
		mav.addObject("projectkpi", k);
		mav.addObject("adviserId", k.getAdviserId());
		mav.addObject("kpis", tmpkpis);
		mav.addObject("codes", tmpcodes);
        return mav;
		
	}
	@RequestMapping(value = "editprojectkpi", method = RequestMethod.POST)
	public RedirectView editPost(ProjectKpi pk) throws Exception {
		Integer projectId = pk.getProjectId();
    	ProjectWrapper pw = this.tempProjectManager.get(projectId);
    	pk.setKpiTitle(this.projectDao.getKpiById(pk.getKpiId()).getTitle());
    	pk.setKpiType(this.projectDao.getKpiById(pk.getKpiId()).getType());
    	if (pk.getKpiId().equals(9)) {
    		pk.setCodeName(this.projectDao.getKpiCodeNameById(pk.getCode()));
    	} else {
    		pk.setCode(0);
    	}
    	if (pk.getId()==null) {
			Adviser a = this.projectDao.getAdviserByTuakiriUniqueId(this.getTuakiriUniqueIdFromRequest());
			pk.setAdviserId(a.getId());
			pk.setAdviserName(a.getFullName());
			pk.setId(random.nextInt());
	    	pw.getProjectKpis().add(pk);
		} else {
			for (int i=0;i<pw.getProjectKpis().size();i++) {
	    		if (pw.getProjectKpis().get(i).getId().equals(pk.getId())) {
	    			pk.setAdviserName(this.projectDao.getAdviserById(pk.getAdviserId()).getFullName());
	    			pw.getProjectKpis().set(i, pk);
	    		}
	    	}
		}
    	this.tempProjectManager.update(projectId, pw);
		return new RedirectView("editproject?id=" + projectId + "#kpis");
	}
	@RequestMapping(value = "deleteprojectkpi", method = RequestMethod.GET)
	public RedirectView delete(Integer id, Integer projectId) throws Exception {
    	ProjectWrapper pw = this.tempProjectManager.get(projectId);
    	List<ProjectKpi> tmp = new LinkedList<ProjectKpi>();
        for (ProjectKpi pk: pw.getProjectKpis()) {
        	if (!pk.getId().equals(id)) {
        		tmp.add(pk);
        	}
        }
        pw.setProjectKpis(tmp);
    	this.tempProjectManager.update(projectId, pw);
		return new RedirectView("editproject?id=" + projectId + "#kpis");
	}
}
