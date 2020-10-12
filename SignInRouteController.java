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
import edu.uark.registerapp.commands.products.ProductQuery;
import edu.uark.registerapp.commands.employees.ActiveEmployeeExistsQuery;
import edu.uark.registerapp.commands.employees.EmployeeSignInCommand;
import edu.uark.registerapp.commands.exceptions.NotFoundException;
import edu.uark.registerapp.controllers.enums.QueryParameterNames;
import edu.uark.registerapp.controllers.enums.ViewModelNames;
import edu.uark.registerapp.controllers.enums.ViewNames;
import edu.uark.registerapp.models.api.EmployeeSignIn;
import java.util.UUID;
import org.apache.commons.lang3.StringUtils;
import edu.uark.registerapp.models.api.Product;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;
import edu.uark.registerapp.commands.activeUsers.ValidateActiveUserCommand;
import edu.uark.registerapp.commands.exceptions.UnauthorizedException;
import edu.uark.registerapp.models.entities.ActiveUserEntity;
import edu.uark.registerapp.models.enums.EmployeeClassification;


@Controller
@RequestMapping(value = "/")
public class SignInRouteController extends BaseController {

	protected ModelAndView setErrorMessageFromQueryString(
		ModelAndView modelAndView,
		final Map<String, String> queryParameters
	) {

		if (!queryParameters.containsKey(QueryParameterNames.ERROR_CODE.getValue())) {
			return modelAndView;
		}

		try {
			modelAndView =
				this.setErrorMessageFromQueryString(
					modelAndView,
					Integer.parseInt(
						queryParameters.get(
							QueryParameterNames.ERROR_CODE.getValue())));
		} catch (final NumberFormatException e) { }

		return modelAndView;
	}
	protected ModelAndView setErrorMessageFromQueryString(
		final ModelAndView modelAndView,
		final Optional<Integer> errorCode
	) {

		if (!errorCode.isPresent()) {
			return modelAndView;
		}

		return this.setErrorMessageFromQueryString(modelAndView, errorCode.get());
	}

	protected Optional<ActiveUserEntity> getCurrentUser(
		final HttpServletRequest request
	) {

		try {
			return Optional.of(
				this.validateActiveUserCommand
					.setSessionKey(request.getSession().getId())
					.execute());
		} catch (final UnauthorizedException e) {
			return Optional.ofNullable(null);
		}
	}

	protected ModelAndView buildInvalidSessionResponse() {
		return new ModelAndView(
			REDIRECT_PREPEND.concat(
				ViewNames.SIGN_IN.getRoute().concat(
					this.buildInitialQueryParameter(
						QueryParameterNames.ERROR_CODE.getValue(),
						QueryParameterMessages.SESSION_NOT_ACTIVE.getKeyAsString()))));
	}

	protected boolean isElevatedUser(final ActiveUserEntity activeUserEntity) {
		return EmployeeClassification.isElevatedUser(
			activeUserEntity.getClassification());
	}

	protected ModelAndView buildNoPermissionsResponse() {
		return this.buildNoPermissionsResponse(ViewNames.MAIN_MENU.getRoute());
	}

	protected ModelAndView buildNoPermissionsResponse(final String redirectRoute) {
		return new ModelAndView(
			REDIRECT_PREPEND.concat(
				redirectRoute.concat(
					this.buildInitialQueryParameter(
						QueryParameterNames.ERROR_CODE.getValue(),
						QueryParameterMessages.NO_PERMISSIONS_TO_VIEW.getKeyAsString()))));
	}

	protected static final String REDIRECT_PREPEND = "Redirect:";

	
	private ModelAndView setErrorMessageFromQueryString(
		final ModelAndView modelAndView,
		final int errorCode
	) {

		final String errorMessage = QueryParameterMessages.mapMessage(errorCode);

		if (!StringUtils.isBlank(errorMessage)) {
			modelAndView.addObject(
				ViewModelNames.ERROR_MESSAGE.getValue(),
				errorMessage);
		}

		return modelAndView;
	}
	
///--------------------------------------------------------------------------------------------------------------------------	
@GetMapping    //This is the sign method/ it deals with the first part of task 4.
public ModelAndView sign(@RequestParam final Map<String, String> queryParameters) {
try {this.activeemployeeexistsquery.execute();} 
catch (NotFoundException e) {
return new ModelAndView(REDIRECT_PREPEND.concat(ViewNames.EMPLOYEE_DETAIL.getRoute()));
}

ModelAndView modelAndView = this.setErrorMessageFromQueryString(new ModelAndView(ViewNames.SIGN_IN.getViewName()),
queryParameters);

if (queryParameters.containsKey(QueryParameterNames.EMPLOYEE_ID.getValue())) {
modelAndView.addObject(
ViewModelNames.EMPLOYEE_ID.getValue(),
queryParameters.get(QueryParameterNames.EMPLOYEE_ID.getValue()));
}

return modelAndView;
}



//-------------------------------------------------------------------------------------------------------------------------------------
// This is the perform method, it is what actually performs the sign in 

@PostMapping(consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
public ModelAndView perform(EmployeeSignIn employee,HttpServletRequest req) {

try {this.employeesignincommand.setSessionId(req.getSession().getId()).setEmployeeSignIn(employee)
.execute();
}
 
catch (Exception e) {
ModelAndView modelAndView = new ModelAndView(ViewNames.SIGN_IN.getViewName());

modelAndView.addObject(ViewModelNames.ERROR_MESSAGE.getValue(),e.getMessage());
modelAndView.addObject(ViewModelNames.EMPLOYEE_ID.getValue(),employee.getEmployeeId());

return modelAndView;
}

return new ModelAndView(REDIRECT_PREPEND.concat(ViewNames.MAIN_MENU.getRoute()));
}


// These are the various properties that are inside of the code 
@Autowired
private ValidateActiveUserCommand validateActiveUserCommand;

@Autowired
private ProductQuery productquery;

@Autowired
private EmployeeSignInCommand employeesignincommand;

@Autowired
private ActiveEmployeeExistsQuery activeemployeeexistsquery;
} 