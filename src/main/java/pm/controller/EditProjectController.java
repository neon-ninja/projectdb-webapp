package pm.controller;

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
import pm.pojo.APLink;
import pm.pojo.Advisor;
import pm.pojo.AdvisorAction;
import pm.pojo.Attachment;
import pm.pojo.Facility;
import pm.pojo.FollowUp;
import pm.pojo.Project;
import pm.pojo.ProjectKpi;
import pm.pojo.ProjectType;
import pm.pojo.RPLink;
import pm.pojo.ResearchOutput;
import pm.pojo.Researcher;
import pm.pojo.Review;
import pm.pojo.Site;
import pm.util.Util;

public class EditProjectController extends SimpleFormController {
	
	private Log log = LogFactory.getLog(EditProjectController.class.getName()); 
	private ProjectDao projectDao;
	private String proxy;

	@Override
	public ModelAndView onSubmit(Object o) throws Exception {
		Project p = (Project) o;
		Integer projectId = p.getId();
		ModelAndView mav = new ModelAndView(super.getSuccessView());
		mav.addObject("id", projectId);
		mav.addObject("proxy", this.proxy);
		this.projectDao.updateProject(projectId, p);
		new Util().addProjectInfosToMav(mav, this.projectDao, projectId);
		return mav;
	}
	
	@Override
	protected Object formBackingObject(HttpServletRequest request) throws ServletException {
		Project p = new Project();
		Integer id = Integer.valueOf(request.getParameter("id"));
		try {
  		    p = this.projectDao.getProjectById(id);
		} catch (Exception e) {
			throw new ServletException(e);
		}
		return p;
	}
	
	@Override
    protected Map referenceData(HttpServletRequest request) throws Exception {
		ModelMap modelMap = new ModelMap();
		Integer pid = Integer.valueOf(request.getParameter("id"));
		List<Site> sitesTmp = this.projectDao.getAllSites();
		List<ProjectType> pTypesTmp = this.projectDao.getAllProjectTypes();
		
        List<RPLink> rplList = projectDao.getAllRPLinksForProjectId(pid);
        List<APLink> aplList = projectDao.getAllAPLinksForProjectId(pid);
		
		List<Review> reviews = this.projectDao.getAllReviewsForProjectId(pid);
		List<FollowUp> followUps = this.projectDao.getAllFollowUpsForProjectId(pid);
		List<ResearchOutput> researchOutputs = this.projectDao.getAllResearchOutputsForProjectId(pid);
		List<Attachment> attachments = this.projectDao.getAllAttachmentsForProjectId(pid);
		List<AdvisorAction> advisorActions = this.projectDao.getAllAdvisorActionsForProjectId(pid);
		List<ProjectKpi> projectKpis = this.projectDao.getAllKpisForProjectId(pid);
		List<Facility> facilitiesOnProject = this.projectDao.getAllFacilitiesOnProject(pid);
		List<Facility> facilitiesNotOnProject = this.projectDao.getAllFacilitiesNotOnProject(pid);
		Map<Integer,String> sites = new LinkedHashMap<Integer,String>();
		Map<Integer,String> pTypes = new LinkedHashMap<Integer,String>();
		List<Researcher> researcherList = new LinkedList<Researcher>();
		List<Advisor> advisorList = new LinkedList<Advisor>();
		
        for (APLink apl: aplList) {
        	advisorList.add(projectDao.getAdvisorById(apl.getAdvisorId()));
        }
        for (RPLink rpl: rplList) {
        	researcherList.add(projectDao.getResearcherById(rpl.getResearcherId()));
        }

		if (sitesTmp != null) {
			for (Site s : sitesTmp) {
				sites.put(s.getId(), s.getName());
			}
		}
		if (pTypesTmp != null) {
			for (ProjectType pt : pTypesTmp) {
				pTypes.put(pt.getId(), pt.getName());
			}
		}
		
        modelMap.put("sites", sites);
        modelMap.put("projectTypes", pTypes);
        modelMap.put("researchers", researcherList);
        modelMap.put("advisors", advisorList);
		modelMap.put("apls", aplList);
		modelMap.put("rpls", rplList);
        modelMap.put("reviews", reviews);
        modelMap.put("followUps", followUps);
        modelMap.put("researchOutputs", researchOutputs);
        modelMap.put("attachments", attachments);
        modelMap.put("advisorActions", advisorActions);
        modelMap.put("projectKpis", projectKpis);
        modelMap.put("facilitiesOnProject", facilitiesOnProject);
        modelMap.put("facilitiesNotOnProject", facilitiesNotOnProject);
        return modelMap;
    }

	public void setProjectDao(ProjectDao projectDao) {
		this.projectDao = projectDao;
	}

	public void setProxy(String proxy) {
		this.proxy = proxy;
	}

}
