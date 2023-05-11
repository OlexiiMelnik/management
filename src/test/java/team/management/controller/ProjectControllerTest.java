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
import team.management.mapper.ProjectMapper;
import team.management.model.Project;
import team.management.model.Team;
import team.management.model.dto.ProjectRequestDto;
import team.management.model.dto.ProjectResponseDto;
import team.management.model.enam.Status;
import team.management.service.ProjectService;

class ProjectControllerTest {
    private ProjectController projectController;
    private ProjectService projectService;
    private ProjectMapper projectMapper;
    private Project project;
    private ProjectResponseDto responseDtoP;
    private ProjectRequestDto requestDtoP;
    private List<Project> projectList;

    @BeforeEach
    void setUp() {
        projectService = Mockito.mock(ProjectService.class);
        projectMapper = Mockito.mock(ProjectMapper.class);
        projectController = new ProjectController(projectService, projectMapper);

        project = new Project();
        project.setId(1L);
        project.setTitle("some good project");
        project.setDescription("very hard project");
        project.setStatus(Status.OPEN);
        project.setStartDate(LocalDate.of(2021,5,3));

        projectList = new ArrayList<>();
        projectList.add(project);

        requestDtoP = new ProjectRequestDto();
        requestDtoP.setTitle(project.getTitle());
        requestDtoP.setDescription(project.getDescription());
        requestDtoP.setStatus(project.getStatus());
        requestDtoP.setStartDate(project.getStartDate());

        responseDtoP = new ProjectResponseDto();
        responseDtoP.setId(project.getId());
        responseDtoP.setTitle(project.getTitle());
        responseDtoP.setDescription(project.getDescription());
        responseDtoP.setStatus(project.getStatus());
        responseDtoP.setStartDate(project.getStartDate());
    }

    @Test
    void create_Ok() {
        Mockito.when(projectMapper.toModelProject(requestDtoP)).thenReturn(project);
        Mockito.when(projectService.save(project)).thenReturn(project);
        Mockito.when(projectMapper.toProjectResponseDto(project)).thenReturn(responseDtoP);

        ProjectResponseDto responseDtoActual = projectController.create(requestDtoP);
        Assertions.assertEquals(responseDtoP, responseDtoActual);

        Mockito.verify(projectService, Mockito.times(1)).save(project);
        Mockito.verify(projectMapper, Mockito.times(1)).toProjectResponseDto(project);
    }

    @Test
    void findById_Ok() {
        Long id = 1L;
        Mockito.when(projectService.findById(id)).thenReturn(Optional.of(project));
        Mockito.when(projectMapper.toProjectResponseDto(project)).thenReturn(responseDtoP);

        ProjectResponseDto responseDtoActual = projectController.findById(id);
        Assertions.assertEquals(responseDtoP, responseDtoActual);
        Mockito.verify(projectService, Mockito.times(1)).findById(id);
        Mockito.verify(projectMapper, Mockito.times(1)).toProjectResponseDto(project);
    }

    @Test
    void findAll_Ok() {
        Mockito.when(projectService.findAll()).thenReturn(projectList);
        Mockito.when(projectMapper.toProjectResponseDto(project)).thenReturn(responseDtoP);

        List<ProjectResponseDto> expected = List.of(responseDtoP);
        List<ProjectResponseDto> responseDtoListActual = projectController.findAll();

        Assertions.assertEquals(expected, responseDtoListActual);
    }

    @Test
    void update_Ok() {
        Long id = 1L;
        Mockito.when(projectMapper.toModelProject(requestDtoP)).thenReturn(project);
        Mockito.when(projectService.save(project)).thenReturn(project);
        Mockito.when(projectMapper.toProjectResponseDto(project)).thenReturn(responseDtoP);

        ProjectResponseDto responseDtoActual = projectController.update(id, requestDtoP);
        Assertions.assertEquals(responseDtoP, responseDtoActual);
    }

    @Test
    void delete_Ok() {
        Long id = 1L;
        Mockito.when(projectService.findById(id)).thenReturn(Optional.of(project));
        Mockito.doNothing().when(projectService).deleteById(id);
        projectController.delete(id);
        Mockito.verify(projectService, Mockito.times(1)).findById(id);
        Mockito.verify(projectService, Mockito.times(1)).deleteById(id);
    }

    @Test
    void delete_ProjectWithTeam_ThrowsException() {
        Long id = 1L;
        project.setTeam(new Team());
        Mockito.when(projectService.findById(id)).thenReturn(Optional.of(project));

        ResponseStatusException exception = Assertions.assertThrows(ResponseStatusException.class,
                () -> projectController.delete(id));
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, exception.getStatusCode());
        Assertions.assertEquals("Project is part of a team and cannot be deleted. " +
                "First, make changes to the command", exception.getReason());

        Mockito.verify(projectService, Mockito.times(1)).findById(id);
        Mockito.verify(projectService, Mockito.never()).deleteById(id);
    }
}
