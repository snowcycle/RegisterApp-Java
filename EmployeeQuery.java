import java.util.UUID;

public class EmployeeQuery {
	private UUID employeeID; 
	
	public Employee returnEmployee() {
		@Autowired
		EmployeeRepository idFinder; 
		EmployeeEntity findID = idFinder.findById(this.employeeID);
		Employee employee = new Employee(findID.get()); ;
		
		if (findID){
			return employee;
		}
		else {
			throw new Exception("NotFoundException"); 
		}
	}
	 
	public UUID getEmployeeID() {
		return this.employeeID; 
	}
	
	public setEmployeeID(final UUID idSet) {
		employeeID = idSet; 
		return employeeID; 
	}
}