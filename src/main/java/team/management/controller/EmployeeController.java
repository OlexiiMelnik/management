package team.management.controller;

import java.util.List;
import java.util.stream.Collectors;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import team.management.mapper.EmployeeMapper;
import team.management.model.Employee;
import team.management.model.dto.EmployeeRequestDto;
import team.management.model.dto.EmployeeResponseDto;
import team.management.service.EmployeeService;
import org.springframework.web.bind.annotation.ExceptionHandler;

@RestController
@RequestMapping("/employees")
@RequiredArgsConstructor
@Tag(name = "Employee Controller", description = "REST-full endpoints  related to employee")
public class EmployeeController {
    private final EmployeeService employeeService;
    private final EmployeeMapper employeeMapper;

    @PostMapping
    @Operation(summary = "post new employee to db")
    public EmployeeResponseDto create(@RequestBody EmployeeRequestDto requestDto) {
        Employee save
                = employeeService.save(employeeMapper.toModelEmployee(requestDto));
        return employeeMapper.toEmployeeResponseDto(save);
    }

    @GetMapping("/{id}")
    @Operation(summary = "get employee by id")
    public EmployeeResponseDto findById(@PathVariable Long id) {
        return employeeMapper.toEmployeeResponseDto(
                employeeService.findById(id).orElseThrow(EntityNotFoundException::new));
    }

    @GetMapping("/all")
    @Operation(summary = "get all employees")
    public List<EmployeeResponseDto> findAll() {
        return employeeService.findAll()
                .stream()
                .map(employeeMapper::toEmployeeResponseDto)
                .collect(Collectors.toList());
    }

    @PutMapping("/{id}")
    @Operation(summary = "update employee by id")
    public EmployeeResponseDto update(@PathVariable Long id,
                                      @RequestBody EmployeeRequestDto requestDto) {
        Employee employee
                = employeeMapper.toModelEmployee(requestDto);
        return employeeMapper.toEmployeeResponseDto(employeeService.update(employee));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "delete employee by id")
    public void delete(@PathVariable Long id) {
        Employee employee
                = employeeService.findById(id).orElseThrow(EntityNotFoundException::new);
        if (employee.getTeam() != null) {
            throw  new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Employee is part of a team and cannot be deleted. " +
                            "First, make changes to the command");
        }
        employeeService.deleteById(id);
    }

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<String> handleResponseStatusException(
            ResponseStatusException e) {
        return ResponseEntity.status(e.getStatusCode()).body(e.getMessage());
    }
}
