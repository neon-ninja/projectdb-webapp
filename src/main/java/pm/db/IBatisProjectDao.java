package pm.db;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import pm.authz.annotation.RequireAdmin;
import pm.authz.annotation.RequireAdvisor;
import pm.authz.annotation.RequireAdvisorOnProject;
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

public class IBatisProjectDao extends SqlMapClientDaoSupport implements ProjectDao {

	public Project getProjectById(final Integer id) throws Exception {
		Project p = (Project) getSqlMapClientTemplate().queryForObject("getProjectById", id);
		ProjectType t = (ProjectType) getSqlMapClientTemplate().queryForObject("getProjectTypeById", p.getProjectTypeId());
		p.setProjectType(t.getName());
		return p;
	}

	public Researcher getResearcherById(final Integer id) throws Exception {
		Researcher r = (Researcher) getSqlMapClientTemplate().queryForObject("getResearcherById", id);
		InstitutionalRole ir = (InstitutionalRole) getSqlMapClientTemplate().queryForObject("getInstitutionalRoleById", r.getInstitutionalRoleId());
		r.setInstitutionalRole(ir.getName());
		return r;
	}

	public Advisor getAdvisorById(final Integer id) throws Exception {
		Advisor a = (Advisor) getSqlMapClientTemplate().queryForObject("getAdvisorById", id);
		a.setNumProjects(this.getNumProjectsForAdvisor(a.getId()));
		return a;
	}

	public Advisor getAdvisorByTuakiriUniqueId(final String id) throws Exception {
		Advisor a = (Advisor) getSqlMapClientTemplate().queryForObject("getAdvisorByTuakiriUniqueId", id);
		a.setNumProjects(this.getNumProjectsForAdvisor(a.getId()));
		return a;
	}

	public InstitutionalRole getInstitutionalRoleById(final Integer id) throws Exception {
		return (InstitutionalRole) getSqlMapClientTemplate().queryForObject("getInstitutionalRoleById", id);
	}

	public FollowUp getFollowUpById(Integer id) throws Exception {
		return (FollowUp) getSqlMapClientTemplate().queryForObject("getFollowUpById", id);
	}
	
	public Review getReviewById(Integer id) throws Exception {
		return (Review) getSqlMapClientTemplate().queryForObject("getReviewById", id);	
	}
	
	public Kpi getKpiById(Integer id) throws Exception {
		return (Kpi) getSqlMapClientTemplate().queryForObject("getKpiById", id);	
	}

	public AdvisorAction getAdvisorActionById(Integer id) throws Exception {
		return (AdvisorAction) getSqlMapClientTemplate().queryForObject("getAdvisorActionById", id);
	}

	public List<Project> getAllProjects() throws Exception {
		List<Project> ps = (List<Project>) getSqlMapClientTemplate().queryForList("getAllProjects");
		for (Project p: ps) {
			ProjectType t = (ProjectType) getSqlMapClientTemplate().queryForObject("getProjectTypeById", p.getProjectTypeId());
			p.setProjectType(t.getName());
		}
		return ps;
	}

	public List<Researcher> getAllResearchers() throws Exception {
		List<Researcher> l = (List<Researcher>) getSqlMapClientTemplate().queryForList("getAllResearchers");
		if (l != null) {
			for (Researcher r: l) {
				InstitutionalRole ir = (InstitutionalRole) getSqlMapClientTemplate().queryForObject("getInstitutionalRoleById", r.getInstitutionalRoleId());
				r.setInstitutionalRole(ir.getName());
			}
		}
		return l;
	}

	public List<Advisor> getAllAdvisors() throws Exception {
		List<Advisor> list = (List<Advisor>) getSqlMapClientTemplate().queryForList("getAllAdvisors");
		for (Advisor a: list) {
			a.setNumProjects(this.getNumProjectsForAdvisor(a.getId()));
		}
		return list;
	}

	public Integer getNumProjectsForAdvisor(Integer advisorId) throws Exception {
		return (Integer) getSqlMapClientTemplate().queryForObject("getNumProjectsForAdvisor", advisorId);		
	}
	
	public List<ResearchOutputType> getResearchOutputTypes() throws Exception {
		return (List<ResearchOutputType>) getSqlMapClientTemplate().queryForList("getResearchOutputTypes");	
	}

	public List<Site> getAllSites() throws Exception {
		return (List<Site>) getSqlMapClientTemplate().queryForList("getAllSites");
	}

	public List<Kpi> getAllKpis() throws Exception {
		return (List<Kpi>) getSqlMapClientTemplate().queryForList("getAllKpis");
	}

	public List<ProjectType> getAllProjectTypes() throws Exception {
		return (List<ProjectType>) getSqlMapClientTemplate().queryForList("getAllProjectTypes");
	}

	public List<Researcher> getAllResearchersOnProject(Integer pid) throws Exception {
		List<Researcher> l = (List<Researcher>) getSqlMapClientTemplate().queryForList("getAllResearchersOnProject", pid);		
		if (l != null) {
			for (Researcher r: l) {
				InstitutionalRole ir = (InstitutionalRole) getSqlMapClientTemplate().queryForObject("getInstitutionalRoleById", r.getInstitutionalRoleId());
				r.setInstitutionalRole(ir.getName());
			}
		}
		return l;
	}

	public List<Advisor> getAllAdvisorsOnProject(Integer pid) throws Exception {
		List<Advisor> list = (List<Advisor>) getSqlMapClientTemplate().queryForList("getAllAdvisorsOnProject", pid);		
		for (Advisor a: list) {
			a.setNumProjects(this.getNumProjectsForAdvisor(a.getId()));
		}
		return list;
	}

	public List<Facility> getAllFacilities() throws Exception {
		return (List<Facility>) getSqlMapClientTemplate().queryForList("getAllFacilities");		
	}

	public List<Facility> getAllFacilitiesOnProject(Integer pid) throws Exception {
		return (List<Facility>) getSqlMapClientTemplate().queryForList("getAllFacilitiesOnProject", pid);		
	}

	public List<Researcher> getAllResearchersNotOnProject(Integer pid) throws Exception {
		List<Researcher> l = (List<Researcher>) getSqlMapClientTemplate().queryForList("getAllResearchersNotOnProject", pid);
		if (l != null) {
			for (Researcher r: l) {
				InstitutionalRole ir = (InstitutionalRole) getSqlMapClientTemplate().queryForObject("getInstitutionalRoleById", r.getInstitutionalRoleId());
				r.setInstitutionalRole(ir.getName());
			}
		}
		return l;
	}

	public List<Advisor> getAllAdvisorsNotOnProject(Integer pid) throws Exception {
		return (List<Advisor>) getSqlMapClientTemplate().queryForList("getAllAdvisorsNotOnProject", pid);
	}

	public List<Facility> getAllFacilitiesNotOnProject(Integer pid) throws Exception {
		return (List<Facility>) getSqlMapClientTemplate().queryForList("getAllFacilitiesNotOnProject", pid);
	}

	public List<ResearcherRole> getAllResearcherRoles() throws Exception {
		return (List<ResearcherRole>) getSqlMapClientTemplate().queryForList("getAllResearcherRoles");		
	}

	public List<AdvisorRole> getAllAdvisorRoles() throws Exception {
		return (List<AdvisorRole>) getSqlMapClientTemplate().queryForList("getAllAdvisorRoles");		
	}

	public List<InstitutionalRole> getAllInstitutionalRoles() throws Exception {
		return (List<InstitutionalRole>) getSqlMapClientTemplate().queryForList("getAllInstitutionalRoles");		
	}

	public List<RPLink> getAllRPLinksForProjectId(Integer id) throws Exception {
		List<RPLink> l = (List<RPLink>) getSqlMapClientTemplate().queryForList("getAllRPLinksForProjectId", id);
		if (l != null) {
			for (RPLink rl: l) {
				ResearcherRole ar = (ResearcherRole) getSqlMapClientTemplate().queryForObject("getResearcherRoleById", rl.getResearcherRoleId());
				rl.setResearcherRole(ar.getName());
			}
		}
		return l;
	}
	
	public List<APLink> getAllAPLinksForProjectId(Integer id) throws Exception {
		List<APLink> l = (List<APLink>) getSqlMapClientTemplate().queryForList("getAllAPLinksForProjectId", id);
		if (l != null) {
			for (APLink al: l) {
				AdvisorRole ar = (AdvisorRole) getSqlMapClientTemplate().queryForObject("getAdvisorRoleById", al.getAdvisorRoleId());
				al.setAdvisorRole(ar.getName());
			}
		}		
		return l;
	}
	
	public List<Review> getAllReviewsForProjectId(Integer id) throws Exception {
		List<Review> l = (List<Review>) getSqlMapClientTemplate().queryForList("getAllReviewsForProjectId", id);
		for (Review r: l) {
			Advisor tmp = (Advisor) getSqlMapClientTemplate().queryForObject("getAdvisorById", r.getAdvisorId());
			r.setAdvisor(tmp.getFullName());
		}
		return l;
	}

	public List<FollowUp> getAllFollowUpsForProjectId(Integer id) throws Exception {
		List<FollowUp> l = (List<FollowUp>) getSqlMapClientTemplate().queryForList("getAllFollowUpsForProjectId", id);		
		for (FollowUp f: l) {
			Advisor tmp = (Advisor) getSqlMapClientTemplate().queryForObject("getAdvisorById", f.getAdvisorId());
			f.setAdvisor(tmp.getFullName());
		}
		return l;
	}

	public List<ResearchOutput> getAllResearchOutputsForProjectId(Integer id) throws Exception {
		List<ResearchOutput> l = (List<ResearchOutput>) getSqlMapClientTemplate().queryForList("getAllResearchOutputsForProjectId", id);
		for (ResearchOutput ro: l) {
			ResearchOutputType tmp = (ResearchOutputType) getSqlMapClientTemplate().queryForObject("getResearchOutputTypeById", ro.getTypeId());
			ro.setType(tmp.getName());
		}
		return l;
	}

	public List<Attachment> getAllAttachmentsForProjectId(Integer id) throws Exception {
		return (List<Attachment>) getSqlMapClientTemplate().queryForList("getAllAttachmentsForProjectId", id);		
	}

	public List<AdvisorAction> getAllAdvisorActionsForProjectId(Integer id) throws Exception {
		List<AdvisorAction> l = (List<AdvisorAction>) getSqlMapClientTemplate().queryForList("getAllAdvisorActionsForProjectId", id);
		for (AdvisorAction aa: l) {
			Advisor tmp = (Advisor) getSqlMapClientTemplate().queryForObject("getAdvisorById", aa.getAdvisorId());
		    aa.setAdvisor(tmp.getFullName());
		}
		return l;
	}

	public List<ProjectKpi> getAllKpisForProjectId(Integer id) throws Exception {
		List<ProjectKpi> l = (List<ProjectKpi>) getSqlMapClientTemplate().queryForList("getAllKpisForProjectId", id);
		for (ProjectKpi pk: l) {
			Advisor tmp = (Advisor) getSqlMapClientTemplate().queryForObject("getAdvisorById", pk.getAdvisorId());
			Kpi kpi = (Kpi) getSqlMapClientTemplate().queryForObject("getKpiById", pk.getKpiId());
			pk.setAdvisor(tmp.getFullName());
			pk.setKpiType(kpi.getType());
			pk.setKpiTitle(kpi.getTitle());
		}
		return l;
	}

    public List<Project> getAllProjectsForResearcherId(Integer id) throws Exception {
    	List<Project> ps = getSqlMapClientTemplate().queryForList("getAllProjectsForResearcherId", id);
		for (Project p: ps) {
			ProjectType t = (ProjectType) getSqlMapClientTemplate().queryForObject("getProjectTypeById", p.getProjectTypeId());
			p.setProjectType(t.getName());
		}
		return ps;
    }

    public List<Project> getAllProjectsForAdvisorId(Integer id) throws Exception {
    	List<Project> ps = getSqlMapClientTemplate().queryForList("getAllProjectsForAdvisorId", id);
		for (Project p: ps) {
			ProjectType t = (ProjectType) getSqlMapClientTemplate().queryForObject("getProjectTypeById", p.getProjectTypeId());
			p.setProjectType(t.getName());
		}
		return ps;
    }

    @RequireAdvisor
	public synchronized Integer createProject(final Project p) throws Exception {
		return (Integer) getSqlMapClientTemplate().insert("createProject", p);
	}

    @RequireAdvisor
	public synchronized Integer createResearcher(final Researcher r) throws Exception {
		return (Integer) getSqlMapClientTemplate().insert("createResearcher", r);
	}

    @RequireAdvisor
	public synchronized Integer createAdvisor(final Advisor a) throws Exception {
		return (Integer) getSqlMapClientTemplate().insert("createAdvisor", a);
	}

    @RequireAdvisorOnProject
	public void createRPLink(Integer projectId, RPLink r) throws Exception {
		getSqlMapClientTemplate().insert("createRPLink", r);
	}
	
    @RequireAdvisorOnProject
    public void createAPLink(Integer projectId, APLink a) throws Exception {
		getSqlMapClientTemplate().insert("createAPLink", a);
	}
	
    @RequireAdvisorOnProject
	public Integer createReview(Integer projectId, Review r) throws Exception {
		return (Integer) getSqlMapClientTemplate().insert("createReview", r);
	}
	
    @RequireAdvisorOnProject
	public Integer createFollowUp(Integer projectId, FollowUp f) throws Exception {
		return (Integer) getSqlMapClientTemplate().insert("createFollowUp", f);
	}
	
    @RequireAdvisorOnProject
	public void createResearchOutput(Integer projectId, ResearchOutput o) throws Exception {
		getSqlMapClientTemplate().insert("createResearchOutput", o);
	}
	
    @RequireAdvisorOnProject
	public void createAttachment(Integer projectId, Attachment a) throws Exception {
		getSqlMapClientTemplate().insert("createAttachment", a);
	}
	
    @RequireAdvisorOnProject
	public Integer createAdvisorAction(Integer projectId, AdvisorAction a) throws Exception {
		return (Integer) getSqlMapClientTemplate().insert("createAdvisorAction", a);
	}
	
    @RequireAdvisorOnProject
	public void createProjectFacility(Integer projectId, ProjectFacility f) throws Exception {
		getSqlMapClientTemplate().insert("createProjectFacility", f);
	}
	
    @RequireAdvisorOnProject
	public void createProjectKpi(Integer projectId, ProjectKpi pk) throws Exception {
		getSqlMapClientTemplate().insert("createProjectKpi", pk);
	}
	
    @RequireAdvisorOnProject
	public void updateProject(Integer projectId, final Project p) throws Exception {
		getSqlMapClientTemplate().update("updateProject", p);
	}

    @RequireAdvisor
	public void updateResearcher(final Researcher r) throws Exception {
		getSqlMapClientTemplate().update("updateResearcher", r);
	}

    @RequireAdvisor
	public void updateAdvisor(final Advisor a) throws Exception {
		getSqlMapClientTemplate().update("updateAdvisor", a);
	}

    @RequireAdvisorOnProject
	public void deleteProject(Integer projectId) throws Exception {
		getSqlMapClientTemplate().update("deleteProject", projectId);
	}

    @RequireAdmin
	public void deleteResearcher(final Integer id) throws Exception {
		getSqlMapClientTemplate().update("deleteResearcher", id);
	}

    @RequireAdmin
	public void deleteAdvisor(final Integer id) throws Exception {
		getSqlMapClientTemplate().update("deleteAdvisor", id);
	}
	
    @RequireAdvisorOnProject
	public void deleteResearcherFromProject(Integer projectId, Integer researcherId) throws Exception {
        Map<String,Object> params=new HashMap<String, Object>();
        params.put("researcherId", researcherId);
        params.put("projectId", projectId);
        getSqlMapClientTemplate().update("deleteResearcherFromProject", params);
	}

    @RequireAdvisorOnProject
	public void deleteAdvisorFromProject(Integer projectId, Integer advisorId) throws Exception {
        Map<String,Object> params=new HashMap<String, Object>();
        params.put("advisorId", advisorId);
        params.put("projectId", projectId);
        getSqlMapClientTemplate().update("deleteAdvisorFromProject", params);
	}
	
    @RequireAdvisorOnProject
	public void deleteReview(Integer projectId, final Integer id) throws Exception {
		getSqlMapClientTemplate().update("deleteReview", id);
	}

    @RequireAdvisorOnProject
	public void deleteFollowUp(Integer projectId, final Integer id) throws Exception {
		getSqlMapClientTemplate().update("deleteFollowUp", id);
	}

    @RequireAdvisorOnProject
	public void deleteResearchOutput(Integer projectId, Integer id) throws Exception {
		getSqlMapClientTemplate().update("deleteResearchOutput", id);
	}
	
    @RequireAdvisorOnProject
	public void deleteAttachment(Integer projectId, Integer id) throws Exception {
		getSqlMapClientTemplate().update("deleteAttachment", id);		
	}
	
    @RequireAdvisorOnProject
	public void deleteAdvisorAction(Integer projectId, Integer id) throws Exception {
		getSqlMapClientTemplate().update("deleteAdvisorAction", id);		
	}

    @RequireAdvisorOnProject
	public void deleteProjectKpi(Integer projectId, Integer id) throws Exception {
		getSqlMapClientTemplate().update("deleteProjectKpi", id);		
	}

    @RequireAdvisorOnProject
	public void deleteFacilityFromProject(Integer projectId, Integer facilityId) throws Exception {
        Map<String,Object> params=new HashMap<String, Object>();
        params.put("projectId", projectId);
        params.put("facilityId", facilityId);
		getSqlMapClientTemplate().update("deleteFacilityFromProject", params);		
	}
}
