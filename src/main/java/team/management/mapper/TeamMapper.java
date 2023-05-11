package team.management.mapper;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;
import team.management.model.Employee;
import team.management.model.Project;
import team.management.model.Team;
import team.management.model.dto.TeamRequestDto;
import team.management.model.dto.TeamResponseDto;

@Component
public class TeamMapper {
    public TeamResponseDto toTeamResponseDto(Team team) {
        TeamResponseDto teamResponseDto = new TeamResponseDto();
        teamResponseDto.setId(team.getId());
        teamResponseDto.setProjectId(mapProjectsToIds(team.getProjectList()));
        teamResponseDto.setEmployeeId(mapEmployeesToIds(team.getEmployeeList()));
        return teamResponseDto;
    }

    public Team toModelTeam(TeamRequestDto requestDto) {
        Team team = new Team();
        team.setProjectList(requestDto.getProjectList());
        team.setEmployeeList(requestDto.getEmployeeList());
        return team;
    }

    private List<Long> mapProjectsToIds(List<Project> projects) {
        if (projects == null) {
            return Collections.emptyList();
        }
        return projects.stream()
                .map(Project::getId)
                .collect(Collectors.toList());
    }

    private List<Long> mapEmployeesToIds(List<Employee> employees) {
        if (employees == null) {
            return Collections.emptyList();
        }
        return employees.stream()
                .map(Employee::getId)
                .collect(Collectors.toList());
    }
}
