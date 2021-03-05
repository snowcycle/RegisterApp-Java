package edu.uark.registerapp.controllers;

//HttpServletRequest allows a servlet to get info about a client request
import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import edu.uark.registerapp.controllers.enums.ViewModelNames;
import edu.uark.registerapp.controllers.enums.ViewNames;
import edu.uark.registerapp.models.entities.ActiveUserEntity;

@Controller
@RequestMapping(value = "/mainMenu")
public class MainMenuRouteController extends BaseRouteController{
	//GET request with /mainMenu route/endpoint path
	@RequestMapping(method = RequestMethod.GET)
	//@RequestParam reads form data and binds it to the specified parameters; maps the request parameter to query parameter
	//parameters for get request: Map<String, String> and HttpServletRequest
	//Map object is a collection of keyed-in elements using any type of key
	public ModelAndView start(
		@RequestParam final Map<String,String> queryParameters, 
		final HttpServletRequest request) {
		//if there's an active user in the current session, add any error messages received in requestParam to the view
		//serve up the main menu view/doc
		//ELSE: immediately redirect to the Sign-In view document with an appropriate error message
		final Optional<ActiveUserEntity> 
		activeUserEntity = 
			this.getCurrentUser(request);
		if (!activeUserEntity.isPresent()
		) {
			return this.buildInvalidSessionResponse();
		}
		
		ModelAndView modelAndView = 
			this.setErrorMessageFromQueryString(
				new ModelAndView(ViewNames.MAIN_MENU.getViewName()),
				queryParameters);
		
		modelAndView.addObject(
			ViewModelNames.IS_ELEVATED_USER.getValue(),
			this.isElevatedUser(activeUserEntity.get()));

			return modelAndView;
	}
}

