package edu.uark.registerapp.controllers;

import javax.servlet.http.HttpServletRequest;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import edu.uark.registerapp.controllers.enums.ViewNames;

import edu.uark.registerapp.models.api.EmployeeSignIn;
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
			// TO DO: Redirect to employeeDetail
		}

		return new ModelAndView(ViewNames.SIGN_IN.getViewName());
	}

	@RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	public ModelAndView performSignIn(
		EmployeeSignIn signIn,
		HttpServletRequest request)
	{

		// TODO: Use the credentials provided in the request body
		//  and the "id" property of the (HttpServletRequest)request.getSession() variable
		//  to sign in the user


		return new ModelAndView(
			REDIRECT_PREPEND.concat(
				ViewNames.MAIN_MENU.getRoute()));
	}

	@RequestMapping(value = "/employeeDetail", method = RequestMethod.GET)
	public ModelAndView showEmployeeDetail()
	{
		return new ModelAndView(ViewNames.EMPLOYEE_DETAIL.getViewName());
	}

	@Autowired
	EmployeeRepository employeeRepository;
}
