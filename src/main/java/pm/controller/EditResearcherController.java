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
import pm.pojo.InstitutionalRole;
import pm.pojo.Researcher;

public class EditResearcherController extends SimpleFormController {
	
	private Log log = LogFactory.getLog(EditResearcherController.class.getName()); 
	private ProjectDao projectDao;
	private String proxy;

	@Override
	public ModelAndView onSubmit(Object r) throws Exception {
		Researcher researcher = (Researcher) r;
    	ModelAndView mav = new ModelAndView(super.getSuccessView());
        this.projectDao.updateResearcher(researcher);
		mav.setViewName("redirect");
		mav.addObject("pathAndQuerystring", "viewresearcher?id=" + researcher.getId());
		mav.addObject("proxy", this.proxy);
		return mav;
	}
	
	@Override
    protected Map referenceData(HttpServletRequest request) throws Exception {
		ModelMap modelMap = new ModelMap();
		List<InstitutionalRole> iRolesTmp = this.projectDao.getInstitutionalRoles();
		HashMap<Integer,String> iRoles = new LinkedHashMap<Integer, String>();
		if (iRolesTmp != null) {
			for (InstitutionalRole ir: iRolesTmp) {
				iRoles.put(ir.getId(), ir.getName());
			}
		}
        modelMap.put("institutionalRoles", iRoles);
        return modelMap;
	}

	@Override
	protected Object formBackingObject(HttpServletRequest request) throws Exception {
		Researcher r = null;
		Integer id = Integer.valueOf(request.getParameter("id"));
  		return this.projectDao.getResearcherById(id);
	}
	
	public void setProjectDao(ProjectDao projectDao) {
		this.projectDao = projectDao;
	}

	public void setProxy(String proxy) {
		this.proxy = proxy;
	}
	
}
