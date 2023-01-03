package fr.schoolbyhiit.portailformation.model.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import fr.schoolbyhiit.portailformation.model.Module;


@Repository
public interface ModuleRepository extends JpaRepository<Module, Long> {

}