package team.management.service.impl;

import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import team.management.model.Project;
import team.management.repository.ProjectRepository;
import team.management.service.ProjectService;

@Service
@RequiredArgsConstructor
public class ProjectServiceImpl implements ProjectService {
    private final ProjectRepository projectRepository;

    @Override
    public Project save(Project project) {
        return projectRepository.save(project);
    }

    @Override
    public Optional<Project> findById(Long id) {
        return projectRepository.findById(id);
    }

    @Override
    public List<Project> findAll() {
        return projectRepository.findAll();
    }

    @Override
    public Project update(Project project) {
        return projectRepository.save(project);
    }

    @Override
    public void deleteById(Long id) {
        projectRepository.deleteById(id);
    }
}
