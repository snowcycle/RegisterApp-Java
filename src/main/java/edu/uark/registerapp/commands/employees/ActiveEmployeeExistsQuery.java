package edu.uark.registerapp.commands.employees;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.uark.registerapp.commands.VoidCommandInterface;
import edu.uark.registerapp.models.repositories.EmployeeRepository;
import edu.uark.registerapp.commands.exceptions.NotFoundException;

@Service
public class ActiveEmployeeExistsQuery implements VoidCommandInterface{
	@Override
	public void execute() {
		if (!this.employeerepository.existsByIsActive(true)) {
			throw new NotFoundException("Employee");
		}
	}
	
	@Autowired
	private EmployeeRepository employeerepository;
}
