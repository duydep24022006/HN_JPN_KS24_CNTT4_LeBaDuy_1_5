package re.cntt4.homework_304.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import re.cntt4.homework_304.entity.Employee;
import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    Page<Employee> findByNameContainingIgnoreCase(String name, Pageable pageable);
    List<Employee> findByDepartment_Id(Long departmentId);
    @Query("SELECT e FROM Employee e " +
            "WHERE (:name IS NULL OR LOWER(e.name) LIKE LOWER(CONCAT('%', :name, '%'))) " +
            "AND (:deptId IS NULL OR e.department.id = :deptId) " +
            "AND (:minAge IS NULL OR e.age >= :minAge) " +
            "AND (:maxAge IS NULL OR e.age <= :maxAge)")
    Page<Employee> searchEmployees(@Param("name") String name,
                                   @Param("deptId") Long deptId,
                                   @Param("minAge") Integer minAge,
                                   @Param("maxAge") Integer maxAge,
                                   Pageable pageable);
}
