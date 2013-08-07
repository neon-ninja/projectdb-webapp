package pm.pojo;

public class CreateProject {

	private Project project;
	private RPLink rpLink;
	private APLink apLink;
	private ProjectFacility hpcFacility;

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public RPLink getRpLink() {
		return rpLink;
	}

	public void setRpLink(RPLink rpLink) {
		this.rpLink = rpLink;
	}

	public APLink getApLink() {
		return apLink;
	}

	public void setApLink(APLink apLink) {
		this.apLink = apLink;
	}

	public void setHpcFacility(ProjectFacility hpcFacility) {
		this.hpcFacility = hpcFacility;
	}

	public ProjectFacility getHpcFacility() {
		return hpcFacility;
	}

}
