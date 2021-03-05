package edu.uark.registerapp.commands.activeUsers;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.uark.registerapp.commands.VoidCommandInterface;
import edu.uark.registerapp.models.entities.ActiveUserEntity;
import edu.uark.registerapp.models.repositories.ActiveUserRepository;

@Service
public class ActiveUserDeleteCommand implements VoidCommandInterface {

	private String sessionKey;
	private ActiveUserRepository activeUserRepository;

	public ActiveUserDeleteCommand(String sessionKey, ActiveUserRepository activeUserRepository)
	{
		this.sessionKey = sessionKey;
		this.activeUserRepository = activeUserRepository;
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

		if (activeUserEntity.isPresent()) {
			this.activeUserRepository.delete(activeUserEntity.get());
		}
	}

	public String getSessionKey()
	{
		return this.sessionKey;
	}
}
