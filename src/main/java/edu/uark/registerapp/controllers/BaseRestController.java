package edu.uark.registerapp.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import edu.uark.registerapp.commands.activeUsers.ValidateActiveUserCommand;
import edu.uark.registerapp.commands.exceptions.ConflictException;
import edu.uark.registerapp.commands.exceptions.NotFoundException;
import edu.uark.registerapp.commands.exceptions.UnauthorizedException;
import edu.uark.registerapp.commands.exceptions.UnprocessableEntityException;
import edu.uark.registerapp.controllers.enums.QueryParameterMessages;
import edu.uark.registerapp.controllers.enums.QueryParameterNames;
import edu.uark.registerapp.controllers.enums.ViewNames;
import edu.uark.registerapp.models.api.ApiResponse;
import edu.uark.registerapp.models.entities.ActiveUserEntity;
import edu.uark.registerapp.models.enums.EmployeeClassification;

public class BaseRestController extends BaseController {
	protected ApiResponse redirectSessionNotActive(
		final HttpServletResponse response
	) {

		response.setStatus(HttpStatus.FOUND.value());
		return (new ApiResponse())
			.setRedirectUrl(
				ViewNames.SIGN_IN.getRoute().concat(
					this.buildInitialQueryParameter(
						QueryParameterNames.ERROR_CODE.getValue(),
						QueryParameterMessages.SESSION_NOT_ACTIVE.getKeyAsString())));
	}

	protected ApiResponse redirectUserNotElevated(
		final HttpServletRequest request,
		final HttpServletResponse response
	) {

		return this.redirectUserNotElevated(request, response, ViewNames.MAIN_MENU.getRoute());
	}

	protected ApiResponse redirectUserNotElevated(
		final HttpServletRequest request,
		final HttpServletResponse response,
		final String redirectRoute
	) {

		try {
			final ActiveUserEntity activeUserEntity =
				this.validateActiveUserCommand
					.setSessionKey(request.getSession().getId())
					.execute();

			if (activeUserEntity == null) {
				return this.redirectSessionNotActive(response);
			} else if (!EmployeeClassification.isElevatedUser(activeUserEntity.getClassification())) {
				response.setStatus(HttpStatus.FOUND.value());

				return (new ApiResponse())
					.setRedirectUrl(
						redirectRoute.concat(
							this.buildInitialQueryParameter(
								QueryParameterNames.ERROR_CODE.getValue(),
								QueryParameterMessages.NO_PERMISSIONS_FOR_ACTION.getKeyAsString())));
			}
		} catch (final UnauthorizedException e) {
			return this.redirectSessionNotActive(response);
		}

		return new ApiResponse();
	}

	@ExceptionHandler({
		ConflictException.class,
		NotFoundException.class,
		UnauthorizedException.class,
		UnprocessableEntityException.class
	})
	public @ResponseBody ApiResponse handleError(final Exception e) {
		return (new ApiResponse()).setErrorMessage(e.getMessage());
	}

	// Properties
	@Autowired
	private ValidateActiveUserCommand validateActiveUserCommand;
}
