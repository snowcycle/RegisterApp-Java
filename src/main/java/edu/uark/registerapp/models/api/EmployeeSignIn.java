package edu.uark.registerapp.commands.employees;

import java.util.Arrays;
import java.util.Optional;

import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.uark.registerapp.commands.ResultCommandInterface;
import edu.uark.registerapp.commands.employees.helpers.EmployeeHelper;
import edu.uark.registerapp.commands.exceptions.UnauthorizedException;
import edu.uark.registerapp.commands.exceptions.UnprocessableEntityException;
import edu.uark.registerapp.models.api.Employee;
import edu.uark.registerapp.models.api.EmployeeSignIn;
import edu.uark.registerapp.models.entities.ActiveUserEntity;
import edu.uark.registerapp.models.entities.EmployeeEntity;
import edu.uark.registerapp.models.repositories.ActiveUserRepository;
import edu.uark.registerapp.models.repositories.EmployeeRepository;

@Service
public class EmployeeSignInCommand implements ResultCommandInterface<Employee> {
	@Override
	public Employee execute() {
		this.validateProperties();

		return new Employee(this.SignInEmployee());
	}

	// Helper methods
	private void validateProperties() {
		if (StringUtils.isBlank(this.employeeSignIn.getEmployeeId())) {
			throw new UnprocessableEntityException("employee ID");
		}
		try {
			Integer.parseInt(this.employeeSignIn.getEmployeeId());
		} catch (final NumberFormatException e) {
			throw new UnprocessableEntityException("employee ID");
		}
		if (StringUtils.isBlank(this.employeeSignIn.getPassword())) {
			throw new UnprocessableEntityException("password");
		}
	}

	@Transactional
	private EmployeeEntity SignInEmployee() {
		final Optional<EmployeeEntity> employeeEntity =
			this.employeeRepository.findByEmployeeId(
				Integer.parseInt(this.employeeSignIn.getEmployeeId()));

		if (!employeeEntity.isPresent()
			|| !Arrays.equals(
				employeeEntity.get().getPassword(),
				EmployeeHelper.hashPassword(this.employeeSignIn.getPassword()))
		) {

			throw new UnauthorizedException();
		}

		final Optional<ActiveUserEntity> activeUserEntity =
			this.activeUserRepository
				.findByEmployeeId(employeeEntity.get().getId());

		if (!activeUserEntity.isPresent()) {
			this.activeUserRepository.save(
					(new ActiveUserEntity())
						.setSessionKey(this.sessionId)
						.setEmployeeId(employeeEntity.get().getId())
						.setClassification(
							employeeEntity.get().getClassification())
						.setName(
							employeeEntity.get().getFirstName()
								.concat(" ")
								.concat(employeeEntity.get().getLastName())));
		} else {
			this.activeUserRepository.save(
				activeUserEntity.get().setSessionKey(this.sessionId));
		}

		return employeeEntity.get();
	}

	// Properties
	private EmployeeSignIn employeeSignIn;
	public EmployeeSignIn getEmployeeSignIn() {
		return this.employeeSignIn;
	}
	public EmployeeSignInCommand setEmployeeSignIn(final EmployeeSignIn employeeSignIn) {
		this.employeeSignIn = employeeSignIn;
		return this;
	}

	private String sessionId;
	public String getSessionId() {
		return this.sessionId;
	}
	public EmployeeSignInCommand setSessionId(final String sessionId) {
		this.sessionId = sessionId;
		return this;
	}

	@Autowired
	private EmployeeRepository employeeRepository;
	@Autowired
	private ActiveUserRepository activeUserRepository;
}
