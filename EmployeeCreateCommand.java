@Service
public class EmployeeCreateCommand implements ResultCommandInterface<Employee> {
	private Employee employee;
	private boolean initialEmployee;
	private EmployeeRepository employeeRepository;
	final EmployeeEntity employeeEntity;
	
	public Employee validate() {

		if (initialEmployee == true) {
			employee.setClassification(
				EmployeeClassification.GENERAL_MANAGER.getClassification());
		}


		employeeEntity =
			this.employeeRepository.save(employeeEntity(this.employee));
		this.employee.setId(employeeEntity.getId());

		this.employee.setPassword(StringUtils.EMPTY);
		this.employee.setCreatedOn(employeeEntity.getCreatedOn());
		this.employee.setEmployeeId(
				employeeEntity.getEmployeeId());

		return this.employee;
	}

	private void checkIfEmpty() {
		if (StringUtils.isBlank(this.eployee.getFirstName())) {
			throw new Exception("firstNameIsEmpty");
		}
		if (StringUtils.isBlank(this.employee.getLastName())) {
			throw new Exception("lastNameIsEmpty");
		}
		if (StringUtils.isBlank(this.employee.getPassword())) {
			throw new Exception("passwordIsEmpty");
		}

		if (isInitialEmployee == false
			&& (EmployeeClassification.map(this.employee.getClassification()) == EmployeeClassification.NOT_DEFINED)) {

			throw new Exception("classification");
		}
	}
	
	public Employee getEmployee() {
		return employee; 
	}
	
	public EmployeeCreateCommand setEmployee(final Employee employee) {
		this.employee = employee;
		return this.employee;
	}

	public boolean getInitialEmployee() {
		return this.initialEmployee;
	}
	
	public EmployeeCreateCommand setInitialEmployee(final boolean setInitial) {

		initialEmployee = setInitial;
		return initialEmployee;
	}

	public EmployeeCreate() {
		initialEmployee = false;
	}
}