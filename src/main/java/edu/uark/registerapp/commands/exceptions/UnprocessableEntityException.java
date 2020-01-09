package edu.uark.registerapp.commands.exceptions;

public class UnprocessableEntityException extends RuntimeException {
	public UnprocessableEntityException(String parameterName) {
		super("Missing or invalid parameter: ".concat(parameterName));
	}

	private static final long serialVersionUID = 6128380142041898414L;
}
