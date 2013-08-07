package pm.pojo;

public class RPLink {

	private Integer researcherId;
	private Integer projectId;
	private Integer researcherRoleId;
	private String researcherRole;
	private String notes;

	public Integer getResearcherRoleId() {
		return researcherRoleId;
	}

	public void setResearcherRoleId(Integer researcherRoleId) {
		this.researcherRoleId = researcherRoleId;
	}

	public Integer getResearcherId() {
		return researcherId;
	}

	public void setResearcherId(Integer researcherId) {
		this.researcherId = researcherId;
	}

	public Integer getProjectId() {
		return projectId;
	}

	public void setProjectId(Integer projectId) {
		this.projectId = projectId;
	}

	public String getResearcherRole() {
		return researcherRole;
	}

	public void setResearcherRole(String researcherRole) {
		this.researcherRole = researcherRole;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

}
