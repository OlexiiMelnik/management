package team.management.model.dto;

import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;
import team.management.model.Team;
import team.management.model.enam.Level;
import team.management.model.enam.Role;

@Getter
@Setter
public class EmployeeRequestDto {
    private Level level;
    private Role role;
    private LocalDate hireDate;
    private LocalDate terminationDate;
    private Team team;
}
