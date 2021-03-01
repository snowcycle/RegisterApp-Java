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
public class ActiveUserDeleteCommand{
    @Override
    public ActiveUserEntity execute(){
        final Optional<ActiveUserEntity> activeUserEntity=
            this.activeUserRepository.findBySessionKey(this.sessionKey);

        if(!activeUserEntity.isPresent()){
            throw new UauthorizedException();
        }

        return activeUserEntity.get();
    }

    private String sessionKey;

    public String getSessionKey(){
        return this.sessionKey;
    }
    
    @Transactional
    public ActiveUserDeleteCommand(){

    }

    @Autowired
    private ActiveUserRepository activeUserRepository;
}
