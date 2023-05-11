package team.management.model;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import team.management.model.enam.Level;
import team.management.model.enam.Role;

@Getter
@Setter
@Entity
@Table(name = "employees")
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "level", nullable = false)
    @Enumerated(EnumType.STRING)
    private Level level;
    @Column(name = "role", nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role;
    @Column(name = "hireDate", nullable = false)
    private LocalDate hireDate;
    @Column(name = "terminationDate")
    private LocalDate terminationDate;
    @ManyToOne(fetch = FetchType.LAZY)
    private Team team;
}
