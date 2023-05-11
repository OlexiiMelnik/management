package team.management.service;

import java.util.List;
import java.util.Optional;
import team.management.model.Project;

public interface ProjectService {
    Project save(Project project);

    Optional<Project> findById(Long id);

    List<Project> findAll();

    Project update(Project project);

    void deleteById(Long id);
}
