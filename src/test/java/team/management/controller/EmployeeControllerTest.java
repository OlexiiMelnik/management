package team.management.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import team.management.mapper.EmployeeMapper;
import team.management.model.Employee;
import team.management.model.Team;
import team.management.model.dto.EmployeeRequestDto;
import team.management.model.dto.EmployeeResponseDto;
import team.management.model.enam.Level;
import team.management.model.enam.Role;
import team.management.service.EmployeeService;

class EmployeeControllerTest {
    private EmployeeController employeeController;
    private EmployeeService employeeService;
    private EmployeeMapper employeeMapper;
    private Employee employee;
    private EmployeeResponseDto responseDtoE;
    private EmployeeRequestDto requestDtoE;
    private List<Employee> employeeList;

    @BeforeEach
    void setUp() {
        employeeService = Mockito.mock(EmployeeService.class);
        employeeMapper = Mockito.mock(EmployeeMapper.class);
        employeeController = new EmployeeController(employeeService, employeeMapper);

        employee = new Employee();
        employee.setId(1L);
        employee.setRole(Role.DESIGNER);
        employee.setLevel(Level.SENIOR);
        employee.setHireDate(LocalDate.of(2020, 11, 10));

        employeeList = new ArrayList<>();
        employeeList.add(employee);

        requestDtoE = new EmployeeRequestDto();
        requestDtoE.setRole(employee.getRole());
        requestDtoE.setLevel(employee.getLevel());
        requestDtoE.setHireDate(employee.getHireDate());

        responseDtoE = new EmployeeResponseDto();
        responseDtoE.setId(employee.getId());
        responseDtoE.setRole(employee.getRole());
        responseDtoE.setLevel(employee.getLevel());
        responseDtoE.setHireDate(employee.getHireDate());
    }

    @Test
    void create_Ok() {
        Mockito.when(employeeMapper.toModelEmployee(requestDtoE)).thenReturn(employee);
        Mockito.when(employeeService.save(employee)).thenReturn(employee);
        Mockito.when(employeeMapper.toEmployeeResponseDto(employee)).thenReturn(responseDtoE);

        EmployeeResponseDto actualResponse = employeeController.create(requestDtoE);
        Assertions.assertEquals(responseDtoE, actualResponse);
        Mockito.verify(employeeService, Mockito.times(1)).save(employee);
    }

    @Test
    void findById_Ok() {
        Long id = 1L;
        Mockito.when(employeeService.findById(id)).thenReturn(Optional.of(employee));
        Mockito.when(employeeMapper.toEmployeeResponseDto(employee)).thenReturn(responseDtoE);

        EmployeeResponseDto actualResponse = employeeController.findById(id);

        Assertions.assertEquals(responseDtoE, actualResponse);
        Mockito.verify(employeeService, Mockito.times(1)).findById(id);
    }

    @Test
    void findAll_Ok() {
        Mockito.when(employeeService.findAll()).thenReturn(employeeList);
        Mockito.when(employeeMapper.toEmployeeResponseDto(employee)).thenReturn(responseDtoE);
        List<EmployeeResponseDto> expected = List.of(responseDtoE);
        List<EmployeeResponseDto> actualResponse = employeeController.findAll();

        Assertions.assertEquals(expected, actualResponse);
        Mockito.verify(employeeService, Mockito.times(1)).findAll();
    }

    @Test
    void update_Ok() {
        Long id = 1L;
        Mockito.when(employeeMapper.toModelEmployee(requestDtoE)).thenReturn(employee);
        Mockito.when(employeeService.update(employee)).thenReturn(employee);
        Mockito.when(employeeMapper.toEmployeeResponseDto(employee)).thenReturn(responseDtoE);

        EmployeeResponseDto actualResponse = employeeController.update(id, requestDtoE);
        Assertions.assertEquals(responseDtoE, actualResponse);
        Mockito.verify(employeeService, Mockito.times(1)).update(employee);
    }

    @Test
    void delete_Ok() {
        Long id = 1L;
        Mockito.when(employeeService.findById(id)).thenReturn(Optional.of(employee));
        Mockito.doNothing().when(employeeService).deleteById(id);
        employeeController.delete(id);
        Mockito.verify(employeeService, Mockito.times(1)).findById(id);
        Mockito.verify(employeeService, Mockito.times(1)).deleteById(id);
    }

    @Test
    void delete_EmployeeWithTeam_ThrowsException() {
        Long id = 1L;
        employee.setTeam(new Team());
        Mockito.when(employeeService.findById(id)).thenReturn(Optional.of(employee));
        ResponseStatusException exception = Assertions.assertThrows(ResponseStatusException.class, () -> {
            employeeController.delete(id);
        });

        String expectedMessage = "Employee is part of a team and cannot be deleted. " +
                "First, make changes to the command";
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, exception.getStatusCode());
        Assertions.assertEquals(expectedMessage, exception.getReason());
    }
}
