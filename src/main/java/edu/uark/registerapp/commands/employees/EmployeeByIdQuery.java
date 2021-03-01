// Last updated 02/20/2021 by Jodi Mitchell
// This file grabs a specific employee by their ID
package edu.uark.registerapp.commands.employees;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.uark.registerapp.commands.ResultCommandInterface;
import edu.uark.registerapp.commands.exceptions.NotFoundException;
import edu.uark.registerapp.models.api.Employee;
import edu.uark.registerapp.models.entities.EmployeeEntity;
import edu.uark.registerapp.models.repositories.EmployeeRepository;

@Service
public class EmployeeByIdQuery implements ResultCommandInterface<Employee> 
{
	@Override
	public Employee execute() 
	{
		
		final Optional<EmployeeEntity> employeeEntity =
			this.employeeRepository.findByEmployeeId(this.employeeId);   // This is throwing errors because the
		Checking to see if the employee exist.							 // operations in EmployeeRepository.java are 
		if (employeeEntity.isPresent()){								 // commented out. I had to do this so the app 
			return new Employee(employeeEntity.get());					 //  will run as expected.
		} else {														 // I think Once we get everything put together 
			throw new NotFoundException("Employee");					 // with the routers we can uncomment those 												                 // functions.
		}
	}

	// Getters and Setters
	private int employeeId;
	public int getEmployeeId() {
		return this.employeeId;
	}
	public EmployeeByIdQuery setemployeeId(final int employeeId) {
		this.employeeId = employeeId;
		return this;
	}

	@Autowired
	private EmployeeRepository employeeRepository;
}
