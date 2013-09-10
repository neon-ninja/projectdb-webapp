package pm.pojo;

public class ProjectKpi {

	private Integer id;
	private Integer kpiId;
	private String kpiType;
	private String kpiTitle;
	private Integer projectId;
	private String adviser;
	private Integer adviserId;
	private String date;
	private Float value;
	private String notes;

	public Integer getId() {
		return id;
	}

	public String getKpiType() {
		return kpiType;
	}

	public void setKpiType(String kpiType) {
		this.kpiType = kpiType;
	}

	public String getAdviser() {
		return adviser;
	}

	public void setAdviser(String adviser) {
		this.adviser = adviser;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getProjectId() {
		return projectId;
	}

	public void setProjectId(Integer projectId) {
		this.projectId = projectId;
	}

	public Integer getAdviserId() {
		return adviserId;
	}

	public void setAdviserId(Integer adviserId) {
		this.adviserId = adviserId;
	}

	public String getKpiTitle() {
		return kpiTitle;
	}

	public void setKpiTitle(String kpiTitle) {
		this.kpiTitle = kpiTitle;
	}

	public Integer getKpiId() {
		return kpiId;
	}

	public void setKpiId(Integer kpiId) {
		this.kpiId = kpiId;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public Float getValue() {
		return value;
	}

	public void setValue(Float value) {
		this.value = value;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}
}
