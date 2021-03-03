package edu.uark.registerapp.commands.employees;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.Optional;

import javax.transaction.Transactional;

import edu.uark.registerapp.models.api.EmployeeSignIn;
import edu.uark.registerapp.models.repositories.ActiveUserRepository;
import edu.uark.registerapp.models.repositories.EmployeeRepository;
import edu.uark.registerapp.commands.ResultCommandInterface;
import edu.uark.registerapp.commands.employees.helpers.EmployeeHelper;
import edu.uark.registerapp.commands.exceptions.UnauthorizedException;
import edu.uark.registerapp.commands.exceptions.UnprocessableEntityException;
import edu.uark.registerapp.models.api.Employee;
import edu.uark.registerapp.models.entities.ActiveUserEntity;
import edu.uark.registerapp.models.entities.EmployeeEntity;

@Service
public class EmployeeSignInCommand implements ResultCommandInterface<Employee>{

	public Employee execute() {
		this.validateProperties();
		return new Employee(this.SignInActiveUser());
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

	private Optional<EmployeeEntity> queryEmployeeUsingID() {
		int employeeID = Integer.parseInt(this.getEmployeeId());
		String password = this.getPassword();
		Optional<EmployeeEntity> employeeEntity = this.employeeRepository.findByEmployeeId(employeeID);
		if (!employeeEntity.isPresent() || !Arrays.equals(employeeEntity.get().getPassword(), EmployeeHelper.hashPassword(password)))
		{
			throw new UnauthorizedException();
		}

		return employeeEntity;
	}

	@Transactional
	private EmployeeEntity SignInActiveUser(){
		Optional<EmployeeEntity> employeeEntity = queryEmployeeUsingID();

		//query the activeuser table for a record with the employee ID
		Optional<ActiveUserEntity> activeUserEntity = 
			this.activeUserRepository.findByEmployeeId(employeeEntity.get().getId());

			//create a new active user in the record in the database if there isn't one already
		if(!activeUserEntity.isPresent()) {
			this.activeUserRepository.save(
				(new ActiveUserEntity())
					.setSessionKey(this.getSessionId())
					.setEmployeeId(employeeEntity.get().getId())
					.setClassification(employeeEntity.get().getClassification())
					.setName(employeeEntity.get().getFirstName().concat(" ").concat(employeeEntity.get().getLastName()))
				);
		}
		else {
			this.activeUserRepository.save(
				activeUserEntity.get().setSessionKey(this.getSessionId())
			);
		}
		return employeeEntity.get();
	}


	//properties
	@Autowired
	private EmployeeRepository employeeRepository;
	@Autowired
	private ActiveUserRepository activeUserRepository;

	private EmployeeSignIn employeeSignIn;
	public String getEmployeeId(){
		return this.employeeSignIn.getEmployeeId();
	}

	public String getPassword(){
		return this.employeeSignIn.getPassword();
	}
	private String sessionId; //session key
	public String getSessionId() {
		return this.sessionId;
	}

	public EmployeeSignInCommand setSessionId(final String sessionId) {
		this.sessionId = sessionId;
		return this;
	}

	public EmployeeSignInCommand setEmployeeSignIn(final EmployeeSignIn employeeSignIn) {
		this.employeeSignIn = employeeSignIn;
		return this;
	}
}
