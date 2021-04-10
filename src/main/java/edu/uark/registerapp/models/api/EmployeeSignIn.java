// Sprint 2 - Task 4. Assigned to Ben Thiele. Taken from sprint 2 example with slight reorganization.
package edu.uark.registerapp.models.api;

import org.apache.commons.lang3.StringUtils;

public class EmployeeSignIn {
    // Instance Variables
    private String employeeId;
    private String password;

    // Constructor - Sets strings to empty.
	public EmployeeSignIn() {
		this.password = StringUtils.EMPTY;
		this.employeeId = StringUtils.EMPTY;
	}

    // Setters and Getters
	public String getEmployeeId() {
		return this.employeeId;
	}

    public EmployeeSignIn setEmployeeId(final String employeeId) {
		this.employeeId = employeeId;
		return this;
	}

    public String getPassword() {
		return this.password;
	}

    public EmployeeSignIn setPassword(final String password) {
		this.password = password;
		return this;
	}
}
