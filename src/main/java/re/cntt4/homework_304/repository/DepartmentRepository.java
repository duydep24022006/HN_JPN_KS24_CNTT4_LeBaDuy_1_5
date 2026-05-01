package re.cntt4.homework_304.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import re.cntt4.homework_304.entity.Department;

public interface DepartmentRepository extends JpaRepository<Department, Long> {
}
