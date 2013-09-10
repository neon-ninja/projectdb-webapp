package pm.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
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
import pm.pojo.Adviser;
import pm.pojo.AdviserRole;
import pm.pojo.Facility;
import pm.pojo.Project;
import pm.pojo.ProjectType;
import pm.pojo.Researcher;
import pm.pojo.ResearcherRole;
import pm.pojo.Site;
import pm.util.Util;

public class CreateProjectController extends SimpleFormController {

	private Log log = LogFactory.getLog(CreateProjectController.class.getName()); 
	private ProjectDao projectDao;
	
	@Override
	public ModelAndView onSubmit(Object cp) throws Exception {
		Integer projectId = -1;		
    	ModelAndView mav = new ModelAndView(super.getSuccessView());
    	// TODO
		new Util().addProjectInfosToMav(mav, this.projectDao, projectId);
		return mav;
	}

	@Override
    protected Map referenceData(HttpServletRequest request) throws Exception {
		ModelMap modelMap = new ModelMap();
		List<Site> sitesTmp = this.projectDao.getSites();
		List<ProjectType> projectTypesTmp = this.projectDao.getProjectTypes();
		List<Researcher> researcherTmp = this.projectDao.getResearchers();
		List<Adviser> adviserTmp = this.projectDao.getAdvisers();
		List<ResearcherRole> researcherRolesTmp = this.projectDao.getResearcherRoles();
		List<AdviserRole> adviserRolesTmp = this.projectDao.getAdviserRoles();
		List<Facility> facilitiesTmp = this.projectDao.getFacilities();
		Map<Integer,String> sites = new LinkedHashMap<Integer,String>();
		Map<Integer,String> projectTypes = new LinkedHashMap<Integer,String>();
		Map<Integer,String> adviserRoles = new LinkedHashMap<Integer,String>();
		Map<Integer,String> researcherRoles = new LinkedHashMap<Integer,String>();
		Map<Integer,String> facilities = new LinkedHashMap<Integer,String>();
		Map<Integer,String> researchers = new LinkedHashMap<Integer,String>();
		Map<Integer,String> advisers = new LinkedHashMap<Integer,String>();
		
		if (projectTypesTmp != null) {
			for (ProjectType t : projectTypesTmp) {
				projectTypes.put(t.getId(),t.getName());
			}
		}
		if (researcherTmp != null) {
			for (Researcher r : researcherTmp) {
				researchers.put(r.getId(), r.getFullName());
			}
		}
		if (adviserTmp != null) {
			for (Adviser a : adviserTmp) {
				advisers.put(a.getId(), a.getFullName());
			}
		}
		if (facilitiesTmp != null) {
			for (Facility f : facilitiesTmp) {
				facilities.put(f.getId(), f.getName());
			}
		}
		if (sitesTmp != null) {
			for (Site s : sitesTmp) {
				sites.put(s.getId(),s.getName());
			}
		}
		if (adviserRolesTmp != null) {
			for (AdviserRole ar : adviserRolesTmp) {
				if (ar.getName().equals("Primary Adviser")) {
					adviserRoles.put(ar.getId(), ar.getName());
				}
			}
		}
		if (researcherRolesTmp != null) {
			for (ResearcherRole rr : researcherRolesTmp) {
				if (rr.getName().equals("PI")) {
				    researcherRoles.put(rr.getId(), rr.getName());
				}
			}
		}
		
        modelMap.put("sites", sites);
        modelMap.put("projectTypes", projectTypes);
        modelMap.put("advisers", advisers);
        modelMap.put("researchers", researchers);
        modelMap.put("researcherRoles", researcherRoles);
        modelMap.put("adviserRoles", adviserRoles);
        modelMap.put("hpcFacilities", facilities);

        return modelMap;
    }
	
	@Override
	protected Object formBackingObject(HttpServletRequest request) throws Exception {
		Project p = new Project();
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Date startDate = new Date();
		Date nextReview = new Date();
		Date nextFollowUp = new Date();
		nextReview.setYear(nextReview.getYear() + 1);
		nextFollowUp.setMonth(nextFollowUp.getMonth() + 3);
		p.setStartDate(df.format(startDate));
		p.setNextFollowUpDate(df.format(nextFollowUp));
		p.setNextReviewDate(df.format(nextReview));
		return p;
	}

	public void setProjectDao(ProjectDao projectDao) {
		this.projectDao = projectDao;
	}

}
