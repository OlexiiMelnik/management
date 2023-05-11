package team.management.model;

import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import team.management.model.enam.Status;


@Getter
@Setter
@Entity
@Table(name = "projects")
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "title", nullable = false)
    private String title;
    @Column(name = "description", nullable = false)
    private String description;
    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private Status status;
    @Column(name = "startDate", nullable = false)
    private LocalDate startDate;
    @Column(name = "finishDate")
    private LocalDate finishDate;
    @ManyToOne(fetch = FetchType.LAZY)
    private Team team;
}
