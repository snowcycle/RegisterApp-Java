package edu.uark.registerapp.commands.employees;

import org.springframework.beans.factory.annotation.Autowired;

import edu.uark.registerapp.models.api.EmployeeSignIn;
import edu.uark.registerapp.commands.exceptions.NotFoundException;
import edu.uark.registerapp.commands.exceptions.UnprocessableEntityException;
import edu.uark.registerapp.models.repositories.EmployeeRepository;
import edu.uark.registerapp.models.entities.EmployeeEntity;

import javax.transaction.Transactional;


public class EmployeeSignInCommand {
	//properties
		private EmployeeSignIn signInObject;
		private String sessionKey;

		public EmployeeSignIn getEmployeeSignIn() {
			return this.signInObject;
		}
		public String getSessionKey() {
			return this.sessionKey;
		}
		public EmployeeSignInCommand setEmployeeSignIn(EmployeeSignIn emp) {
			this.signInObject = emp;
			return this;
		}
		public EmployeeSignInCommand setSessionKey(String key) {
			this.sessionKey = key;
			return this;
		}


	//functions

	private void validateEmployee() {
		if (this.signInObject.getId().isEmpty()) {
			throw new NotFoundException("Employee ID");
		}
		if (this.signInObject.getId() != (int)this.signInObject.getId()) {
			throw new NotFoundException("Employee ID");
		}
		if (this.signInObject.getPword().isEmpty()) {
			throw new UnprocessableEntityException("password");
		}
	}

	

	@Autowired
	private EmployeeRepository employeeRepository;

	
}