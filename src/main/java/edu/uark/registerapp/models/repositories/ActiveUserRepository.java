// We will need this for the CRUD operations to find the employee Id and the session key of the acitve user

package edu.uark.registerapp.models.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.repository.CrudRepository;

import edu.uark.registerapp.models.entities.ActiveUserEntity;

public interface ActiveUserRepository extends CrudRepository<ActiveUserEntity, UUID> {
	Optional<ActiveUserEntity> findByEmployeeId(UUID employeeId); 
	Optional<ActiveUserEntity> findBySessionKey(String sessionKey);
}