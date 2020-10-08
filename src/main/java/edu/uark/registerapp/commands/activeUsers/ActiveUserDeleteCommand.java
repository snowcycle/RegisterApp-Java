package edu.uark.registerapp.commands.activeUsers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;

import edu.uark.registerapp.commands.VoidCommandInterface;
import edu.uark.registerapp.commands.exceptions.NotFoundException;
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
		validateProperties(activeUserEntity);
		if (!activeUserEntity.isPresent()) { // No record with the associated record ID exists in the database.
			throw new NotFoundException("Active User");
		}

		this.activeUserRepository.delete(activeUserEntity.get());
	}


    private void validateProperties(final Optional<ActiveUserEntity> activeUserEntity) {

		if (StringUtils.isBlank(activeUserEntity.get().getName())) {
			throw new UnprocessableEntityException("name");
        }
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
