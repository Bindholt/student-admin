package kea.exercise.studentadmin.dtos.course;

import kea.exercise.studentadmin.dtos.student.StudentResponseDTO;
import kea.exercise.studentadmin.dtos.teacher.TeacherResponseDTO;

import java.util.List;

public record CourseResponseDTO(
        Long id,
        String subject,
        int schoolYear,
        boolean current,
        TeacherResponseDTO teacher,
        List<StudentResponseDTO> students
) {
}
