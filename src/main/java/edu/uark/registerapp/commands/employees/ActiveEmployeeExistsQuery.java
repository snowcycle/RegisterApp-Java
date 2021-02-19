package edu.uark.registerapp.commands.employees;

import org.apache.commons.lang3.StringUtils;
import edu.uark.registerapp.models.repositories.EmployeeRepository;
import edu.uark.registerapp.commands.exceptions.NotFoundException;

@Service
public class ActiveEmployeeExistsQuery implements EmployeeRepository{
    @Override
    public void checkIfActive(){
        boolean isActive = existsByIsActive(isActive);
        if(!isActive){
            throw new NotFoundException("Employee");
        }
    }

    @Autowire
    private EmployeeRepository employeeRepository;
}
