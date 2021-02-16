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

import edu.uark.registerapp.commands.products.ProductCreateCommand;
import edu.uark.registerapp.commands.products.ProductDeleteCommand;
import edu.uark.registerapp.commands.products.ProductUpdateCommand;
import edu.uark.registerapp.controllers.enums.ViewNames;
import edu.uark.registerapp.models.api.ApiResponse;
import edu.uark.registerapp.models.api.Product;

@RestController
@RequestMapping(value = "/api/product")
public class ProductRestController extends BaseRestController {
	@RequestMapping(value = "/", method = RequestMethod.POST)
	public @ResponseBody ApiResponse createProduct(
		@RequestBody final Product product,
		final HttpServletRequest request,
		final HttpServletResponse response
	) {

		final ApiResponse elevatedUserResponse =
			this.redirectUserNotElevated(
				request,
				response,
				ViewNames.PRODUCT_LISTING.getRoute());

		if (!elevatedUserResponse.getRedirectUrl().equals(StringUtils.EMPTY)) {
			return elevatedUserResponse;
		}

		return this.productCreateCommand
			.setApiProduct(product)
			.execute();
	}

	@RequestMapping(value = "/{productId}", method = RequestMethod.PUT)
	public @ResponseBody ApiResponse updateProduct(
		@PathVariable final UUID productId,
		@RequestBody final Product product,
		final HttpServletRequest request,
		final HttpServletResponse response
	) {

		final ApiResponse elevatedUserResponse =
			this.redirectUserNotElevated(
				request,
				response,
				ViewNames.PRODUCT_LISTING.getRoute());

		if (!elevatedUserResponse.getRedirectUrl().equals(StringUtils.EMPTY)) {
			return elevatedUserResponse;
		}

		return this.productUpdateCommand
			.setProductId(productId)
			.setApiProduct(product)
			.execute();
	}

	@RequestMapping(value = "/{productId}", method = RequestMethod.DELETE)
	public @ResponseBody ApiResponse deleteProduct(
		@PathVariable final UUID productId, 
		final HttpServletRequest request,
		final HttpServletResponse response
	) {

		final ApiResponse elevatedUserResponse =
			this.redirectUserNotElevated(
				request,
				response,
				ViewNames.PRODUCT_LISTING.getRoute());

		if (!elevatedUserResponse.getRedirectUrl().equals(StringUtils.EMPTY)) {
			return elevatedUserResponse;
		}

		this.productDeleteCommand
			.setProductId(productId)
			.execute();

		return new ApiResponse();
	}

	// Properties
	@Autowired
	private ProductCreateCommand productCreateCommand;
	
	@Autowired
	private ProductDeleteCommand productDeleteCommand;
	
	@Autowired
	private ProductUpdateCommand productUpdateCommand;
}
