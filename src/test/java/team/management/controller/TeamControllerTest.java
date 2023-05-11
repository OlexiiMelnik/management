package team.management.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import team.management.mapper.TeamMapper;
import team.management.model.Employee;
import team.management.model.Project;
import team.management.model.Team;
import team.management.model.dto.TeamRequestDto;
import team.management.model.dto.TeamResponseDto;
import team.management.service.TeamService;
import static org.junit.jupiter.api.Assertions.assertThrows;

class TeamControllerTest {
    private TeamController teamController;
    private TeamService teamService;
    private TeamMapper teamMapper;
    private Team team;
    private TeamRequestDto teamRequestDto;
    private TeamResponseDto teamResponseDto;
    private List<Team> teamList;

    @BeforeEach
    void setUp() {
        teamService = Mockito.mock(TeamService.class);
        teamMapper = Mockito.mock(TeamMapper.class);
        teamController = new TeamController(teamService, teamMapper);

        team = new Team();
        team.setProjectList(new ArrayList<>());
        team.setEmployeeList(new ArrayList<>());
        team.setId(1L);

        teamRequestDto = new TeamRequestDto();
        teamRequestDto.setEmployeeList(team.getEmployeeList());
        teamRequestDto.setProjectList(team.getProjectList());

        teamResponseDto = new TeamResponseDto();
        teamResponseDto.setId(team.getId());
        teamResponseDto.setEmployeeId(new ArrayList<>());
        teamResponseDto.setProjectId(new ArrayList<>());
        teamList = new ArrayList<>();
        teamList.add(team);
    }

    @Test
    void create_Ok() {
        Mockito.when(teamMapper.toModelTeam(teamRequestDto)).thenReturn(team);
        Mockito.when(teamService.save(team)).thenReturn(team);
        Mockito.when(teamMapper.toTeamResponseDto(team)).thenReturn(teamResponseDto);

        TeamResponseDto teamResponseDtoActual = teamController.create(teamRequestDto);

        Assertions.assertEquals(teamResponseDto, teamResponseDtoActual);
        Mockito.verify(teamService, Mockito.times(1)).save(team);
        Mockito.verify(teamMapper, Mockito.times(1)).toTeamResponseDto(team);
    }

    @Test
    void findById_Ok() {
        Long id = 1L;
        Mockito.when(teamService.findById(id)).thenReturn(Optional.of(team));
        Mockito.when(teamMapper.toTeamResponseDto(team)).thenReturn(teamResponseDto);

        TeamResponseDto teamResponseDtoActual = teamController.findById(id);
        Assertions.assertEquals(teamResponseDto, teamResponseDtoActual);
    }

    @Test
    void findAll() {
        Mockito.when(teamService.findAll()).thenReturn(teamList);
        Mockito.when(teamMapper.toTeamResponseDto(team)).thenReturn(teamResponseDto);

        List<TeamResponseDto> expectedList = List.of(teamResponseDto);
        List<TeamResponseDto> actualList = teamController.findAll();
        Assertions.assertEquals(expectedList, actualList);
    }

    @Test
    void update_Ok() {
        Long id = 1L;
        Mockito.when(teamMapper.toModelTeam(teamRequestDto)).thenReturn(team);
        Mockito.when(teamService.update(team)).thenReturn(team);
        Mockito.when(teamMapper.toTeamResponseDto(team)).thenReturn(teamResponseDto);

        TeamResponseDto teamResponseDtoActual = teamController.update(id, teamRequestDto);
        Assertions.assertEquals(teamResponseDto, teamResponseDtoActual);
    }

    @Test
    void delete_Ok() {
        Long id = 1L;
        Mockito.when(teamService.findById(id)).thenReturn(Optional.of(team));
        Mockito.doNothing().when(teamService).deleteById(id);
        teamController.delete(id);
        Mockito.verify(teamService, Mockito.times(1)).findById(id);
        Mockito.verify(teamService, Mockito.times(1)).deleteById(id);
    }

    @Test
    void delete_WithDependentProjects_ThrowsBadRequestException() {
        Long id = 1L;

        team.getProjectList().add(new Project());

        Mockito.when(teamService.findById(id)).thenReturn(Optional.of(team));

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            teamController.delete(id);
        });

        Assertions.assertEquals(HttpStatus.BAD_REQUEST, exception.getStatusCode());
        Assertions.assertEquals("The team has dependent projects. First," +
                " separate the project from the team.", exception.getReason());
    }

    @Test
    void delete_WithEmployees_ThrowsBadRequestException() {
        Long id = 1L;

        team.getEmployeeList().add(new Employee());

        Mockito.when(teamService.findById(id)).thenReturn(Optional.of(team));

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            teamController.delete(id);
        });

        Assertions.assertEquals(HttpStatus.BAD_REQUEST, exception.getStatusCode());
        Assertions.assertEquals("Team contains employees. " +
                        "Disband employees first.",
                exception.getReason());
    }

    @Test
    void addProject_Ok() {
        Long teamId = 1L;
        Long projectId = 2L;
        String expectedMessage = "Adding the project to the team was successful. Good luck";
        Mockito.when(teamService.addProject(projectId, teamId)).thenReturn(expectedMessage);
        String actualMessage = teamController.addProject(teamId, projectId);
        Assertions.assertEquals(expectedMessage, actualMessage);
        Mockito.verify(teamService, Mockito.times(1)).addProject(projectId, teamId);
    }

    @Test
    void removeProject_Ok() {
        Long teamId = 1L;
        Long projectId = 2L;
        String expectedMessage = "Removing the project from the team was successful.";
        Mockito.when(teamService.removeProject(projectId, teamId)).thenReturn(expectedMessage);
        String actualMessage = teamController.removeProject(teamId, projectId);
        Assertions.assertEquals(expectedMessage, actualMessage);
        Mockito.verify(teamService, Mockito.times(1)).removeProject(projectId, teamId);
    }

    @Test
    void addEmployee_Ok() {
        Long teamId = 1L;
        Long employeeId = 2L;
        String expectedMessage = "Adding the employee to the team was successful.";
        Mockito.when(teamService.addEmployee(employeeId, teamId)).thenReturn(expectedMessage);
        String actualMessage = teamController.addEmployee(teamId, employeeId);
        Assertions.assertEquals(expectedMessage, actualMessage);
        Mockito.verify(teamService, Mockito.times(1)).addEmployee(employeeId, teamId);
    }

    @Test
    void removeEmployee_Ok() {
        Long teamId = 1L;
        Long employeeId = 2L;
        String expectedMessage = "Removing the employee from the team was successful.";
        Mockito.when(teamService.removeEmployee(employeeId, teamId)).thenReturn(expectedMessage);
        String actualMessage = teamController.removeEmployee(teamId, employeeId);
        Assertions.assertEquals(expectedMessage, actualMessage);
        Mockito.verify(teamService, Mockito.times(1)).removeEmployee(employeeId, teamId);
    }
}