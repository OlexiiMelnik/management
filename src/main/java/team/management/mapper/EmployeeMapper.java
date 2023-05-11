package team.management.mapper;

import org.springframework.stereotype.Component;
import team.management.model.Employee;
import team.management.model.dto.EmployeeRequestDto;
import team.management.model.dto.EmployeeResponseDto;

@Component
public class EmployeeMapper {
    public EmployeeResponseDto toEmployeeResponseDto(Employee employee) {
        EmployeeResponseDto employeeResponseDto = new EmployeeResponseDto();
        employeeResponseDto.setId(employee.getId());
        employeeResponseDto.setLevel(employee.getLevel());
        employeeResponseDto.setRole(employee.getRole());
        employeeResponseDto.setHireDate(employee.getHireDate());
        employeeResponseDto.setTerminationDate(employee.getTerminationDate());
        if(employee.getTeam() != null) {
            employeeResponseDto.setTeamId(employee.getTeam().getId());
        } else  {
            employeeResponseDto.setTeamId(null);
        }
        return employeeResponseDto;
    }

    public Employee toModelEmployee(EmployeeRequestDto employeeRequestDto) {
        Employee employee = new Employee();
        employee.setLevel(employeeRequestDto.getLevel());
        employee.setRole(employeeRequestDto.getRole());
        employee.setHireDate(employeeRequestDto.getHireDate());
        employee.setTerminationDate(employeeRequestDto.getTerminationDate());
        employee.setTeam(employeeRequestDto.getTeam());
        return employee;
    }
}
