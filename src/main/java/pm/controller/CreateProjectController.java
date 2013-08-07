package pm.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
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
import pm.pojo.APLink;
import pm.pojo.Advisor;
import pm.pojo.AdvisorRole;
import pm.pojo.CreateProject;
import pm.pojo.Facility;
import pm.pojo.Project;
import pm.pojo.ProjectFacility;
import pm.pojo.ProjectType;
import pm.pojo.RPLink;
import pm.pojo.Researcher;
import pm.pojo.ResearcherRole;
import pm.pojo.Site;
import pm.util.Util;

public class CreateProjectController extends SimpleFormController {

	private static Log log = LogFactory.getLog(Thread.currentThread().getClass()); 
	private ProjectDao projectDao;
	
	@Override
	public ModelAndView onSubmit(Object cp) throws Exception {
		CreateProject createProject = (CreateProject) cp;
		Project project = createProject.getProject();
		RPLink rpLink = createProject.getRpLink();
		APLink apLink = createProject.getApLink();
		ProjectFacility pf = createProject.getHpcFacility();
		Integer pid = -1;
		
    	ModelAndView mav = new ModelAndView(super.getSuccessView());
		try {
		    pid = this.projectDao.createProject(project);
			project.setId(pid);
			rpLink.setProjectId(pid);
			apLink.setProjectId(pid);
			pf.setProjectId(pid);
			this.projectDao.createRPLink(rpLink);
			this.projectDao.createAPLink(apLink);
			this.projectDao.createProjectFacility(pf);
		} catch (Exception e) {
        	throw new ServletException(e);
        }
		new Util().addProjectInfosToMav(mav, this.projectDao, pid);
		return mav;
	}

	@Override
    protected Map referenceData(HttpServletRequest request) throws Exception {
		ModelMap modelMap = new ModelMap();
		List<Site> sitesTmp = this.projectDao.getAllSites();
		List<ProjectType> projectTypesTmp = this.projectDao.getAllProjectTypes();
		List<Researcher> researcherTmp = this.projectDao.getAllResearchers();
		List<Advisor> advisorTmp = this.projectDao.getAllAdvisors();
		List<ResearcherRole> researcherRolesTmp = this.projectDao.getAllResearcherRoles();
		List<AdvisorRole> advisorRolesTmp = this.projectDao.getAllAdvisorRoles();
		List<Facility> facilitiesTmp = this.projectDao.getAllFacilities();
		Map<Integer,String> sites = new LinkedHashMap<Integer,String>();
		Map<Integer,String> projectTypes = new LinkedHashMap<Integer,String>();
		Map<Integer,String> advisorRoles = new LinkedHashMap<Integer,String>();
		Map<Integer,String> researcherRoles = new LinkedHashMap<Integer,String>();
		Map<Integer,String> facilities = new LinkedHashMap<Integer,String>();
		Map<Integer,String> researchers = new LinkedHashMap<Integer,String>();
		Map<Integer,String> advisors = new LinkedHashMap<Integer,String>();
		
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
		if (advisorTmp != null) {
			for (Advisor a : advisorTmp) {
				advisors.put(a.getId(), a.getFullName());
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
		if (advisorRolesTmp != null) {
			for (AdvisorRole ar : advisorRolesTmp) {
				if (ar.getName().equals("Primary Advisor")) {
					advisorRoles.put(ar.getId(), ar.getName());
				}
			}
		}
		if (researcherRolesTmp != null) {
			for (ResearcherRole rr : researcherRolesTmp) {
				researcherRoles.put(rr.getId(), rr.getName());
			}
		}
		
        modelMap.put("sites", sites);
        modelMap.put("projectTypes", projectTypes);
        modelMap.put("advisors", advisors);
        modelMap.put("researchers", researchers);
        modelMap.put("researcherRoles", researcherRoles);
        modelMap.put("advisorRoles", advisorRoles);
        modelMap.put("hpcFacilities", facilities);

        return modelMap;
    }
	
	@Override
	protected Object formBackingObject(HttpServletRequest request) throws ServletException {
		CreateProject cp = new CreateProject();
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
		p.setNotes("Job submission characteristics prior to using NeSI facilities:<br>\nNumber CPU Cores used: N/A<br>\nAmount of memory[GB] used: N/A<br>\nNumber Concurrent Jobs: N/A<br>\n");
		cp.setProject(p);
		cp.setRpLink(new RPLink());
		cp.setApLink(new APLink());
		cp.setHpcFacility(new ProjectFacility());
		return cp;
	}

	public void setProjectDao(ProjectDao projectDao) {
		this.projectDao = projectDao;
	}

}
