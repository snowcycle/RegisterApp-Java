package edu.uark.registerapp.models.api;
//This is from ApiResponse.java, aka under models/api
import org.apache.commons.lang3.StringUtils;

public class EmployeeSignIn {
	private String employeeId;
	
	public String getId() {
		return this.employeeId;
	}
	
	public SignIn setId(final String employeeId) {
		this.employeeId = employeeId;
		return this;
	}

	private String password;
	public String getPword() {
		return this.password;
	}
	public EmployeeSignIn setPword( final String password) {
		this.password = password;
		return this;
	}
	
	public EmployeeSignIn(final String password, final String employeeId) {
		this.password = StringUtils.EMPTY;
		this.employeeId = StringUtils.EMPTY;
	}

}