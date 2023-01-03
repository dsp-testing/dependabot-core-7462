package fr.schoolbyhiit.portailformation.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "modules", schema = "portail_formation")
@Getter
@Setter
public class Module {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(max = 100)
    private String name;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "module", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Course> courses;
}
