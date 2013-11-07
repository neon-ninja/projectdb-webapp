package pm.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ProjectKpisController extends GlobalController {
	@RequestMapping(value = "/viewprojectkpis", method = RequestMethod.GET)
	public ModelAndView viewprojectkpis() throws Exception {
    	ModelAndView mav = new ModelAndView();
    	mav.addObject("projectKpis", projectDao.getProjectKpis());
		return mav;
	}
}
