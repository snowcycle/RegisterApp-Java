package edu.uark.registerapp.commands.employees;

import org.apache.commons.lang3.StringUtils;
import edu.uark.registerapp.models.repositories.EmployeeRepository;
import edu.uark.registerapp.commands.exceptions.NotFoundException;

public class ActiveEmployeeExistsQuery implements EmployeeRepository{
    public boolean existsByIsActive(boolean isActive){
        return isActive;
    }
}
