package edu.uark.registerapp.models.api;


import org.apache.commons.lang3.StringUtils;

import edu.uark.registerapp.commands.employees.helpers.EmployeeHelper;
import edu.uark.registerapp.models.entities.EmployeeEntity;

public class EmployeeSignIn extends ApiResponse {

        private String employeeId;
        private String password;

        public String getEmployeeId() { 
                return this.employeeId; 
        }

        public void setEmployeeId(final String employeeId) { 
                this.employeeId = employeeId; 
        }

        public String getPassword() { 
                return this.password; 
        }

        public void setPassword(final String password) { 
                this.password = password; 
        }

        public EmployeeSignIn() {
                super();

                this.employeeId = StringUtils.EMPTY;
                this.password = StringUtils.EMPTY;
        }

        public EmployeeSignIn(final EmployeeEntity employeeEntity) {
                super(false);

                this.password = StringUtils.EMPTY;
                this.employeeId = EmployeeHelper.padEmployeeId(employeeEntity.getEmployeeId());

        }

}
    

