package edu.uark.registerapp.commands.employees.helpers;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.lang3.StringUtils;

public class EmployeeHelper {
	public static String padEmployeeId(final int employeeId) {
		final String employeeIdAsString = Integer.toString(employeeId);

		return ((employeeIdAsString.length() < EMPLOYEE_ID_MAXIMUM_LENGTH)
			? StringUtils.leftPad(
				employeeIdAsString,
				EMPLOYEE_ID_MAXIMUM_LENGTH,
				"0")
			: employeeIdAsString);
	}

	// Hash password using message digest
	public static byte[] hashPassword(final String password) {
		try {
			final MessageDigest messageDigest =
				MessageDigest.getInstance("SHA-256");

			messageDigest.update(password.getBytes());

			return messageDigest.digest();
		} catch (final NoSuchAlgorithmException e) {
			return new byte[0];
		}
	}
	
	private static final int EMPLOYEE_ID_MAXIMUM_LENGTH = 5;
}