package edu.uark.registerapp.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.Optional;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import edu.uark.registerapp.commands.activeUsers.ActiveUserDeleteCommand;
import edu.uark.registerapp.controllers.enums.ViewNames;
import edu.uark.registerapp.models.api.ApiResponse;
import edu.uark.registerapp.commands.products.ProductCreateCommand;
import edu.uark.registerapp.commands.products.ProductDeleteCommand;
import edu.uark.registerapp.commands.products.ProductUpdateCommand;
import edu.uark.registerapp.models.api.Product;
import edu.uark.registerapp.commands.activeUsers.ValidateActiveUserCommand;
import edu.uark.registerapp.commands.exceptions.UnauthorizedException;
import edu.uark.registerapp.controllers.enums.ViewModelNames;
import edu.uark.registerapp.controllers.enums.QueryParameterMessages;
import edu.uark.registerapp.controllers.enums.QueryParameterNames;
import edu.uark.registerapp.models.entities.ActiveUserEntity;
import edu.uark.registerapp.models.enums.EmployeeClassification;

@RestController
@RequestMapping(value = "/api")
public class SignInRestController extends BaseController {
protected ModelAndView setErrorMessageFromQueryString(
ModelAndView modelAndView,final Map<String, String> queryParameters) {
		if (!queryParameters.containsKey(QueryParameterNames.ERROR_CODE.getValue())) {
			return modelAndView;
		}

		try {
			modelAndView = this.setErrorMessageFromQueryString(modelAndView,
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
	
	
@DeleteMapping(value = "/signOut")
public @ResponseBody ApiResponse remove(
final HttpServletRequest req) {

this.activeUserDeleteCommand
.setSessionKey(req.getSession().getId())
.execute();

return (new ApiResponse())
.setRedirectUrl(ViewNames.SIGN_IN.getRoute());
}

@DeleteMapping(value = "/{productId}")
	public @ResponseBody ApiResponse deleteProduct(
		@PathVariable final UUID productId
	) {

		this.productdeletecommand
			.setProductId(productId)
			.execute();

		return new ApiResponse();
	}

@Autowired
private ValidateActiveUserCommand validateActiveUserCommand;

@Autowired
private ProductCreateCommand productcreatecommand;
	
@Autowired
private ProductDeleteCommand productdeletecommand;
	
@Autowired
private ProductUpdateCommand productupdatecommand;

@Autowired
private ActiveUserDeleteCommand activeuserdeletecommand;
}

