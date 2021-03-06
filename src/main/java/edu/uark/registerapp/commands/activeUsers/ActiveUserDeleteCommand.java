package edu.uark.registerapp.commands.activeUsers;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.uark.registerapp.commands.VoidCommandInterface;
import edu.uark.registerapp.models.entities.ActiveUserEntity;
import edu.uark.registerapp.models.entities.EmployeeEntity;
import edu.uark.registerapp.models.repositories.ActiveUserRepository;
import edu.uark.registerapp.models.repositories.EmployeeRepository;

@Service
public class ActiveUserDeleteCommand implements VoidCommandInterface {

	private String sessionKey;
	private ActiveUserRepository activeUserRepository;
	private EmployeeRepository employeeRepository;

	public ActiveUserDeleteCommand(
		String sessionKey,
		ActiveUserRepository activeUserRepository,
		EmployeeRepository employeeRepository)
	{
		this.sessionKey = sessionKey;
		this.activeUserRepository = activeUserRepository;
		this.employeeRepository = employeeRepository;
	}

	public ActiveUserDeleteCommand()
	{
		this.sessionKey = "";
	}

	@Override
	@Transactional
	public void execute()
	{
		if (activeUserRepository == null)
		{
			return;
		}

		final Optional<ActiveUserEntity> activeUserEntity =
			this.activeUserRepository.findBySessionKey(this.sessionKey);

		if (activeUserEntity.isPresent())	// Found active user
		{
			if (employeeRepository != null)
			{
				Optional<EmployeeEntity> employee =
					employeeRepository.findById(activeUserEntity.get().getEmployeeId());

				if (employee.isPresent())	// Found corresponding entry in 'employee' table
				{
					employee.get().setIsActive(false);
					employeeRepository.save(employee.get());
				}
			}

			this.activeUserRepository.delete(activeUserEntity.get());
		}
	}

	public String getSessionKey()
	{
		return this.sessionKey;
	}
}
