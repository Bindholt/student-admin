package kea.exercise.studentadmin.repositories;

import kea.exercise.studentadmin.models.Course;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<Course, Long> {
}
