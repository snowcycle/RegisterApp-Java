package edu.uark.registerapp.commands.activeUsers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import edu.uark.registerapp.commands.VoidCommandInterface;
import edu.uark.registerapp.models.entities.ActiveUserEntity;
import edu.uark.registerapp.models.repositories.ActiveUserRepository;

public class ActiveUserDeleteCommand implements VoidCommandInterface {
	
	@Override
	@Transactional
	public void execute() {
		final Optional<ActiveUserEntity> activeUserEntity =
			this.activeUserRepository.findBySessionKey(this.sessionKey);

		if (activeUserEntity.isPresent()) {
			this.activeUserRepository.delete(activeUserEntity.get());
		}
	}


	private String sessionKey;

	ActiveUserDeleteCommand(String sessionKey)
	{
		this.sessionKey = sessionKey;
	}

	public String getSessionKey()
	{
		return this.sessionKey;
	}

	public ActiveUserDeleteCommand setSessionKey(String sessionKey) {
		this.sessionKey = sessionKey;
		return this;
	}

	// Properties
	@Autowired
	private ActiveUserRepository activeUserRepository;

	
}
