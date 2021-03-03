package edu.uark.registerapp.commands.employees;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.uark.registerapp.models.repositories.EmployeeRepository;
import edu.uark.registerapp.commands.exceptions.NotFoundException;

@Service
public class ActiveEmployeeExistsQuery {
	public void execute()
	{
		final Boolean activeEmployeeExists =
			this.employeeRepository.existsByIsActive(true);
		
		if (!activeEmployeeExists)
		{
			throw new NotFoundException("Employee");
		}
	}

	@Autowired
	private EmployeeRepository employeeRepository;
}
