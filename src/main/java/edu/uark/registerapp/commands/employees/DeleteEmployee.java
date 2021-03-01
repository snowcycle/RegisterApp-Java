// This will delete an employee by their employee ID

package edu.uark.registerapp.commands.employees;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.uark.registerapp.commands.VoidCommandInterface;
import edu.uark.registerapp.commands.exceptions.NotFoundException;
import edu.uark.registerapp.models.entities.EmployeeEntity;
import edu.uark.registerapp.models.repositories.EmployeeRepository;

@Service
public class DeleteEmployee implements VoidCommandInterface {
	@Transactional
	@Override
	public void execute() {
		final Optional<EmployeeEntity> employeeEntity =
			this.employeeRepository.findById(this.employeeId); // Find the employee first
		if (!employeeEntity.isPresent()) { // No record with the associated record ID exists in the database.
			throw new NotFoundException("Employee");
		}

		this.employeeRepository.delete(employeeEntity.get()); // Delete the employee if found
	}

	// Getters and Setters
	private UUID employeeId;
	public UUID getEmployeeId() {
		return this.employeeId;
	}
	public DeleteEmployee setEmployeeId(final UUID employeeId) {
		this.employeeId = employeeId;
		return this;
	}
	
	@Autowired
	private EmployeeRepository employeeRepository;
}
