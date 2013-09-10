package pm.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

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
import pm.util.Util;

public class CreateResearchOutputController extends SimpleFormController {

	private Log log = LogFactory.getLog(CreateResearchOutputController.class.getName()); 
	private ProjectDao projectDao;
	private TempProjectManager tempProjectManager;
	private String proxy;
	private Random random = new Random();
	
	@Override
	public ModelAndView onSubmit(Object o) throws Exception {
		ResearchOutput r = (ResearchOutput) o;
		Integer projectId = r.getProjectId();
    	ModelAndView mav = new ModelAndView(super.getSuccessView());
    	ProjectWrapper pw = this.tempProjectManager.get(projectId);
    	r.setId(random.nextInt());
    	r.setType(this.projectDao.getResearchOutputTypeById(r.getTypeId()).getName());
    	pw.getResearchOutputs().add(r);
    	this.tempProjectManager.update(projectId, pw);
		mav.setViewName("redirect");
		mav.addObject("pathAndQuerystring", "editproject?id=" + projectId + "#outputs");
		mav.addObject("proxy", this.proxy);
		return mav;
	}

	@Override
	protected Object formBackingObject(HttpServletRequest request) throws Exception {
		ResearchOutput r = new ResearchOutput();
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		r.setDate(df.format(new Date()));
		return r;
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
		modelMap.put("pid", Integer.valueOf(request.getParameter("id")));
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
