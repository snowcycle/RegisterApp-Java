// The API that talks to DB about the entity employee and gives back the data to the user.
package edu.uark.registerapp.models.api;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;

import edu.uark.registerapp.commands.employees.helpers.EmployeeHelper;
import edu.uark.registerapp.models.entities.EmployeeEntity;

public class Employee extends ApiResponse {
    private UUID id;
    public UUID getId() {
    	return this.id;
    }

    public Employee setId(final UUID id) {
		this.id = id;
		return this;
	}

	private String employeeId;
	public String getEmployeeId() {
		return this.employeeId;
	}

	//Part of task 3
	public Employee setEmployeeId(final int employeeId) {
	 	this.employeeId = EmployeeHelper.padEmployeeId(employeeId);
	 	return this;
	 }
	public Employee setEmployeeId(final String employeeId) {
		this.employeeId = employeeId;
		return this;
	}

	private String firstName;
	public String getFirstName() {
		return this.firstName;
	}
	public Employee setFirstName(final String firstName) {
		this.firstName = firstName;
		return this;
	}

	private String lastName;
	public String getLastName() {
		return this.lastName;
	}
	public Employee setLastName(final String lastName) {
		this.lastName = lastName;
		return this;
	}

	private String password;
	public String getPassword() {
		return this.password;
	}
	public Employee setPassword(final String password) {
		this.password = password;
		return this;
	}

	private boolean isActive;
	public boolean getIsActive() {
		return this.isActive;
	}
	public Employee setIsActive(final boolean isActive) {
		this.isActive = isActive;
		return this;
	}

	private int classification;
	public int getClassification() {
		return this.classification;
	}
	public Employee setClassification(final int classification) {
		this.classification = classification;
		return this;
	}

	private UUID managerId;
	public UUID getManagerId() {
		return this.managerId;
	}
	public Employee setManagerId(final UUID managerId) {
		this.managerId = managerId;
		return this;
	}

	private String createdOn;
	public String getCreatedOn() {
		return this.createdOn;
	}
	public Employee setCreatedOn(final String createdOn) {
		this.createdOn = createdOn;
		return this;
	}
	public Employee setCreatedOn(final LocalDateTime createdOn) {
		this.createdOn =
			createdOn.format(DateTimeFormatter.ofPattern("MM/dd/yyyy"));

		return this;
	}

	private boolean isInitialEmployee;
	public boolean getIsInitialEmployee() {
		return this.isInitialEmployee;
	}
	public Employee setIsInitialEmployee(final boolean isInitialEmployee) {
		this.isInitialEmployee = isInitialEmployee;
		return this;
	}

	public Employee() {
		super();

		this.isActive = true;
		this.id = new UUID(0, 0);
		this.classification = -1;
		this.isInitialEmployee = false;
		this.managerId = new UUID(0, 0);
		this.lastName = StringUtils.EMPTY;
		this.password = StringUtils.EMPTY;
		this.firstName = StringUtils.EMPTY;
		this.employeeId = StringUtils.EMPTY;

		this.setCreatedOn(LocalDateTime.now());
	}

	public Employee(final edu.uark.registerapp.models.entities.EmployeeEntity employeeEntity) {
		super(false);

		this.isInitialEmployee = false;
		this.id = employeeEntity.getId();
		this.password = StringUtils.EMPTY;
		this.isActive = employeeEntity.getIsActive();
		this.lastName = employeeEntity.getLastName();
		this.firstName = employeeEntity.getFirstName();
		this.managerId = employeeEntity.getManagerId();
		this.classification = employeeEntity.getClassification();
		this.employeeId =
		 	EmployeeHelper.padEmployeeId(employeeEntity.getEmployeeId());

		this.setCreatedOn(employeeEntity.getCreatedOn());
	}
}