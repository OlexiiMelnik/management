package team.management.service;

import java.util.List;
import java.util.Optional;
import team.management.model.Employee;

public interface EmployeeService {
    Employee save(Employee employee);

    Optional<Employee> findById(Long id);

    List<Employee> findAll();

    Employee update(Employee employee);

    void deleteById(Long id);
}
