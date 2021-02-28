package edu.uark.registerapp.models.api;

import org.apache.commons.lang3.StringUtils;
public class EmployeeSignIn {
    private String employeeId;
    private String password;
    
    public EmployeeSignIn(){
        this.password = StringUtils.EMPTY;
        this.employeeId = StringUtils.EMPTY;
    }
    public String getEmployeeId() {
        return this.employeeId;
    }
    
    public EmployeeSignIn setEmployeeId(final String employeeId) {
        this.employeeId = employeeId;
        return this;
    }
    
    public String getPassword() {
        return this.password;
    }

    public EmployeeSignIn setPassword(final String password) {
        this.password = password;
        return this;
    }
}
