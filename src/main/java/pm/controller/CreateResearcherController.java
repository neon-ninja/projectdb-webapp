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
import pm.pojo.InstitutionalRole;
import pm.pojo.Researcher;

public class CreateResearcherController extends SimpleFormController {

	private static Log log = LogFactory.getLog(Thread.currentThread().getClass()); 
	private ProjectDao projectDao;
	private String profileDefaultPicture;
	
	@Override
	public ModelAndView onSubmit(Object r) throws ServletException {
		Researcher researcher = (Researcher) r;
    	ModelAndView mav = new ModelAndView(super.getSuccessView());
		try {
            researcher.setId(this.projectDao.createResearcher(researcher));
    		InstitutionalRole ir = (InstitutionalRole) projectDao.getInstitutionalRoleById(researcher.getInstitutionalRoleId());
    		researcher.setInstitutionalRole(ir.getName());
		} catch (Exception e) {
        	throw new ServletException(e);
        }
		mav.addObject("researcher", researcher);
		return mav;
	}
	
	@Override
    protected Map referenceData(HttpServletRequest request) throws Exception {
		ModelMap modelMap = new ModelMap();
		List<InstitutionalRole> iRolesTmp = this.projectDao.getAllInstitutionalRoles();
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
	protected Object formBackingObject(HttpServletRequest request) throws ServletException {
		Researcher r = new Researcher();
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		r.setStartDate(df.format(new Date()));
		r.setPictureUrl(this.profileDefaultPicture);
		return r;
	}

	public void setProjectDao(ProjectDao projectDao) {
		this.projectDao = projectDao;
	}

	public void setProfileDefaultPicture(String profileDefaultPicture) {
		this.profileDefaultPicture = profileDefaultPicture;
	}
	
}
