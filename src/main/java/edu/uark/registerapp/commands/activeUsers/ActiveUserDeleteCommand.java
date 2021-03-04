package edu.uark.registerapp.commands.activeUsers;

import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.uark.registerapp.commands.VoidCommandInterface;
import edu.uark.registerapp.commands.exceptions.UnprocessableEntityException;
import edu.uark.registerapp.models.entities.ActiveUserEntity;
import edu.uark.registerapp.models.repositories.ActiveUserRepository;

@Service
public class ActiveUserDeleteCommand implements VoidCommandInterface {
	
	@Transactional
	@Override
	public void execute() {
		final Optional<ActiveUserEntity> activeUserEntity =
			this.activeUserRepository.findBySessionKey(this.sessionKey);

		validateEmployeeRequestObject(activeUserEntity);

		//removes active user
		if (activeUserEntity.isPresent()) {
			this.activeUserRepository.delete(activeUserEntity.get());
		}
	}

	private void validateEmployeeRequestObject(Optional<ActiveUserEntity> activeUserEntity){
		if (StringUtils.isBlank(activeUserEntity.get().getName())) {
			throw new UnprocessableEntityException("Name");
		}
		String [] name = activeUserEntity.get().getName().split(" ", 2);
		if (StringUtils.isBlank(name[0])) {throw new UnprocessableEntityException("First Name");}
		if (StringUtils.isBlank(name[1])) {throw new UnprocessableEntityException("Last Name");}
	}

	// Properties
	private String sessionKey;
	public String getSessionKey() {
		return this.sessionKey;
	}
	public ActiveUserDeleteCommand setSessionKey(final String sessionKey) {
		this.sessionKey = sessionKey;
		return this;
	}

	@Autowired
	private ActiveUserRepository activeUserRepository;
}
