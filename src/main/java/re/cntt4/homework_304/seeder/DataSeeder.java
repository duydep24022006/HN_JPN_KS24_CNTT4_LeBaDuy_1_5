package re.cntt4.homework_304.seeder;


import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import re.cntt4.homework_304.entity.Department;
import re.cntt4.homework_304.entity.Employee;
import re.cntt4.homework_304.repository.DepartmentRepository;
import re.cntt4.homework_304.repository.EmployeeRepository;

import java.util.Arrays;

@Component
public class DataSeeder implements CommandLineRunner {

    private final DepartmentRepository departmentRepository;
    private final EmployeeRepository employeeRepository;

    public DataSeeder(DepartmentRepository departmentRepository, EmployeeRepository employeeRepository) {
        this.departmentRepository = departmentRepository;
        this.employeeRepository = employeeRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if (departmentRepository.count() == 0 && employeeRepository.count() == 0) {
            Department d1 = new Department();
            d1.setName("IT");
            d1.setLocation("Hanoi");

            Department d2 = new Department();
            d2.setName("HR");
            d2.setLocation("HCMC");

            departmentRepository.saveAll(Arrays.asList(d1, d2));

            Employee e1 = new Employee();
            e1.setName("Nguyen Van A");
            e1.setAge(25);
            e1.setAvatar("a.jpg");
            e1.setStatus("Active");
            e1.setDepartment(d1);

            Employee e2 = new Employee();
            e2.setName("Tran Thi B");
            e2.setAge(30);
            e2.setAvatar("b.jpg");
            e2.setStatus("Inactive");
            e2.setDepartment(d2);

            employeeRepository.saveAll(Arrays.asList(e1, e2));
        }
    }
}

