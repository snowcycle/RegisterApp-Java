package edu.uark.registerapp.models.api;


import org.apache.commons.lang3.StringUtils;

public class EmployeeSignIn {

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
                this.employeeId = StringUtils.EMPTY;
                this.password = StringUtils.EMPTY;
        }

        public EmployeeSignIn(final EmployeeSignIn employeeSignIn) {
                this.password = employeeSignIn.getPassword();
                this.employeeId = employeeSignIn.getEmployeeId();

        }

}
    

