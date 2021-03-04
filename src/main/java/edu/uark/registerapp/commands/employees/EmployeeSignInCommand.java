package edu.uark.registerapp.commands.employees;

import java.util.Optional;
import java.util.Arrays; 

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import javax.transaction.Transactional;

import edu.uark.registerapp.models.api.EmployeeSignIn;
import edu.uark.registerapp.models.entities.ActiveUserEntity;
import edu.uark.registerapp.models.entities.EmployeeEntity;
import edu.uark.registerapp.models.repositories.ActiveUserRepository;
import edu.uark.registerapp.models.repositories.EmployeeRepository;


public class EmployeeSignInCommand {

	private EmployeeSignIn employeeSignIn;
	private String sessionKey;

	EmployeeSignInCommand(EmployeeSignIn employeeSignIn, String sessionKey)
	{
		this.employeeSignIn = employeeSignIn;
		this.sessionKey = sessionKey;
	}

	@Transactional
	boolean execute()
	{
		if (!validateSignIn())	// Credentials are invalid
			return false;

		Optional<EmployeeEntity> employee =		// Finds employee by employee ID
			employeeRepository.findByEmployeeId(
				Integer.parseInt(employeeSignIn.getId()));
		
		if (employee.isEmpty())		// Employee does not exist
			return false;

		if (!Arrays.equals(			// Passwords do NOT match
			employeeSignIn.getPassword().getBytes(),
			employee.get().getPassword()))
		{
			return false;
		}

		// NOTE: At this point, we have a valid sign in

		Optional<ActiveUserEntity> activeUser =		// Finds active user by UUID
			activeUserRepository.findByEmployeeId(employee.get().getId());

		if (activeUser.isPresent())		// Updates existing active user's session key
		{
			ActiveUserEntity user = activeUser.get().setSessionKey(sessionKey);
			activeUserRepository.save(user);
		}
		else	// Creates new active user
		{
			ActiveUserEntity user = new ActiveUserEntity();

			user.setClassification(employee.get().getClassification());

			// Create full name then set active user name
			String employeeName = employee.get().getFirstName();
			employeeName.concat(" ").concat(employee.get().getLastName());
			user.setName(employeeName);

			user.setEmployeeId(employee.get().getId());
			user.setSessionKey(sessionKey);

			activeUserRepository.save(user);
		}

		return true;
	}
	
	private boolean validateSignIn()
	{
		String employeeId = employeeSignIn.getId();

		if (StringUtils.isBlank(employeeId) || employeeId.length() > 5)
			return false;

		for (int i = 0; i < employeeId.length(); i++)
		{
			if(!(employeeId.charAt(i) >= 0 && employeeId.charAt(i) <= 9))
				return false;
		}

		if(employeeSignIn.getPassword().equals(""))
			return false;
		
		return true; 
	}

	@Autowired
	private EmployeeRepository employeeRepository;

	@Autowired
	private ActiveUserRepository activeUserRepository;
}
