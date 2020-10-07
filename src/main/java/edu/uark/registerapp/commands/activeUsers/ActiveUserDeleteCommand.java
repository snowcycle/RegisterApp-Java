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
		this.validateProperties();


		final Optional<ActiveUserEntity> activeUserEntity =
			this.activeUserRepository.findBySessionKey(this.sessionKey);
		if (!activeUserEntity.isPresent()) { // No record with the associated record ID exists in the database.
			throw new NotFoundException("Active User");
		}

		this.activeUserRepository.delete(activeUserEntity.get());
	}


    private void validateProperties() {
		if (StringUtils.isBlank(this.firstName)) {
			throw new UnprocessableEntityException("first name");
        }
        if (StringUtils.isBlank(this.lastName)) {
			throw new UnprocessableEntityException("last name");
        }
	}


    // Properties
    private String sessionKey;
    private String firstName;
    private String lastName;


	public String getSessionKey() {
		return this.sessionKey;
	}

	public ActiveUserDeleteCommand setSessionKey(final String sessionKey) {
		this.sessionKey = sessionKey;
		return this;
    }
    
    public String getFirstName() {
		return this.firstName;
	}

	public ActiveUserDeleteCommand setFirstName(final String firstName) {
		this.firstName = firstName;
		return this;
    }
    
    public String getLastName() {
		return this.lastName;
	}

	public ActiveUserDeleteCommand setLastName(final String lastName) {
		this.lastName = lastName;
		return this;
	}

	@Autowired
	private ActiveUserRepository activeUserRepository;
}
