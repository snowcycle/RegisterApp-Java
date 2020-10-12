@Service
public class EmployeeUpdateCommand implements ResultCommandInterface<Employee> {

	private Employee employee; 
	private EmployeeRepository employeeRepository; 
	private UUID employeeId;
	
	public Employee validate() {
		this.validateProperties();
		
		this.updateEntity();

		return this.employee;
	}

	// Helper methods
	private void validateProperties() {
		if (StringUtils.isBlank(this.employee.getFirstName())) {
			throw new Exception("firstNameIsEmpty");
		}
		if (StringUtils.isBlank(this.employee.getLastName())) {
			throw new Exception("lastNameIsEmpty");
		}
		if (EmployeeClassification.map(this.employee.getClassification()) == EmployeeClassification.NOT_DEFINED) {
			throw new Exception("classification");
		}
	}

	private void updateEntity() {
		final EmployeeEntity employeeEntity =
			this.employeeRepository.findById(this.employeeId);

		if (employeeEntity) {
			throw new Exception("employeeDoesNotExist"); 
		}

		this.employee = employeeEntity.get()
			.synchronize(this.employee); 

		this.employeeRepository.save(employeeEntity.get()); 
	}

	public UUID getEmployeeId() {
		return this.employeeId;
	}
	public EmployeeUpdateCommand setEmployeeId(final UUID idSet) {
		this.employeeId = idSet;
		return this.employeeId;
	}

	public Employee getEmployee() {
		return this.employee;
	}
	
	public EmployeeUpdate setEmployee(final Employee employee) {
		this.employee = employee;
		return this;
	}
}