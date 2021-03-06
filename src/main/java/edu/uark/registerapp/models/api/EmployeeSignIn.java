package edu.uark.registerapp.models.api;

public class EmployeeSignIn {
	
	private String employeeId;
	private String password;

	public EmployeeSignIn(String employeeId, String password)
	{
		this.employeeId = employeeId;
		this.password = password;
	}

	public EmployeeSignIn(int employeeId, String password)
	{
		this.employeeId = Integer.toString(employeeId);
		this.password = password;
	}

	public EmployeeSignIn()
	{
		this.employeeId = "";
		this.password = "";
	}

	public String getId()
	{
		return this.employeeId;
	}

	public String getPassword()
	{
		return this.password;
	}
}
