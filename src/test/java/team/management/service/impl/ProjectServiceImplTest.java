package team.management.service.impl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import team.management.model.Project;
import team.management.model.enam.Status;
import team.management.repository.ProjectRepository;
import team.management.service.ProjectService;

class ProjectServiceImplTest {
    private ProjectRepository projectRepository;
    private ProjectService projectService;
    private Project project;
    private List<Project> projectList;

    @BeforeEach
    void setUp() {
        projectRepository = Mockito.mock(ProjectRepository.class);
        projectService = new ProjectServiceImpl(projectRepository);
        project = new Project();
        project.setTitle("some good project");
        project.setDescription("very hard project");
        project.setStatus(Status.OPEN);
        project.setStartDate(LocalDate.of(2021,5,3));
        projectList = new ArrayList<>();
        projectList.add(project);
    }

    @Test
    void save_Ok() {
        Mockito.when(projectRepository.save(project)).thenReturn(project);
        Project actual = projectService.save(project);
        Assertions.assertEquals(project, actual);
    }

    @Test
    void findById() {
        Mockito.when(projectRepository.findById(1L)).thenReturn(Optional.of(project));
        Optional<Project> actual = projectService.findById(1L);
        Assertions.assertTrue(actual.isPresent());
        Assertions.assertEquals(project, actual.get());
    }

    @Test
    void findAll() {
        Mockito.when(projectRepository.findAll()).thenReturn(projectList);
        List<Project> actual = projectService.findAll();
        Assertions.assertEquals(projectList, actual);
    }

    @Test
    void update_Ok() {
        Mockito.when(projectRepository.save(project)).thenReturn(project);
        Project actual = projectService.update(project);
        Assertions.assertEquals(project, actual);
    }

    @Test
    void delete_Ok() {
        Long id = 1L;
        Mockito.doNothing().when(projectRepository).deleteById(id);
        projectService.deleteById(id);
        Mockito.verify(projectRepository,
                Mockito.times(1)).deleteById(id);
    }
}
