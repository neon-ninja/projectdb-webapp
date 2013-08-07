package pm.util;

import java.util.LinkedList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;

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

public class Util {

	private Log log = LogFactory.getLog(Thread.currentThread().getClass()); 

	public void addProjectInfosToMav(ModelAndView mav, ProjectDao dao, Integer projectId) throws Exception {
		Project p = dao.getProjectById(projectId);
        List<RPLink> rplList = dao.getAllRPLinksForProjectId(projectId);
        List<APLink> aplList = dao.getAllAPLinksForProjectId(projectId);
        List<Researcher> researcherList = new LinkedList<Researcher>();
        List<Advisor> advisorList = new LinkedList<Advisor>();
        List<Review> reviewList = dao.getAllReviewsForProjectId(projectId);        
        List<FollowUp> followUpList = dao.getAllFollowUpsForProjectId(projectId);
        List<ResearchOutput> rOutputList = dao.getAllResearchOutputsForProjectId(projectId);
        List<Attachment> aList = dao.getAllAttachmentsForProjectId(projectId);
        List<AdvisorAction> aActionList = dao.getAllAdvisorActionsForProjectId(projectId);
        List<ProjectKpi> projectKpis = dao.getAllKpisForProjectId(projectId);
        List<Facility> facilityList = dao.getAllFacilitiesOnProject(projectId);
		List<ProjectType> projectTypes = dao.getAllProjectTypes();
		List<Site> sites = dao.getAllSites();

        for (APLink apl: aplList) {
        	advisorList.add(dao.getAdvisorById(apl.getAdvisorId()));
        }
        for (RPLink rpl: rplList) {
        	researcherList.add(dao.getResearcherById(rpl.getResearcherId()));
        }

		mav.addObject("project", p);
		mav.addObject("apls", aplList);
		mav.addObject("rpls", rplList);
		mav.addObject("advisors", advisorList);
		mav.addObject("researchers", researcherList);
		mav.addObject("reviews", reviewList);
		mav.addObject("followUps", followUpList);
		mav.addObject("researchOutputs", rOutputList);
		mav.addObject("attachments", aList);
		mav.addObject("advisorActions", aActionList);
		mav.addObject("facilities", facilityList);
		mav.addObject("sites", sites);
		mav.addObject("projectKpis", projectKpis);
		mav.addObject("projectTypes", projectTypes);
	}
}
