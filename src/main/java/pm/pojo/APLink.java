package pm.pojo;

public class APLink {

	private Integer advisorId;
	private Integer projectId;
	private String advisorRole;
	private Integer advisorRoleId;
	private String notes;

	public Integer getAdvisorRoleId() {
		return advisorRoleId;
	}

	public void setAdvisorRoleId(Integer advisorRoleId) {
		this.advisorRoleId = advisorRoleId;
	}

	public Integer getAdvisorId() {
		return advisorId;
	}

	public void setAdvisorId(Integer advisorId) {
		this.advisorId = advisorId;
	}

	public Integer getProjectId() {
		return projectId;
	}

	public void setProjectId(Integer projectId) {
		this.projectId = projectId;
	}

	public String getAdvisorRole() {
		return advisorRole;
	}

	public void setAdvisorRole(String advisorRole) {
		this.advisorRole = advisorRole;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

}
