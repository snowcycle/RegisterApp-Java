package edu.uark.registerapp.controllers;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import edu.uark.registerapp.controllers.enums.ViewNames;
import edu.uark.registerapp.models.entities.EmployeeEntity;
import edu.uark.registerapp.models.repositories.EmployeeRepository;

@Controller
@RequestMapping(value = "/employeeDetail")
public class EmployeeDetailRouteController extends BaseRouteController {
	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView showEmployeeDetail()
	{
		return new ModelAndView(ViewNames.EMPLOYEE_DETAIL.getViewName());
	}

	@RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	public ModelAndView makeNewEmployee(
		EmployeeEntity employee,
		HttpServletRequest request
	)
	{
		employee = new EmployeeEntity();

		employee.setFirstName(request.getParameter("firstname"));
		employee.setLastName(request.getParameter("lastname"));
		employee.setPassword(request.getParameter("password").getBytes());
		employee.setClassification(Integer.parseInt(
			request.getParameter("classification")));

		employeeRepository.save(employee);

		if (this.employeeRepository.count() == 1)
		{
			ModelAndView modelAndView = new ModelAndView(
				REDIRECT_PREPEND.concat(ViewNames.SIGN_IN.getRoute()));

			modelAndView.addObject("Method", "GET");
			
			return modelAndView;
		}
		else
			return new ModelAndView(ViewNames.EMPLOYEE_DETAIL.getViewName());

		//return new ModelAndView(ViewNames.EMPLOYEE_DETAIL.getViewName());
	}

	@Autowired
	EmployeeRepository employeeRepository;
}
