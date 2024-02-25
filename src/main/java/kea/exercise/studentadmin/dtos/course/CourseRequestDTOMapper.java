package kea.exercise.studentadmin.dtos.course;

import kea.exercise.studentadmin.models.Course;
import kea.exercise.studentadmin.models.Student;
import kea.exercise.studentadmin.models.Teacher;
import kea.exercise.studentadmin.repositories.StudentRepository;
import kea.exercise.studentadmin.repositories.TeacherRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.function.Function;

@Component
public class CourseRequestDTOMapper implements Function<CourseRequestDTO, Course> {
    TeacherRepository teacherRepository;
    StudentRepository studentRepository;

    public CourseRequestDTOMapper(TeacherRepository teacherRepository, StudentRepository studentRepository) {
        this.teacherRepository = teacherRepository;
        this.studentRepository = studentRepository;
    }

    @Override
    public Course apply(CourseRequestDTO courseRequestDTO) {
        Course course = new Course();
        course.setId(courseRequestDTO.id());
        course.setSubject(courseRequestDTO.subject());
        course.setSchoolYear(courseRequestDTO.schoolYear());
        course.setCurrent(courseRequestDTO.current());

        if(courseRequestDTO.teacherId() != null) {
            Optional<Teacher> teacher = teacherRepository.findById(courseRequestDTO.teacherId());
            teacher.ifPresent(course::setTeacher);
        }

        if(courseRequestDTO.studentIds() != null) {
            courseRequestDTO.studentIds().forEach(id -> {
                Optional<Student> student = studentRepository.findById(id);
                student.ifPresent(course::addStudent);
            });
        }

        return course;
    }

}
