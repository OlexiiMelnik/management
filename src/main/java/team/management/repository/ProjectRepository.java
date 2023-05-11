package team.management.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import team.management.model.Project;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {
}
