package edu.uark.registerapp.commands.employees;

import java.util.Optional;
import java.util.Arrays;

import org.apache.commons.lang3.StringUtils;

import javax.transaction.Transactional;

import edu.uark.registerapp.models.api.EmployeeSignIn;
import edu.uark.registerapp.models.entities.ActiveUserEntity;
import edu.uark.registerapp.models.entities.EmployeeEntity;
import edu.uark.registerapp.models.repositories.ActiveUserRepository;
import edu.uark.registerapp.models.repositories.EmployeeRepository;


public class EmployeeSignInCommand {

	private EmployeeSignIn employeeSignIn;
	private String sessionKey;

	public EmployeeSignInCommand(EmployeeSignIn employeeSignIn, String sessionKey)
	{
		this.employeeSignIn = employeeSignIn;
		this.sessionKey = sessionKey;
	}

	EmployeeSignInCommand()
	{
		this.employeeSignIn = new EmployeeSignIn();
		this.sessionKey = "";
	}

	@Transactional
	public boolean execute()
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

			// New active user entry - complete
			activeUserRepository.save(user);

			// Update 'active' column on employee table to true
			employee.get().setIsActive(true);
			employeeRepository.save(employee.get());
		}

		return true;
	}
	
	private boolean validateSignIn()
	{
		String employeeId = employeeSignIn.getId();

		if (StringUtils.isBlank(employeeId) || employeeId.length() > 5)
		{
			return false;
		}

		for (int i = 0; i < employeeId.length(); i++)
		{
			if(!(employeeId.charAt(i) >= '0' && employeeId.charAt(i) <= '9'))
			{
				return false;
			}
		}

		if(StringUtils.isBlank(employeeSignIn.getPassword()))
		{
			return false;
		}
		
		return true; 
	}

	/*
	ONLY CALLED BY SignInRouteController
	PROBABLY A BAD WAY TO DO THIS
	I think @Autowired doesn't work because this class is not a controller...
	...hence SignInRouteController setting this property
	*/
	public void setEmployeeRepository(EmployeeRepository employeeRepository)
	{
		this.employeeRepository = employeeRepository;
	}

	// THIS IS ALSO PROBABLY BAD FOR THE SAME REASON AS ABOVE
	public void setActiveUserRepository(ActiveUserRepository activeUserRepository)
	{
		this.activeUserRepository = activeUserRepository;
	}

	private EmployeeRepository employeeRepository;
	private ActiveUserRepository activeUserRepository;
}
