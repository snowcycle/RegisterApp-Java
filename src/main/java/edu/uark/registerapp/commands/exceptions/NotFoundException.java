package edu.uark.registerapp.commands.exceptions;

public class NotFoundException extends RuntimeException {
	public NotFoundException(String objectName) {
		super(objectName.concat(" was not found."));
	}

	private static final long serialVersionUID = 773195628141355966L;
}
