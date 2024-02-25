package kea.exercise.studentadmin.repositories;

import kea.exercise.studentadmin.models.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Long> {
}
