package kea.exercise.studentadmin.dtos;
import org.springframework.stereotype.Service;
import kea.exercise.studentadmin.models.Student;
import java.util.function.Function;

@Service
public class StudentResponseDTOMapper implements Function<Student, StudentResponseDTO> {
    @Override
    public StudentResponseDTO apply(Student student) {
        return new StudentResponseDTO(
                student.getId(),
                student.getFirstName(),
                student.getMiddleName(),
                student.getLastName(),
                student.getDateOfBirth(),
                student.isPrefect(),
                student.getEnrollmentYear(),
                student.getGraduationYear(),
                student.isGraduated(),
                student.getHouse() == null ? null : student.getHouse().getName(),
                student.getSchoolYear()
        );
    }
}