package kea.exercise.studentadmin.dtos.course;

import kea.exercise.studentadmin.models.Student;
import kea.exercise.studentadmin.models.Teacher;

import java.util.List;

public record CourseRequestDTO(
        Long id,
        String subject,
        int schoolYear,
        boolean current,
        Long teacherId,
        List<Long> studentIds
) { }
