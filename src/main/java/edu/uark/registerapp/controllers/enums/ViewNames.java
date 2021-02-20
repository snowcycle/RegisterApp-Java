package edu.uark.registerapp.controllers.enums;

public enum ViewNames {
	PRODUCT_DETAIL("productDetail"),
	PRODUCT_LISTING("productListing", "/"),
	MAIN_MENU("mainMenu");
	
	public String getRoute() {
		return this.route;
	}
	public String getViewName() {
		return this.viewName;
	}

	private String route;
	private String viewName;

	private ViewNames(final String viewName) {
		this(viewName, "/".concat(viewName));
	}

	private ViewNames(final String viewName, final String route) {
		this.route = route;
		this.viewName = viewName;
	}
}
