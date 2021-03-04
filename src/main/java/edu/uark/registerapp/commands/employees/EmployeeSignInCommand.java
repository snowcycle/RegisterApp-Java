package edu.uark.registerapp.commands.employees;

import edu.uark.registerapp.models.api.EmployeeSignIn;
import edu.uark.registerapp.models.repositories.EmployeeRepository;


public class EmployeeSignInCommand {

	private EmployeeSignIn employeeSignIn;
	private String sessionKey;
	
	// TO DO: Validate employeeSignIn's employeeId and password
	boolean validateSignIn()
	{
		if(employeeSignIn.getId().equals(""))
			return false;

		String idNum = employeeSignIn.getId();
		for(int i = 0; i < idNum.length(); i++)
		{
			if(!(idNum.charAt(i) >= 0 && idNum.charAt(i) <= 9))
				return false;
		}

		if(employeeSignIn.getPassword().equals(""))
			return false;
		
		return true; 
		
	}
	// TO DO: Query employee by id and verify matching passwords
	/*if(validateSignIn())
	{
		EmployeeRepository.queryByEmployeeId() 
	}*/
}
