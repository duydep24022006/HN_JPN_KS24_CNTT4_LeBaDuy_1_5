package re.cntt4.homework_304.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import re.cntt4.homework_304.entity.Department;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long> {
}
