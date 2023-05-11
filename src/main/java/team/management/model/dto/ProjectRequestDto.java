package team.management.model.dto;

import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;
import team.management.model.Team;
import team.management.model.enam.Status;

@Getter
@Setter
public class ProjectRequestDto {
    private String title;
    private String description;
    private Status status;
    private LocalDate startDate;
    private LocalDate finishDate;
    private Team team;;
}
