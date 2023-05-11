package team.management.service;

import java.util.List;
import java.util.Optional;
import team.management.model.Team;

public interface TeamService {
    Team save(Team team);

    Optional<Team> findById(Long id);

    List<Team> findAll();

    Team update(Team team);

    void deleteById(Long id);

    String addProject(Long projectId, Long teamId);

    String removeProject(Long projectId, Long teamId);

    String addEmployee(Long employeeId, Long teamId);

    String removeEmployee(Long employeeId, Long teamId);
}
