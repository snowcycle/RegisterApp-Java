// Task 4 - Assigned to Ben Thiele
package edu.uark.registerapp.controllers;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import edu.uark.registerapp.commands.employees.ActiveEmployeeExistsQuery;
import edu.uark.registerapp.commands.employees.EmployeeSignInCommand;
import edu.uark.registerapp.commands.exceptions.NotFoundException;
import edu.uark.registerapp.controllers.enums.QueryParameterNames;
import edu.uark.registerapp.controllers.enums.ViewModelNames;
import edu.uark.registerapp.controllers.enums.ViewNames;
import edu.uark.registerapp.models.api.EmployeeSignIn;

@Controller // Defines Class as route handler
@RequestMapping(value = "/") // Maps class to url address
public class SignInRouteController extends BaseRouteController {
	@RequestMapping(method = RequestMethod.GET) // Method responds to GET request
	// Show sign in screen
	public ModelAndView showSignIn(
		@RequestParam final Map<String, String> queryParameters // Extract query string
	) {
		// Check if active employees exist, redirect to employee detail page if not
		try {
			this.activeEmployeeExistsQuery.execute(); 
		} catch (NotFoundException e) {
			return new ModelAndView(
				REDIRECT_PREPEND.concat(
					ViewNames.EMPLOYEE_DETAIL.getRoute()));
		}
        //If active employees exist go to sign in
		ModelAndView modelAndView =
			this.setErrorMessageFromQueryString(
				new ModelAndView(ViewNames.SIGN_IN.getViewName()), //Set view
				queryParameters);
		
		if (queryParameters.containsKey(QueryParameterNames.EMPLOYEE_ID.getValue())) {
			modelAndView.addObject(
				ViewModelNames.EMPLOYEE_ID.getValue(), // Set model
				queryParameters.get(QueryParameterNames.EMPLOYEE_ID.getValue()));
		}

		return modelAndView;
	}

	// Respond to POST request
	@RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	public ModelAndView performSignIn(
		EmployeeSignIn employeeSignIn, // Object to hold employee credentials
		HttpServletRequest request
	) {

		try {
			// Object validates employee information and session
			// If fails, error message is displayed
			this.employeeSignInCommand
				.setSessionId(request.getSession().getId())
				.setEmployeeSignIn(employeeSignIn)
				.execute();
		} catch (Exception e) {
			ModelAndView modelAndView =
				new ModelAndView(ViewNames.SIGN_IN.getViewName()); 

			modelAndView.addObject(
				ViewModelNames.ERROR_MESSAGE.getValue(),
				e.getMessage());
			modelAndView.addObject(
				ViewModelNames.EMPLOYEE_ID.getValue(),
				employeeSignIn.getEmployeeId());

			return modelAndView;
		}

		// Successful login moves user to main menu
		return new ModelAndView(
			REDIRECT_PREPEND.concat(
				ViewNames.MAIN_MENU.getRoute()));
	}

	// Properties
	@Autowired
	private EmployeeSignInCommand employeeSignInCommand; 

	@Autowired
	private ActiveEmployeeExistsQuery activeEmployeeExistsQuery;
}
