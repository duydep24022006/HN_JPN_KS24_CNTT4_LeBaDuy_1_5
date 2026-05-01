package re.cntt4.homework_304.controller;

import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import re.cntt4.homework_304.dto.EmployeeDTO;
import re.cntt4.homework_304.entity.Employee;
import re.cntt4.homework_304.repository.DepartmentRepository;
import re.cntt4.homework_304.repository.EmployeeRepository;
import re.cntt4.homework_304.service.FileStorageService;

@Controller
public class EmployeeController {

    private final EmployeeRepository employeeRepository;
    private final DepartmentRepository departmentRepository;
    private final FileStorageService fileStorageService;

    public EmployeeController(EmployeeRepository employeeRepository,
                              DepartmentRepository departmentRepository,
                              FileStorageService fileStorageService) {
        this.employeeRepository = employeeRepository;
        this.departmentRepository = departmentRepository;
        this.fileStorageService = fileStorageService;
    }

    @GetMapping("/employees")
    public String listEmployees(Model model) {
        model.addAttribute("employees", employeeRepository.findAll());
        return "employees-list";
    }

    @GetMapping("/employees/new")
    public String showCreateForm(Model model) {
        model.addAttribute("employee", new EmployeeDTO());
        model.addAttribute("departments", departmentRepository.findAll());
        return "employee-form";
    }

    @PostMapping("/employees/save")
    public String saveEmployee(@Valid @ModelAttribute("employee") EmployeeDTO dto,
                               BindingResult result,
                               @RequestParam("file") MultipartFile file,
                               Model model) {
        if (result.hasErrors()) {
            model.addAttribute("departments", departmentRepository.findAll());
            return "employee-form";
        }

        Employee emp = new Employee();
        emp.setName(dto.getName());
        emp.setAge(dto.getAge());
        emp.setStatus(dto.getStatus());
        emp.setDepartment(departmentRepository.findById(dto.getDepartmentId()).orElse(null));

        if (!file.isEmpty()) {
            String fileName = fileStorageService.storeFile(file);
            emp.setAvatar(fileName);
        }

        employeeRepository.save(emp);
        return "redirect:/employees";
    }
}
