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

    public static byte[] hashPassword(final String password) {
        // This exception should maybe not be handled within the method
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            // Convert password string to byte array
            byte [] passwordBytes = password.getBytes(StandardCharsets.UTF_8);
            return messageDigest.digest(passwordBytes);
        } catch(NoSuchAlgorithmException e){
            // I don't know how to handle errors but this probably won't happen anyway
            System.out.println("Sorry, SHA-256 doesn't exist actually");
            return new byte[0];
        }
    }

    private static final int EMPLOYEE_ID_MAXIMUM_LENGTH = 5;
}