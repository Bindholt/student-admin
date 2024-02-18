package kea.exercise.studentadmin.repositories;

import kea.exercise.studentadmin.models.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeacherRepository extends JpaRepository<Teacher, Integer> {
}
