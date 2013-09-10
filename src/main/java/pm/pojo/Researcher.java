package pm.pojo;

public class Researcher {

	private Integer id;
	private String fullName;
	private String email;
	private String phone;
	private String institution;
	private String department1;
	private String department2;
	private Integer institutionalRoleId;
	private String institutionalRoleName;
	private String pictureUrl;
	private String startDate;
	private String endDate;
	private String notes;

	public Integer getId() {
		return id;
	}

	public Integer getInstitutionalRoleId() {
		return institutionalRoleId;
	}

	public void setInstitutionalRoleId(Integer institutionalRoleId) {
		this.institutionalRoleId = institutionalRoleId;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getInstitution() {
		return institution;
	}

	public void setInstitution(String institution) {
		this.institution = institution;
	}

	public String getDepartment1() {
		return department1;
	}

	public void setDepartment1(String department1) {
		this.department1 = department1;
	}

	public String getDepartment2() {
		return department2;
	}

	public void setDepartment2(String department2) {
		this.department2 = department2;
	}

	public String getPictureUrl() {
		return pictureUrl;
	}

	public void setPictureUrl(String pictureUrl) {
		this.pictureUrl = pictureUrl;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public String getInstitutionalRoleName() {
		return institutionalRoleName;
	}

	public void setInstitutionalRoleName(String institutionalRoleName) {
		this.institutionalRoleName = institutionalRoleName;
	}

}
