package edu.uark.registerapp.commands.activeUsers;

import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.pringframework.beans.factory.annotation.Transactional;


import edu.uark.registerapp.commands.ResultCommandInterface;
import edu.uark.registerapp.commands.exceptions.UnauthorizedException;
import edu.uark.registerapp.nodels.entities.ActiveUserEntity;
import edu.uark.registerapp.models.repositories.ActiveUserRepository;

@Service
public class ActiveUserDeleteCommand implements ResultCommandInterface<ActiveUserEntity>{
    @Override
    @Transactional
    public void execute(){
        final Optional<ActiveUserEntity> activeUserEntity=
            this.activeUserRepository.findBySessionKey(this.sessionKey);

        if(!activeUserEntity.isPresent()){
            this.activeUserRepository.delete(activeUserEntity.get());
        }
    }

    private String sessionKey;

    public String getSessionKey(){
        return this.sessionKey;
    }
    
    public ActiveUserDeleteCommand setSessionKey(final String sessionKey){
        this.sessionKey = sessionKey;
    }

    @Autowired
    private ActiveUserRepository activeUserRepository;
}
