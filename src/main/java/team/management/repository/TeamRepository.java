package team.management.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import team.management.model.Team;

@Repository
public interface TeamRepository extends JpaRepository<Team, Long> {

}
