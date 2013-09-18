package pm.db;

import pm.pojo.TempProject;

public interface TempProjectDao {

	public Integer createProject(TempProject tp);
	public TempProject getProject(Integer pid);
	public String getOwner(Integer pid);
	public void deleteProject(Integer pid);
	public void deleteExpiredProjects(Integer lifetimeSeconds);
	public Boolean projectExists(Integer pid);
	public Integer getNextNewProjectId();
	public void updateLastVisited(Integer pid);
	public void updateProject(TempProject tp);
}
