package pm.pojo;

public class EditProject {

	private Project project;
	private Integer researcherToAdd;
	private Integer researcherToRemove;
	private String action;

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public Integer getResearcherToAdd() {
		return researcherToAdd;
	}

	public void setResearcherToAdd(Integer researcherToAdd) {
		this.researcherToAdd = researcherToAdd;
	}

	public Integer getResearcherToRemove() {
		return researcherToRemove;
	}

	public void setResearcherToRemove(Integer researcherToRemove) {
		this.researcherToRemove = researcherToRemove;
	}

}
