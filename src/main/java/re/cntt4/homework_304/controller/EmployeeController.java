package re.cntt4.homework_304.controller;

import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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
    public String listEmployees(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "name") String sortField,
            @RequestParam(defaultValue = "asc") String sortDir,
            @RequestParam(defaultValue = "") String keyword,
            Model model) {

        Sort sort = sortDir.equals("asc") ? Sort.by(sortField).ascending()
                : Sort.by(sortField).descending();

        Pageable pageable = PageRequest.of(page, size, sort);

        Page<Employee> employeePage;
        if (keyword.isEmpty()) {
            employeePage = employeeRepository.findAll(pageable);
        } else {
            employeePage = employeeRepository.findByNameContainingIgnoreCase(keyword, pageable);
        }

        model.addAttribute("employees", employeePage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", employeePage.getTotalPages());
        model.addAttribute("totalItems", employeePage.getTotalElements());
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");
        model.addAttribute("keyword", keyword);
        model.addAttribute("departments", departmentRepository.findAll());

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
    @GetMapping("/employees/filter")
    public String filterEmployees(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Long departmentId,
            @RequestParam(required = false) Integer minAge,
            @RequestParam(required = false) Integer maxAge,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            Model model) {

        Pageable pageable = PageRequest.of(page, size);
        Page<Employee> employeePage = employeeRepository.searchEmployees(name, departmentId, minAge, maxAge, pageable);

        model.addAttribute("employees", employeePage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", employeePage.getTotalPages());
        model.addAttribute("departments", departmentRepository.findAll());

        return "employees-list";
    }

}
