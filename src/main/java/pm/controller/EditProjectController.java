package pm.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

import pm.authz.AuthzAspect;
import pm.db.ProjectDao;
import pm.pojo.APLink;
import pm.pojo.Project;
import pm.pojo.ProjectType;
import pm.pojo.ProjectWrapper;
import pm.pojo.RPLink;
import pm.temp.TempProjectManager;

public class EditProjectController extends SimpleFormController {
	
	private Log log = LogFactory.getLog(EditProjectController.class.getName()); 
	private ProjectDao projectDao;
	private TempProjectManager tempProjectManager;
	private String proxy;
	private AuthzAspect authzAspect;	

	@Override
	public ModelAndView onSubmit(Object o) throws Exception {
		ProjectWrapper pw = (ProjectWrapper) o;
		ModelAndView mav = new ModelAndView();
		String op = pw.getOperation();
		Integer pid = pw.getProject().getId();
		this.authzAspect.verifyUserIsAdviserOnProject(pid);

		if (op.equals("CANCEL")) {
			this.handleCancel(pid, mav);
		} else if (op.equals("RESET")) {
			this.handleReset(pid, mav);
		} else if (op.equals("UPDATE")) {
			this.handleUpdate(pw, mav);
		} else if (op.equals("SAVE_AND_CONTINUE_EDITING")) {
			this.handleSaveAndContinue(pw, mav);
		} else if (op.equals("SAVE_AND_FINISH_EDITING")) {
			this.handleSaveAndFinish(pw, mav);
		} else {
			throw new Exception("Unknown operation: " + op);
		}
		
		pw.setSecondsLeft(this.tempProjectManager.getSessionDuration());
		return mav;
	}
	
	protected void handleCancel(Integer pid, ModelAndView mav) throws Exception {
		this.tempProjectManager.unregister(pid);
		mav.setViewName("redirect");
		mav.addObject("proxy", this.proxy);			
		if (pid < 0) { // new project
			mav.addObject("pathAndQuerystring", "viewprojects");
		} else { // old project
			mav.addObject("pathAndQuerystring", "viewproject?id=" + pid);
		}
	}

	protected void handleReset(Integer pid, ModelAndView mav) throws Exception {
		this.tempProjectManager.unregister(pid);
		mav.setViewName("redirect");
		mav.addObject("proxy", this.proxy);
		mav.addObject("pathAndQuerystring", "editproject?id=" + pid);
	}

	protected void handleUpdate(ProjectWrapper pwCommand, ModelAndView mav) throws Exception{
		Project p = pwCommand.getProject();
		String redirect = pwCommand.getRedirect();
		Integer pid = p.getId();
		ProjectWrapper pwTemp = this.tempProjectManager.get(pid);
		pwTemp.setProject(p);
		pwTemp.setErrorMessage("");
        this.tempProjectManager.update(pid, pwTemp);
		mav.setViewName("redirect");
		mav.addObject("pathAndQuerystring", redirect);
		mav.addObject("proxy", this.proxy);
	}
	
	protected void handleSaveAndFinish(ProjectWrapper pw, ModelAndView mav) throws Exception {
		Integer pid = pw.getProject().getId();
		if (this.isProjectValid(pw)) {
			Project p = pw.getProject();
			pw = this.tempProjectManager.get(pid);
			pw.setProject(p);
			if (pid < 0) {
				String projectCode = this.projectDao.getNextProjectCode(p.getHostInstitution());
				pw.getProject().setProjectCode(projectCode);
				pid = this.projectDao.createProjectWrapper(pw);
			} else {
				this.projectDao.updateProjectWrapper(pid, pw);
			}
			this.tempProjectManager.unregister(pid);
			mav.addObject("pathAndQuerystring", "viewproject?id=" + pid);
		} else {
			this.tempProjectManager.update(pid, pw);
			mav.addObject("pathAndQuerystring", "editproject?id=" + pid);
		}
		mav.setViewName("redirect");
		mav.addObject("proxy", this.proxy);
	}
	
	protected void handleSaveAndContinue(ProjectWrapper pw, ModelAndView mav) throws Exception{
		Project p = pw.getProject();
		Integer pidOld = p.getId();
		Integer pid = pidOld;
		ProjectWrapper pwNew = this.tempProjectManager.get(pidOld);
		pwNew.setProject(p);
		if (this.isProjectValid(pwNew)) {
			pwNew.setErrorMessage("");
			if (pidOld < 0) {
				String projectCode = this.projectDao.getNextProjectCode(p.getHostInstitution());
				pw.getProject().setProjectCode(projectCode);
				pid = this.projectDao.createProjectWrapper(pwNew);
				pwNew.getProject().setId(pid);
				this.tempProjectManager.register(pwNew);
				this.tempProjectManager.unregister(pidOld);
			} else {
				this.tempProjectManager.update(pidOld, pwNew);
				this.projectDao.updateProjectWrapper(pidOld,pwNew);
			}
		} else {
			// save error message
			this.tempProjectManager.update(pid, pwNew);
		}
		mav.setViewName("redirect");
		mav.addObject("pathAndQuerystring", "editproject?id=" + pid);
		mav.addObject("proxy", this.proxy);
	}
	
	@Override
	protected Object formBackingObject(HttpServletRequest request) throws Exception {
		Integer pid = null;
		if (request.getParameterMap().containsKey("id")) {
		    pid = Integer.valueOf(request.getParameter("id"));
		}
		ProjectWrapper pw = null;
		if (pid == null) {
			pw = new ProjectWrapper();
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			Date startDate = new Date();
			Date nextReview = new Date();
			Date nextFollowUp = new Date();
			nextReview.setYear(nextReview.getYear() + 1);
			nextFollowUp.setMonth(nextFollowUp.getMonth() + 3);
			pw.getProject().setStartDate(df.format(startDate));
			pw.getProject().setNextFollowUpDate(df.format(nextFollowUp));
			pw.getProject().setNextReviewDate(df.format(nextReview));
	  	    this.tempProjectManager.register(pw);
		} else {
			if (pid < 0) {
				if (this.tempProjectManager.isRegistered(pid)) {
					pw = tempProjectManager.get(pid);
				} else {
					pw = new ProjectWrapper();
					pw.getProject().setId(pid);
					this.tempProjectManager.register(pid, pw);
				}
			} else {
				this.authzAspect.verifyUserIsAdviserOnProject(pid);
				if (this.tempProjectManager.isRegistered(pid)) {
					pw = tempProjectManager.get(pid);
				} else {
					pw = this.projectDao.getProjectWrapperById(pid);
				}
		  	    this.tempProjectManager.register(pid, pw);
			}
		}
		
		pw.setSecondsLeft(this.tempProjectManager.getSessionDuration());
		return pw;
	}	
	
	@Override
    protected Map<String,Object> referenceData(HttpServletRequest request) throws Exception {
		ModelMap modelMap = new ModelMap();
		List<ProjectType> projectTypes = projectDao.getProjectTypes();
		Map<Integer,String> pTypes = new LinkedHashMap<Integer,String>();
        if (projectTypes != null) {
            for (ProjectType pt : projectTypes) {
                    pTypes.put(pt.getId(), pt.getName());
            }    
        }
		modelMap.put("projectTypes", pTypes);
		modelMap.put("institutions", this.projectDao.getInstitutions());
        return modelMap;
    }

	private boolean isProjectValid(ProjectWrapper pw) {
		if (pw.getProject().getName().trim().equals("")) {
			pw.setErrorMessage("A project must have a title");
			return false;				
		}		

		// Exactly one PI?
		int count = 0;
		for (RPLink rp: pw.getRpLinks()) {
			if (rp.getResearcherRoleId() == 1) {
				count += 1;
			}
		}
		if (count == 0 || count > 1) {
			pw.setErrorMessage("There must be exactly 1 PI on a project");
			return false;	
		}
		
		// Exactly one primary adviser?
		count = 0;
		for (APLink ap: pw.getApLinks()) {
			if (ap.getAdviserRoleId() == 1) {
				count += 1;
			}
		}
		if (count == 0 || count > 1) {
			pw.setErrorMessage("There must be exactly 1 primary adviser on a project");
			return false;
		}
		return true;
	}
	
	public void setProjectDao(ProjectDao projectDao) {
		this.projectDao = projectDao;
	}

	public void setTempProjectManager(TempProjectManager tempProjectManager) {
		this.tempProjectManager = tempProjectManager;
	}

	public void setProxy(String proxy) {
		this.proxy = proxy;
	}
	
	public void setAuthzAspect(AuthzAspect authzAspect) {
		this.authzAspect = authzAspect;
	}

}
