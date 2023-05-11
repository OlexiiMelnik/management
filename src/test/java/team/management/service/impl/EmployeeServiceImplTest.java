package team.management.service.impl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import team.management.model.Employee;
import team.management.model.enam.Level;
import team.management.model.enam.Role;
import team.management.repository.EmployeeRepository;
import team.management.service.EmployeeService;

class EmployeeServiceImplTest {
    private EmployeeRepository employeeRepository;
    private EmployeeService employeeService;
    private Employee employee;
    private List<Employee> employeeList;

    @BeforeEach
    void setUp() {
        employeeRepository = Mockito.mock(EmployeeRepository.class);
        employeeService = new EmployeeServiceImpl(employeeRepository);
        employee = new Employee();
        employee.setRole(Role.DESIGNER);
        employee.setLevel(Level.SENIOR);
        employee.setHireDate(LocalDate.of(2020,11,10));
        employeeList = new ArrayList<>();
        employeeList.add(employee);
    }

    @Test
    void save_Ok() {
        Mockito.when(employeeRepository.save(employee)).thenReturn(employee);
        Employee actual = employeeService.save(employee);
        Assertions.assertEquals(employee, actual);
    }

    @Test
    void findById_Ok() {
        Mockito.when(employeeRepository.findById(1L))
                .thenReturn(Optional.of(employee));
        Optional<Employee> actual = employeeService.findById(1L);
        Assertions.assertTrue(actual.isPresent());
        Assertions.assertEquals(employee, actual.get());
    }

    @Test
    void findAll_Ok() {
        Mockito.when(employeeRepository.findAll()).thenReturn(employeeList);
        List<Employee> actual = employeeService.findAll();
        Assertions.assertEquals(employeeList, actual);
    }

    @Test
    void update_Ok() {
        Mockito.when(employeeRepository.save(employee)).thenReturn(employee);
        Employee actual = employeeService.update(employee);
        Assertions.assertEquals(employee, actual);
    }

    @Test
    void deleteById_Ok() {
        Long id = 1L;
        Mockito.doNothing().when(employeeRepository).deleteById(id);
        employeeService.deleteById(id);
        Mockito.verify(employeeRepository,
                Mockito.times(1)).deleteById(id);
    }
}