package edu.uark.registerapp.commands.employees;

import org.apache.commons.lang3.StringUtils;
import edu.uark.registerapp.models.repositories.EmployeeRepository;
import edu.uark.registerapp.commands.exceptions.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ActiveEmployeeExistsQuery implements EmployeeRepository{
    @Override
    public void ActiveEmployeeExistsQuery(){
        if(!this.employeeRepository.existsByIsActive(true)){
            throw new NotFoundException("Employee");
        }
    }

    @Autowire
    private EmployeeRepository employeeRepository;
}
