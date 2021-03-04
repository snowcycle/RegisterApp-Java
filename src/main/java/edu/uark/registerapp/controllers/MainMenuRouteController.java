package edu.uark.registerapp.controllers;

import java.util.UUID;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import edu.uark.registerapp.commands.exceptions.UnauthorizedException;
import edu.uark.registerapp.controllers.enums.ViewNames;

@Controller
@RequestMapping(value="/mainMenu")
public class MainMenuRouteController {
	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView start(
		@RequestParam Map<String, String> queryParameters,
		final HttpServletRequest httpServletRequest
	) {
		ModelAndView modelAndView = new ModelAndView(ViewNames.MAIN_MENU.getViewName(), queryParameters);
		final Optional<ActiveUserEntity> ActiveUserEntity =
			this.getCurrentUser(httpServletRequest);	
		return modelAndView;
	}

	protected Optional<ActiveUserEntity> getCurrentUser(final HttpServletRequest request) 
	{
		try {
			return Optional.of(
				this.validateActiveUserCommand
					.setSessionKey(request.getSession().getId())
					.execute());
		} catch (final UnauthorizedException e) {
			return Optional.ofNullable(null);
		}
	}
}
