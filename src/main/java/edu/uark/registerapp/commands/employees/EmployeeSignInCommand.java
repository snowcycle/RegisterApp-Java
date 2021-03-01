package edu.uark.registerapp.commands.employees;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.apache.commons.lang3.StringUtils;
import edu.uark.registerapp.models.api.EmployeeSignIn;
import edu.uark.registerapp.models.repositories.EmployeeRepository;
import edu.uark.registerapp.commands.ResultCommandInterface;
import edu.uark.registerapp.commands.exceptions.UnprocessableEntityException;
import edu.uark.registerapp.models.api.Employee;

@Service
public class EmployeeSignInCommand implements ResultCommandInterface<Employee>{
	//properties
	private EmployeeSignIn employeeSignIn;
	private String sessionId; //session key
	@Autowired
	private EmployeeRepository employeeRepository;
	@Autowired
	public String getSessionId() {
		return this.sessionId;
	}

	public Employee execute() {
		return null;
	}

	private void validateProperties() {
		//ID should not be blank. Should be a number
		if (StringUtils.isBlank(this.employeeSignIn.getEmployeeId())) {
			throw new UnprocessableEntityException("employee ID");
		}

		//ID should only be a number
		try {
			Integer.parseInt(this.employeeSignIn.getEmployeeId());
		} catch (NumberFormatException e) {
			throw new UnprocessableEntityException("employee ID");
		}

		if (StringUtils.isBlank(this.employeeSignIn.getPassword())){
			throw new UnprocessableEntityException("password");
		}
	}

	private void queryEmployeebyID() {
		
	}

}
