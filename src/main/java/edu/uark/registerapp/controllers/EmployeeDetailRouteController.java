package edu.uark.registerapp.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import edu.uark.registerapp.controllers.enums.ViewNames;

@Controller
@RequestMapping(value = "/employeeDetail")
public class EmployeeDetailRouteController {
	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView showEmployeeDetail()
	{
		return new ModelAndView(ViewNames.EMPLOYEE_DETAIL.getViewName());
	}
}
