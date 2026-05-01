package re.cntt4.homework_304.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import re.cntt4.homework_304.entity.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
}
