package edu.uark.registerapp.models.api;

import org.apache.commons.lang3.StringUtils;

public class ApiResponse {
	private String errorMessage;
	public String getMessage() {
		return this.errorMessage;
	}
	public ApiResponse setErrorMessage(final String message) {
		this.errorMessage = message;
		return this;
	}

	private String redirectUrl;
	public String getRedirectUrl() {
		return this.redirectUrl;
	}
	public ApiResponse setRedirectUrl(final String redirectUrl) {
		this.redirectUrl = redirectUrl;
		return this;
	}

	private boolean isEmpty;
	public boolean getIsEmpty() {
		return this.isEmpty;
	}
	public ApiResponse setIsEmpty(final boolean isEmpty) {
		this.isEmpty = isEmpty;
		return this;
	}

	public ApiResponse() {
		this(true, StringUtils.EMPTY, StringUtils.EMPTY);
	}
	public ApiResponse(final boolean isEmpty) {
		this(isEmpty, StringUtils.EMPTY, StringUtils.EMPTY);
	}
	public ApiResponse(
		final boolean isEmpty,
		final String errorMessage,
		final String redirectUrl
	) {
		this.isEmpty = isEmpty;
		this.redirectUrl = redirectUrl;
		this.errorMessage = errorMessage;
	}
}
