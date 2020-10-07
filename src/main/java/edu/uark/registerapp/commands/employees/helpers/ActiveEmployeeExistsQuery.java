package edu.uark.registerapp.commands.employees.helpers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.uark.registerapp.commands.VoidCommandInterface;
import edu.uark.registerapp.commands.exceptions.NotFoundException;
import edu.uark.registerapp.models.repositories.EmployeeRepository;


@Service
public class ActiveEmployeeExistsQuery implements VoidCommandInterface {
    @Override
    public void execute() {

        boolean check = this.employeeRepository.existsByIsActive(true);
        if (!check) {
            throw new NotFoundException("Employee");
        }
    }

    @Autowired
    private EmployeeRepository employeeRepository;

}
