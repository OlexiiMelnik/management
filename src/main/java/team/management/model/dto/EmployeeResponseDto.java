package team.management.model.dto;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import team.management.model.Team;
import team.management.model.enam.Level;
import team.management.model.enam.Role;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class EmployeeResponseDto {
    private Long id;
    private Level level;
    private Role role;
    private LocalDate hireDate;
    private LocalDate terminationDate;
    @JsonProperty("teamId")
    private Long teamId;
}
