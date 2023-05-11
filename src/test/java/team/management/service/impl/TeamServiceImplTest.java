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
import team.management.model.Project;
import team.management.model.Team;
import team.management.model.enam.Level;
import team.management.model.enam.Role;
import team.management.model.enam.Status;
import team.management.repository.EmployeeRepository;
import team.management.repository.ProjectRepository;
import team.management.repository.TeamRepository;
import team.management.service.TeamService;

class TeamServiceImplTest {
    private TeamRepository teamRepository;
    private ProjectRepository projectRepository;
    private EmployeeRepository employeeRepository;
    private TeamService teamService;
    private Team team;
    private List<Team> teamList;
    private Project project;
    private Employee employee;

    @BeforeEach
    void setUp() {
        teamRepository = Mockito.mock(TeamRepository.class);
        projectRepository = Mockito.mock(ProjectRepository.class);
        employeeRepository = Mockito.mock(EmployeeRepository.class);
        teamService = new TeamServiceImpl(
                teamRepository, projectRepository, employeeRepository);
        team = new Team();
        team.setProjectList(new ArrayList<>());
        team.setEmployeeList(new ArrayList<>());
        teamList = new ArrayList<>();
        teamList.add(team);
        project = new Project();
        project.setTitle("some good project");
        project.setDescription("very hard project");
        project.setStatus(Status.OPEN);
        project.setStartDate(LocalDate.of(2021,5,3));
        employee = new Employee();
        employee.setRole(Role.DESIGNER);
        employee.setLevel(Level.SENIOR);
        employee.setHireDate(LocalDate.of(2020,11,10));
    }

    @Test
    void save_Ok() {
        Mockito.when(teamRepository.save(team)).thenReturn(team);
        Team actual = teamService.save(team);
        Assertions.assertEquals(team, actual);
    }

    @Test
    void findById_Ok() {
        Mockito.when(teamRepository.findById(1L)).thenReturn(Optional.of(team));
        Optional<Team> actual = teamService.findById(1L);
        Assertions.assertTrue(actual.isPresent());
        Assertions.assertEquals(team, actual.get());
    }

    @Test
    void findAll_Ok() {
        Mockito.when(teamRepository.findAll()).thenReturn(teamList);
        List<Team> actualList = teamService.findAll();
        Assertions.assertEquals(teamList, actualList);
    }

    @Test
    void update_Ok() {
        Mockito.when(teamRepository.save(team)).thenReturn(team);
        Team actual = teamService.update(team);
        Assertions.assertEquals(team, actual);
    }

    @Test
    void delete_Ok() {
        Long id = 1L;
        Mockito.doNothing().when(teamRepository).deleteById(id);
        teamService.deleteById(id);
        Mockito.verify(teamRepository,
                Mockito.times(1)).deleteById(id);
    }

    @Test
    void addProject_Ok() {
        Long projectId = 1L;
        Long teamId = 1L;
        Mockito.when(projectRepository.findById(projectId))
                .thenReturn(Optional.of(project));
        Mockito.when(teamRepository.findById(teamId))
                .thenReturn(Optional.of(team));
        Mockito.when(projectRepository.save(project))
                .thenReturn(project);

        String result = teamService.addProject(projectId, teamId);
        String expected = "Adding the project to the team was successful. Good luck";
        Assertions.assertEquals(expected, result);

        Mockito.verify(projectRepository, Mockito.times(1)).findById(projectId);
        Mockito.verify(teamRepository, Mockito.times(1)).findById(teamId);
        Mockito.verify(projectRepository, Mockito.times(1)).save(project);

        Assertions.assertTrue(team.getProjectList().contains(project));
        Assertions.assertEquals(team, project.getTeam());
    }

    @Test
    void removeProject_Ok() {
        Long projectId = 1L;
        Long teamId = 1L;
        Mockito.when(projectRepository.findById(projectId))
                .thenReturn(Optional.of(project));
        Mockito.when(teamRepository.findById(teamId))
                .thenReturn(Optional.of(team));
        Mockito.when(projectRepository.save(project))
                .thenReturn(project);

        String result = teamService.removeProject(projectId, teamId);
        String expected = "Removing the project from the team was successful.";
        Assertions.assertEquals(expected, result);

        Mockito.verify(projectRepository, Mockito.times(1)).findById(projectId);
        Mockito.verify(teamRepository, Mockito.times(1)).findById(teamId);
        Mockito.verify(projectRepository, Mockito.times(1)).save(project);

        Assertions.assertFalse(team.getProjectList().contains(project));
        Assertions.assertNull(project.getTeam());
    }

    @Test
    void addEmployee_Ok() {
        Long employeeId = 1L;
        Long teamId = 1L;
        Mockito.when(employeeRepository.findById(employeeId))
                .thenReturn(Optional.of(employee));
        Mockito.when(teamRepository.findById(teamId))
                .thenReturn(Optional.of(team));
        Mockito.when(employeeRepository.save(employee))
                .thenReturn(employee);

        String result = teamService.addEmployee(employeeId, teamId);
        String expected = "Adding the employee to the team was successful.";
        Assertions.assertEquals(expected, result);

        Mockito.verify(employeeRepository, Mockito.times(1)).findById(employeeId);
        Mockito.verify(teamRepository, Mockito.times(1)).findById(teamId);
        Mockito.verify(employeeRepository, Mockito.times(1)).save(employee);

        Assertions.assertTrue(team.getEmployeeList().contains(employee));
        Assertions.assertEquals(team, employee.getTeam());
    }

    @Test
    void removeEmployee_Ok() {
        Long employeeId = 1L;
        Long teamId = 1L;
        Mockito.when(employeeRepository.findById(employeeId))
                .thenReturn(Optional.of(employee));
        Mockito.when(teamRepository.findById(teamId))
                .thenReturn(Optional.of(team));
        Mockito.when(employeeRepository.save(employee))
                .thenReturn(employee);

        String result = teamService.removeEmployee(employeeId, teamId);
        String expected = "Removing the employee from the team was successful.";
        Assertions.assertEquals(expected, result);

        Mockito.verify(employeeRepository, Mockito.times(1)).findById(employeeId);
        Mockito.verify(teamRepository, Mockito.times(1)).findById(teamId);
        Mockito.verify(employeeRepository, Mockito.times(1)).save(employee);

        Assertions.assertFalse(team.getEmployeeList().contains(employee));
        Assertions.assertNull(employee.getTeam());
    }
}
