package pm.util;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;

import pm.db.ProjectDao;
import pm.pojo.ProjectType;
import pm.pojo.ProjectWrapper;
import pm.pojo.Site;

public class Util {

	private Log log = LogFactory.getLog(Util.class.getName()); 

	public void addProjectInfosToMav(ModelAndView mav, ProjectDao dao, Integer projectId) throws Exception {
		ProjectWrapper pw = dao.getProjectWrapperById(projectId);
		List<ProjectType> projectTypes = dao.getProjectTypes();
		List<Site> sites = dao.getSites();
		mav.addObject("projectwrapper", pw);
		mav.addObject("sites", sites);
		mav.addObject("projectTypes", projectTypes);
	}
}
