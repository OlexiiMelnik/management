package team.management.model.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class TeamResponseDto {
    private Long id;
    @JsonProperty("projectsId")
    private List<Long> projectId;
    @JsonProperty("employeesId")
    private List<Long> employeeId;
}
