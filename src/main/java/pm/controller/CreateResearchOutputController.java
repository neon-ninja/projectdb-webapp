package pm.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
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
import pm.pojo.ResearchOutput;
import pm.pojo.ResearchOutputType;
import pm.util.Util;

public class CreateResearchOutputController extends SimpleFormController {

	private Log log = LogFactory.getLog(CreateResearchOutputController.class.getName()); 
	private ProjectDao projectDao;
	private String proxy;
	
	@Override
	public ModelAndView onSubmit(Object o) throws Exception {
		ResearchOutput r = (ResearchOutput) o;
    	Integer projectId = r.getProjectId(); 
    	ModelAndView mav = new ModelAndView(super.getSuccessView());
		mav.addObject("id", projectId);
		mav.addObject("proxy", this.proxy);
		this.projectDao.createResearchOutput(projectId, r);
		new Util().addProjectInfosToMav(mav, this.projectDao, projectId);
		return mav;
	}

	@Override
	protected Object formBackingObject(HttpServletRequest request) throws ServletException {
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

}
