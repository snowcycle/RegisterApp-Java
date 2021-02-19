package edu.uark.registerapp.controllers.enums;

public enum QueryParameterNames {
	NOT_DEFINED(""),
	ERROR_CODE("errorCode");
	
	public String getValue() {
		return value;
	}
	
	private String value;

	private QueryParameterNames(final String value) {
		this.value = value;
	}
}
