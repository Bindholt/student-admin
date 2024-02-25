package kea.exercise.studentadmin.dtos.teacher;

import kea.exercise.studentadmin.dtos.student.StudentResponseDTO;
import kea.exercise.studentadmin.models.Teacher;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class TeacherResponseDTOMapper implements Function<Teacher, TeacherResponseDTO> {
    @Override
    public TeacherResponseDTO apply(Teacher teacher) {
        return new TeacherResponseDTO(
                teacher.getId(),
                teacher.getFirstName(),
                teacher.getMiddleName(),
                teacher.getLastName(),
                teacher.getDateOfBirth(),
                teacher.getHouse() == null ? null : teacher.getHouse().getName(),
                teacher.isHeadOfHouse(),
                teacher.getEmploymentType(),
                teacher.getEmploymentStart(),
                teacher.getEmploymentEnd()
        );
    }
}
