package edu.uark.registerapp.commands.employees.helpers;

import org.apache.commons.lang3.StringUtils;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

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

    // Password hashing for employees
    public static byte[] hashPassword(final String password) {

        // This NoSuchAlgorithmException should maybe not be handled within the method
        try {
            final MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");

            // Convert password string to byte array
            messageDigest.update(password.getBytes());
            return messageDigest.digest();
        } catch(final NoSuchAlgorithmException e){
            // I don't know how to handle errors but this probably won't happen anyway
            System.out.println("SHA-256 is not valid");
            return new byte[0];
        }
    }

    private static final int EMPLOYEE_ID_MAXIMUM_LENGTH = 5;
}