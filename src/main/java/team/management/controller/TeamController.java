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
import team.management.mapper.TeamMapper;
import team.management.model.Team;
import team.management.model.dto.TeamRequestDto;
import team.management.model.dto.TeamResponseDto;
import team.management.service.TeamService;

@RestController
@RequestMapping("/teams")
@RequiredArgsConstructor
@Tag(name = "Team Controller", description = "REST-full endpoints  related to team and endpoints to manege team ")
public class TeamController {
    private final TeamService teamService;
    private final TeamMapper teamMapper;

    @PostMapping
    @Operation(summary = "post new team to db")
    public TeamResponseDto create(@RequestBody TeamRequestDto requestDto) {
        Team save = teamService.save(teamMapper.toModelTeam(requestDto));
        return teamMapper.toTeamResponseDto(save);
    }

    @GetMapping("/{id}")
    @Operation(summary = "get team by id")
    public TeamResponseDto findById(@PathVariable Long id) {
        return teamMapper.toTeamResponseDto(teamService.findById(id).orElseThrow(
                EntityNotFoundException::new));
    }

    @GetMapping("/all")
    @Operation(summary = "get all teams ")
    public List<TeamResponseDto> findAll() {
        return teamService.findAll()
                .stream()
                .map(teamMapper::toTeamResponseDto)
                .collect(Collectors.toList());
    }

    @PutMapping("/{id}")
    @Operation(summary = "update team by id")
    public TeamResponseDto update(@PathVariable Long id,
                                  @RequestBody TeamRequestDto requestDto) {
        Team update
                = teamService.update(teamMapper.toModelTeam(requestDto));
        return teamMapper.toTeamResponseDto(update);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "update team by id")
    public void delete(@PathVariable Long id) {
        Team team
                = teamService.findById(id).orElseThrow(EntityNotFoundException::new);
        if (!team.getProjectList().isEmpty()) {
            throw  new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "The team has dependent projects. " +
                            "First, separate the project from the team.");
        } else if (!team.getEmployeeList().isEmpty()) {
            throw  new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Team contains employees. Disband employees first.");
        } else {
            teamService.deleteById(id);
        }
    }

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<String> handleResponseStatusException(
            ResponseStatusException e) {
        return ResponseEntity.status(e.getStatusCode()).body(e.getMessage());
    }

    @PostMapping("/{teamId}/projects/{projectId}")
    @Operation(summary = "add project to team")
    public String addProject(@PathVariable Long teamId, @PathVariable Long projectId) {
        return teamService.addProject(projectId, teamId);
    }

    @DeleteMapping("/{teamId}/projects/{projectId}")
    @Operation(summary = "remove project from team")
    public String removeProject(@PathVariable Long teamId, @PathVariable Long projectId) {
        return teamService.removeProject(projectId, teamId);
    }

    @PostMapping("/{teamId}/employees/{employeeId}")
    @Operation(summary = "add employee to team")
    public String addEmployee(@PathVariable Long teamId, @PathVariable Long employeeId) {
        return teamService.addEmployee(employeeId, teamId);
    }

    @DeleteMapping("/{teamId}/employees/{employeeId}")
    @Operation(summary = "remove employee from team")
    public String removeEmployee(@PathVariable Long teamId, @PathVariable Long employeeId) {
        return teamService.removeEmployee(employeeId, teamId);
    }
}
