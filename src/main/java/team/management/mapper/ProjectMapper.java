package team.management.mapper;

import org.springframework.stereotype.Component;
import team.management.model.Project;
import team.management.model.dto.ProjectRequestDto;
import team.management.model.dto.ProjectResponseDto;

@Component
public class ProjectMapper {
    public ProjectResponseDto toProjectResponseDto(Project project) {
        ProjectResponseDto projectResponseDto = new ProjectResponseDto();
        projectResponseDto.setId(project.getId());
        projectResponseDto.setTitle(project.getTitle());
        projectResponseDto.setDescription(project.getDescription());
        projectResponseDto.setStatus(project.getStatus());
        projectResponseDto.setStartDate(project.getStartDate());
        projectResponseDto.setFinishDate(project.getFinishDate());
        if (project.getTeam() != null) {
            projectResponseDto.setTeamId(project.getTeam().getId());
        } else {
            projectResponseDto.setTeamId(null);
        }
        return projectResponseDto;
    }

    public Project toModelProject(ProjectRequestDto projectRequestDto) {
        Project project = new Project();
        project.setTitle(projectRequestDto.getTitle());
        project.setDescription(projectRequestDto.getDescription());
        project.setStatus(projectRequestDto.getStatus());
        project.setStartDate(projectRequestDto.getStartDate());
        project.setFinishDate(projectRequestDto.getFinishDate());
        project.setTeam(projectRequestDto.getTeam());
        return project;
    }
}
