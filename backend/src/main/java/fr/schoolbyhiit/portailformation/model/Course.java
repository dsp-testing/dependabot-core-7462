package fr.schoolbyhiit.portailformation.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "courses", schema = "portail_formation")
@Getter
@Setter
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(max = 100)
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "file_name")
    private String fileName;

    @ManyToOne
    @JoinColumn(name = "module_id", nullable = false)
    private Module module;

}
