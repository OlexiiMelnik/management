package team.management.service.impl;

import java.util.List;
import java.util.Optional;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import team.management.model.Employee;
import team.management.model.Project;
import team.management.model.Team;
import team.management.repository.EmployeeRepository;
import team.management.repository.ProjectRepository;
import team.management.repository.TeamRepository;
import team.management.service.TeamService;

@Service
@RequiredArgsConstructor
public class TeamServiceImpl implements TeamService {
    private final TeamRepository teamRepository;
    private final ProjectRepository projectRepository;
    private final EmployeeRepository employeeRepository;

    @Override
    public Team save(Team team) {
        return teamRepository.save(team);
    }

    @Override
    public Optional<Team> findById(Long id) {
        return teamRepository.findById(id);
    }

    @Override
    public List<Team> findAll() {
        return teamRepository.findAll();
    }

    @Override
    public Team update(Team team) {
        return teamRepository.save(team);
    }

    @Override
    public void deleteById(Long id) {
        teamRepository.deleteById(id);
    }

    @Override
    public String addProject(Long projectId, Long teamId) {
        Project project = projectRepository.findById(projectId).orElseThrow(
                EntityNotFoundException::new);
        Team team = teamRepository.findById(teamId).orElseThrow(
                EntityNotFoundException::new);
        List<Project> projectList = team.getProjectList();
        projectList.add(project);
        project.setTeam(team);
        projectRepository.save(project);
        return "Adding the project to the team was successful. Good luck";
    }

    @Override
    public String removeProject(Long projectId, Long teamId) {
        Project project = projectRepository.findById(projectId).orElseThrow(
                EntityNotFoundException::new);
        Team team = teamRepository.findById(teamId).orElseThrow(
                EntityNotFoundException::new);
        List<Project> projectList = team.getProjectList();
        projectList.remove(project);
        project.setTeam(null);
        projectRepository.save(project);
        return "Removing the project from the team was successful.";
    }

    @Override
    public String addEmployee(Long employeeId, Long teamId) {
        Employee employee = employeeRepository.findById(employeeId).orElseThrow(
                EntityNotFoundException::new);
        Team team = teamRepository.findById(teamId).orElseThrow(
                EntityNotFoundException::new);
        List<Employee> employeeList = team.getEmployeeList();
        employeeList.add(employee);
        employee.setTeam(team);
        employeeRepository.save(employee);
        return "Adding the employee to the team was successful.";
    }

    @Override
    public String removeEmployee(Long employeeId, Long teamId) {
        Employee employee = employeeRepository.findById(employeeId).orElseThrow(
                EntityNotFoundException::new);
        Team team = teamRepository.findById(teamId).orElseThrow(
                EntityNotFoundException::new);
        List<Employee> employeeList = team.getEmployeeList();
        employeeList.remove(employee);
        employee.setTeam(null);
        employeeRepository.save(employee);
        return "Removing the employee from the team was successful.";
    }
}
