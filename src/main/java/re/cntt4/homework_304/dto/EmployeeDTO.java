// src/main/java/re/cntt4/homework_304/dto/EmployeeDTO.java
package re.cntt4.homework_304.dto;

import jakarta.validation.constraints.*;
public class EmployeeDTO {

    @NotBlank(message = "Tên không được để trống")
    private String name;

    @Min(value = 18, message = "Tuổi phải >= 18")
    @Max(value = 65, message = "Tuổi phải <= 65")
    private int age;

    @NotBlank(message = "Status không được để trống")
    private String status;

    @NotNull(message = "Phòng ban bắt buộc chọn")
    private Long departmentId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Long departmentId) {
        this.departmentId = departmentId;
    }
}
