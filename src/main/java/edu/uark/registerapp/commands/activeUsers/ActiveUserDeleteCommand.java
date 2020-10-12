package edu.uark.registerapp.commands.activeUsers;

import edu.uark.registerapp.models.entities.EmployeeEntity;
import edu.uark.registerapp.models.entities.ActiveUserEntity;
import edu.uark.registerapp.commands.exceptions.NotFoundException;
import edu.uark.registerapp.models.repositories.ActiveUserRepository;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;



public class ActiveUserDeleteCommand{
	
	private String sessionKey;
	public String getSessionKey() {
		return this.sessionKey;
	}

	public boolean validateEmployee(EmployeeEntity emp) {
		if (emp.getFirstName().isEmpty()) {
			throw new NotFoundException("First name");
		}
		if (emp.getLastName().isEmpty()) {
			throw new NotFoundException("Last name");
		}
	}
	
	@Transactional
	public void execute() {
		ActiveUserEntity user = this.activeUserRepository.findBySessionKey(this.sessionKey);
		if (user.isPresent()) {
			this.activeUserRepository.delete(user.get());
		}
	}
	
	@Autowired
	private ActiveUserRepository activeUserRepository;
}
