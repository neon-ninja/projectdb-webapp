package pm.db;

import java.util.List;

import pm.pojo.APLink;
import pm.pojo.Advisor;
import pm.pojo.AdvisorAction;
import pm.pojo.AdvisorRole;
import pm.pojo.Attachment;
import pm.pojo.Facility;
import pm.pojo.FollowUp;
import pm.pojo.InstitutionalRole;
import pm.pojo.Kpi;
import pm.pojo.Project;
import pm.pojo.ProjectFacility;
import pm.pojo.ProjectKpi;
import pm.pojo.ProjectType;
import pm.pojo.RPLink;
import pm.pojo.ResearchOutput;
import pm.pojo.ResearchOutputType;
import pm.pojo.Researcher;
import pm.pojo.ResearcherRole;
import pm.pojo.Review;
import pm.pojo.Site;

public interface ProjectDao {

	public Project getProjectById(Integer id) throws Exception;
	public Researcher getResearcherById(Integer id) throws Exception;
	public Advisor getAdvisorById(Integer id) throws Exception;
	public Advisor getAdvisorByTuakiriUniqueId(String id) throws Exception;
	public InstitutionalRole getInstitutionalRoleById(Integer id) throws Exception;
	public FollowUp getFollowUpById(Integer id) throws Exception;
	public Review getReviewById(Integer id) throws Exception;
	public Kpi getKpiById(Integer id) throws Exception;
	public AdvisorAction getAdvisorActionById(Integer id) throws Exception;
	public List<Researcher> getAllResearchers() throws Exception;
	public List<ResearchOutputType> getResearchOutputTypes() throws Exception;
	public List<Site> getAllSites() throws Exception;
	public List<Kpi> getAllKpis() throws Exception;
	public List<ProjectType> getAllProjectTypes() throws Exception;
	public List<Project> getAllProjects() throws Exception;
	public List<Advisor> getAllAdvisors() throws Exception;
	public List<Facility> getAllFacilities() throws Exception;
	public List<Researcher> getAllResearchersOnProject(Integer projectId) throws Exception;
	public List<Advisor> getAllAdvisorsOnProject(Integer projectId) throws Exception;
	public List<Facility> getAllFacilitiesOnProject(Integer projectId) throws Exception;
	public List<Researcher> getAllResearchersNotOnProject(Integer projectId) throws Exception;
	public List<Advisor> getAllAdvisorsNotOnProject(Integer projectId) throws Exception;
	public List<Facility> getAllFacilitiesNotOnProject(Integer projectId) throws Exception;
	public List<ResearcherRole> getAllResearcherRoles() throws Exception;
	public List<AdvisorRole> getAllAdvisorRoles() throws Exception;
	public List<InstitutionalRole> getAllInstitutionalRoles() throws Exception;
	public List<RPLink> getAllRPLinksForProjectId(Integer id) throws Exception;
	public List<APLink> getAllAPLinksForProjectId(Integer id) throws Exception;
    public List<Review> getAllReviewsForProjectId(Integer id) throws Exception;
    public List<FollowUp> getAllFollowUpsForProjectId(Integer id) throws Exception;
    public List<ProjectKpi> getAllKpisForProjectId(Integer id) throws Exception;
    public List<ResearchOutput> getAllResearchOutputsForProjectId(Integer id) throws Exception;
    public List<Attachment> getAllAttachmentsForProjectId(Integer id) throws Exception;
    public List<AdvisorAction> getAllAdvisorActionsForProjectId(Integer id) throws Exception;
    public List<Project> getAllProjectsForResearcherId(Integer id) throws Exception;
    public List<Project> getAllProjectsForAdvisorId(Integer id) throws Exception;
    public Integer getNumProjectsForAdvisor(Integer advisorId) throws Exception;
    
	public Integer createProject(Project p) throws Exception;
	public Integer createResearcher(Researcher r) throws Exception;
	public Integer createAdvisor(Advisor a) throws Exception;
	public void createRPLink(Integer projectId, RPLink r) throws Exception;
	public void createAPLink(Integer projectId, APLink a) throws Exception;
	public Integer createReview(Integer projectId, Review r) throws Exception;
	public Integer createFollowUp(Integer projectId, FollowUp f) throws Exception;
	public void createResearchOutput(Integer projectId, ResearchOutput o) throws Exception;
	public void createAttachment(Integer projectId, Attachment a) throws Exception;
	public Integer createAdvisorAction(Integer projectId, AdvisorAction a) throws Exception;
	public void createProjectFacility(Integer projectId, ProjectFacility f) throws Exception;
	public void createProjectKpi(Integer projectId, ProjectKpi k) throws Exception;
	
	public void updateProject(Integer projectId, Project p) throws Exception;
	public void updateResearcher(Researcher r) throws Exception;
	public void updateAdvisor(Advisor a) throws Exception;
	
	public void deleteProject(Integer projectId) throws Exception;
	public void deleteResearcher(Integer id) throws Exception;
	public void deleteAdvisor(Integer id) throws Exception;
	public void deleteReview(Integer projectId, Integer id) throws Exception;
	public void deleteFollowUp(Integer projectId, Integer id) throws Exception;
	public void deleteResearchOutput(Integer projectId, Integer id) throws Exception;
	public void deleteAttachment(Integer projectId, Integer id) throws Exception;
	public void deleteAdvisorAction(Integer projectId, Integer id) throws Exception;
	public void deleteResearcherFromProject(Integer projectId, Integer researcherId) throws Exception;
	public void deleteAdvisorFromProject(Integer projectId, Integer advisorId) throws Exception;
	public void deleteFacilityFromProject(Integer projectId, Integer facilityId) throws Exception;
	public void deleteProjectKpi(Integer projectId, Integer id) throws Exception;
	
}
