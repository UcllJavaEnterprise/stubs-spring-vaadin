package be.ucll.java.ent.repository;

import be.ucll.java.ent.model.StudentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StudentRepository extends JpaRepository<StudentEntity, Integer> {

    List<StudentEntity> findAllByNaamContainsIgnoreCaseOrderByNaam(String naam);

    StudentEntity getFirstByNaam(String naam);

}
