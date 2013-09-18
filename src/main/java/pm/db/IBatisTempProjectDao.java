package pm.db;

import java.util.HashMap;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import pm.pojo.TempProject;

public class IBatisTempProjectDao extends SqlMapClientDaoSupport implements TempProjectDao {

	public Integer createProject(final TempProject tp) {
		return (Integer) getSqlMapClientTemplate().insert("createTempProject", tp);
	}
	
	public TempProject getProject(Integer pid) {
		return (TempProject) getSqlMapClientTemplate().queryForObject("getTempProjectById", pid);
	}
	
	public String getOwner(Integer pid) {
		return (String) getSqlMapClientTemplate().queryForObject("getOwner", pid);
	}
	public void deleteExpiredProjects(Integer lifetimeSeconds) {
		Long maxTimestamp = (System.currentTimeMillis()/1000) - lifetimeSeconds;
		getSqlMapClientTemplate().insert("deleteExpiredTempProjects", maxTimestamp);		
	}
	
	public Boolean projectExists(Integer pid) {
		Integer count = (Integer) getSqlMapClientTemplate().queryForObject("countOccurences", pid);
		return count.equals(0) ? false : true;
	}

	public Boolean projectExistsAndOwnedByUser(Integer pid, String user) {
		String owner = (String) getSqlMapClientTemplate().queryForObject("getOwner", pid);
		return this.projectExists(pid) && owner.equals(user);		
	}
	
	public void updateLastVisited(Integer projectId) {
        Map<String,Object> params=new HashMap<String, Object>();
        params.put("id", projectId);
        params.put("lastVisited", System.currentTimeMillis()/1000);
		getSqlMapClientTemplate().insert("updateTempProjectLastVisited", params);		
	}

	public void updateProject(TempProject tp) {
		getSqlMapClientTemplate().update("updateTempProject", tp);
	}

	public void deleteProject(Integer projectId) {
		getSqlMapClientTemplate().update("deleteTempProject", projectId);
	}

	public Integer getNextNewProjectId() {
		Integer id = ((Integer)getSqlMapClientTemplate().queryForObject("getMinId"));
		if (id == null || id > -1) {
			id = 0;
		}
		return id-1;
	}


}
