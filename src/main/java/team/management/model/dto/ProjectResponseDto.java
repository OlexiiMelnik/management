package team.management.model.dto;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import team.management.model.enam.Status;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProjectResponseDto {
    private Long id;
    private String title;
    private String description;
    private Status status;
    private LocalDate startDate;
    private LocalDate finishDate;
    @JsonProperty("teamId")
    private Long teamId;
}
