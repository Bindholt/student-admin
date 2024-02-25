package kea.exercise.studentadmin.dtos.course;

import kea.exercise.studentadmin.dtos.student.StudentResponseDTOMapper;
import kea.exercise.studentadmin.dtos.teacher.TeacherResponseDTOMapper;
import kea.exercise.studentadmin.models.Course;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class CourseResponseDTOMapper implements Function<Course, CourseResponseDTO>{
    private final TeacherResponseDTOMapper teacherResponseDTOMapper;
    private final StudentResponseDTOMapper studentResponseDTOMapper;

    public CourseResponseDTOMapper(TeacherResponseDTOMapper teacherResponseDTOMapper, StudentResponseDTOMapper studentResponseDTOMapper) {
        this.teacherResponseDTOMapper = teacherResponseDTOMapper;
        this.studentResponseDTOMapper = studentResponseDTOMapper;
    }

    @Override
    public CourseResponseDTO apply(Course course) {
        return new CourseResponseDTO(
                course.getId(),
                course.getSubject(),
                course.getSchoolYear(),
                course.isCurrent(),
                course.getTeacher() == null ? null : teacherResponseDTOMapper.apply(course.getTeacher()),
                course.getStudents()
                        .stream()
                        .map(studentResponseDTOMapper)
                        .toList()
        );
    }
}
