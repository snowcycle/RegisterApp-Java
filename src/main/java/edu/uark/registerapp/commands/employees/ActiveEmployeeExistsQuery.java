import edu.uark.registerapp.commands.exceptions.NotFoundException;
import edu.uark.registerapp.models.repositories.EmployeeRepository;

public class ActiveEmployeeExistsQuery {
	public void execute() {
		if (!this.employeeRepository.existsByIsActive(true)) {     
			throw NotFoundException("Employee");
		}
	}
}