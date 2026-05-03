package re.cntt4.homework_304.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import re.cntt4.homework_304.entity.Department;
import re.cntt4.homework_304.entity.Employee;
import re.cntt4.homework_304.repository.DepartmentRepository;
import re.cntt4.homework_304.repository.EmployeeRepository;

import java.util.List;

@Service
public class DepartmentService {

    private final DepartmentRepository departmentRepository;
    private final EmployeeRepository employeeRepository;

    public DepartmentService(DepartmentRepository departmentRepository,
                             EmployeeRepository employeeRepository) {
        this.departmentRepository = departmentRepository;
        this.employeeRepository = employeeRepository;
    }

    @Transactional
    public void deleteDepartment(Long deptId) {
        Department dept = departmentRepository.findById(deptId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy phòng ban"));

        List<Employee> employees = employeeRepository.findByDepartment_Id(deptId);

        for (Employee emp : employees) {
            emp.setDepartment(null);
            employeeRepository.save(emp);
        }

        departmentRepository.delete(dept);
    }
}
