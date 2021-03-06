package edu.uark.registerapp.controllers;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import edu.uark.registerapp.commands.employees.EmployeeSignInCommand;
import edu.uark.registerapp.commands.exceptions.NotFoundException;
import edu.uark.registerapp.controllers.enums.ViewModelNames;
import edu.uark.registerapp.controllers.enums.ViewNames;

import edu.uark.registerapp.models.api.EmployeeSignIn;
import edu.uark.registerapp.models.repositories.ActiveUserRepository;
import edu.uark.registerapp.models.repositories.EmployeeRepository;


@Controller
@RequestMapping(value = "/")
public class SignInRouteController extends BaseRouteController
{
	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView showSignIn()
	{
		// Check if any employees exist. If none, redirect to employeeDetail.
		if (this.employeeRepository.count() == 0)
		{
			return new ModelAndView(
				REDIRECT_PREPEND.concat(ViewNames.EMPLOYEE_DETAIL.getRoute()));
		}

		return new ModelAndView(ViewNames.SIGN_IN.getViewName());
	}

	@RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	public ModelAndView performSignIn(
		EmployeeSignIn signIn,
		HttpServletRequest request)
	{
		signIn = new EmployeeSignIn(request.getParameter("employeeId"),
			request.getParameter("password"));

		EmployeeSignInCommand signInCommand = new EmployeeSignInCommand(signIn,
			request.getSession().getId());

		// See comments on this set method in EmployeeSignInCommand.java
		// TLDR: I don't like doing it this way but I don't know how to do better
		signInCommand.setEmployeeRepository(this.employeeRepository);
		signInCommand.setActiveUserRepository(this.activeUserRepository);

		/*
		We don't need to validate the sign in information here because
		EmployeeSignInCommand.execute() does this and returns false
		if unsuccessful.
		*/

		if (signInCommand.execute())	// Sign in is successful...
		{
			return new ModelAndView(
				REDIRECT_PREPEND.concat(ViewNames.MAIN_MENU.getRoute()));
		}
		else
		{
			ModelAndView modelAndView =
				new ModelAndView(ViewNames.SIGN_IN.getViewName());

			final Exception e =
				new NotFoundException("An employee with matching ID or password");

			modelAndView.addObject(
				ViewModelNames.ERROR_MESSAGE.getValue(),
				e.getMessage());

			return modelAndView;
		}
	}

	@Autowired
	EmployeeRepository employeeRepository;

	@Autowired
	ActiveUserRepository activeUserRepository;
}
