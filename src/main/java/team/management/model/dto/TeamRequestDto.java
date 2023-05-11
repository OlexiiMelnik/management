package team.management.model.dto;

import java.util.List;
import lombok.Getter;
import lombok.Setter;
import team.management.model.Employee;
import team.management.model.Project;

@Getter
@Setter
public class TeamRequestDto {
    private List<Project> projectList;
    private List<Employee> employeeList;
}
