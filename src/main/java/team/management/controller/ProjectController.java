package team.management.controller;

import java.util.List;
import java.util.stream.Collectors;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import team.management.mapper.ProjectMapper;
import team.management.model.Project;
import team.management.model.dto.ProjectRequestDto;
import team.management.model.dto.ProjectResponseDto;
import team.management.service.ProjectService;

@RestController
@RequestMapping("/projects")
@RequiredArgsConstructor
@Tag(name = "Project Controller", description = "REST-full endpoints  related to project")
public class ProjectController {
    private final ProjectService projectService;
    private final ProjectMapper projectMapper;

    @PostMapping
    @Operation(summary = "post new project to db")
    public ProjectResponseDto create(@RequestBody ProjectRequestDto requestDto) {
        Project save
                = projectService.save(projectMapper.toModelProject(requestDto));
        return projectMapper.toProjectResponseDto(save);
    }

    @GetMapping("/{id}")
    @Operation(summary = "get project by id")
    public ProjectResponseDto findById(@PathVariable Long id) {
        return projectMapper.toProjectResponseDto(
                projectService.findById(id).orElseThrow(
                        EntityNotFoundException::new));
    }

    @GetMapping("/all")
    @Operation(summary = "get all projects")
    public List<ProjectResponseDto> findAll() {
        return projectService.findAll()
                .stream()
                .map(projectMapper::toProjectResponseDto)
                .collect(Collectors.toList());
    }

    @PutMapping("/{id}")
    @Operation(summary = "update project by id")
    public ProjectResponseDto update(@PathVariable Long id,
                                     @RequestBody ProjectRequestDto requestDto) {
        Project project = projectMapper.toModelProject(requestDto);
        return projectMapper.toProjectResponseDto(projectService.save(project));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "delete project by id")
    public void delete(@PathVariable Long id) {
        Project project
                = projectService.findById(id).orElseThrow(EntityNotFoundException::new);
        if (project.getTeam() != null) {
            throw  new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Project is part of a team and cannot be deleted. " +
                            "First, make changes to the command");
        }
        projectService.deleteById(id);
    }

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<String> handleResponseStatusException(
            ResponseStatusException e) {
        return ResponseEntity.status(e.getStatusCode()).body(e.getMessage());
    }
}
