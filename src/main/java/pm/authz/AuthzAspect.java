package pm.authz;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import pm.db.ProjectDao;
import pm.pojo.Advisor;

public class AuthzAspect {

	private Log log = LogFactory.getLog(AuthzAspect.class.getName()); 
	private String remoteUserHeader;
	private ProjectDao projectDao;

	public void verifyUserIsAdvisor() throws AuthzException {
		log.info("verifying user " + this.getTuakiriUniqueIdFromRequest() + " is advisor");
		try {
			String tuakiriUniqueId = this.getTuakiriUniqueIdFromRequest();
			List<Advisor> advisors = this.projectDao.getAllAdvisors();
			if (advisors != null) {
				for (Advisor a: advisors) {
					String tid = a.getTuakiriUniqueId();
					if (tid != null && !tid.trim().equals("") && tid.equals(tuakiriUniqueId)) {
						return;
					}
				}
			}
		} catch (Exception e) {
			throw new AuthzException(e.getMessage());
		}
		throw new AuthzException("Only an advisor can perform this operation.");
	}

	public void verifyUserIsAdvisorOnProject(Integer projectId) throws AuthzException {
		try {
			String tuakiriUniqueId = this.getTuakiriUniqueIdFromRequest();
			Advisor tmp = this.projectDao.getAdvisorByTuakiriUniqueId(tuakiriUniqueId);
			if (tmp != null) {
				if (tmp.getIsAdmin() > 0) {
					return;
				}
			}
			log.info("verifying user " + tuakiriUniqueId + " is admin or advisor on project " + projectId);
			List<Advisor> advisors = this.projectDao.getAllAdvisorsOnProject(projectId);
			if (advisors != null) {
				for (Advisor a: advisors) {
					String tid = a.getTuakiriUniqueId();
					if (tid != null && !tid.trim().equals("") && tid.equals(tuakiriUniqueId)) {
						return;
					}
				}
			}
		} catch (Exception e) {
			throw new AuthzException(e.getMessage());
		}
		throw new AuthzException("Only an advisor of this project or an admin can perform this operation.");
	}

	public void verifyUserIsAdmin() throws AuthzException {		
		log.info("verifying user " + this.getTuakiriUniqueIdFromRequest() + " is admin");
		try {
			String tuakiriUniqueId = this.getTuakiriUniqueIdFromRequest();
			if (tuakiriUniqueId != null && !tuakiriUniqueId.trim().equals("")) {
				Advisor advisor = this.projectDao.getAdvisorByTuakiriUniqueId(tuakiriUniqueId);
				if (advisor.getIsAdmin() > 0) {
					return;
				}				
			}
		} catch (Exception e) {
			throw new AuthzException(e.getMessage());
		}
		throw new AuthzException("Only an admin can perform this operation.");
	}

	public ProjectDao getProjectDao() {
		return projectDao;
	}

	public void setProjectDao(ProjectDao projectDao) {
		this.projectDao = projectDao;
	}

	public String getRemoteUserHeader() {
		return remoteUserHeader;
	}

	public void setRemoteUserHeader(String remoteUserHeader) {
		this.remoteUserHeader = remoteUserHeader;
	}

	private String getTuakiriUniqueIdFromRequest() {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		String user = (String) request.getAttribute(this.remoteUserHeader);
		if (user == null) {
			user = "NULL";
		}
		return user;
	}
}
