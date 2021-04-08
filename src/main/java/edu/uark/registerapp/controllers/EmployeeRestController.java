package edu.uark.registerapp.controllers;

import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import edu.uark.registerapp.commands.employees.ActiveEmployeeExistsQuery;
import edu.uark.registerapp.commands.employees.EmployeeCreateCommand;
import edu.uark.registerapp.commands.employees.EmployeeUpdateCommand;
import edu.uark.registerapp.commands.exceptions.NotFoundException;
import edu.uark.registerapp.controllers.enums.QueryParameterNames;
import edu.uark.registerapp.controllers.enums.ViewNames;
import edu.uark.registerapp.models.api.ApiResponse;
import edu.uark.registerapp.models.api.Employee;

@RestController
@RequestMapping(value = "/api/employee")
public class EmployeeRestController extends BaseRestController {
	@RequestMapping(value = "/", method = RequestMethod.POST)
	public @ResponseBody ApiResponse createEmployee(
		@RequestBody final Employee employee,
		final HttpServletRequest request,
		final HttpServletResponse response
	) {

		boolean isInitialEmployee = false;
		ApiResponse canCreateEmployeeResponse;

		try {
			this.activeEmployeeExistsQuery.execute();

			canCreateEmployeeResponse =
				this.redirectUserNotElevated(request, response);
		} catch (final NotFoundException e) {
			isInitialEmployee = true;
			canCreateEmployeeResponse = new ApiResponse();
		}

		if (!canCreateEmployeeResponse.getRedirectUrl().equals(StringUtils.EMPTY)) {
			return canCreateEmployeeResponse;
		}

		final Employee createdEmployee =
			this.employeeCreateCommand
				.setApiEmployee(employee)
				.setIsInitialEmployee(isInitialEmployee)
				.execute();

		if (isInitialEmployee) {
			createdEmployee
				.setRedirectUrl(
					ViewNames.SIGN_IN.getRoute().concat(
						this.buildInitialQueryParameter(
							QueryParameterNames.EMPLOYEE_ID.getValue(),
							createdEmployee.getEmployeeId())));
		}

		return createdEmployee.setIsInitialEmployee(isInitialEmployee);
	}

	@RequestMapping(value = "/{employeeId}", method = RequestMethod.PATCH)
	public @ResponseBody ApiResponse updateEmployee(
		@PathVariable final UUID employeeId,
		@RequestBody final Employee employee,
		final HttpServletRequest request,
		final HttpServletResponse response
	) {

		final ApiResponse elevatedUserResponse =
			this.redirectUserNotElevated(request, response);
		if (!elevatedUserResponse.getRedirectUrl().equals(StringUtils.EMPTY)) {
			return elevatedUserResponse;
		}

		return this.employeeUpdateCommand
			.setEmployeeId(employeeId)
			.setApiEmployee(employee)
			.execute();
	}

	// Properties
	@Autowired
	private EmployeeCreateCommand employeeCreateCommand;
	
	@Autowired
	private EmployeeUpdateCommand employeeUpdateCommand;
	
	@Autowired
	private ActiveEmployeeExistsQuery activeEmployeeExistsQuery;
}