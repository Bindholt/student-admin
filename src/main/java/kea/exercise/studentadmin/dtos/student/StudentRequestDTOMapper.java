package kea.exercise.studentadmin.dtos.student;

import kea.exercise.studentadmin.models.House;
import kea.exercise.studentadmin.models.Student;
import kea.exercise.studentadmin.repositories.HouseRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.function.Function;

@Component
public class StudentRequestDTOMapper implements Function<StudentRequestDTO, Student> {
    private final HouseRepository houseRepository;

    public StudentRequestDTOMapper(HouseRepository houseRepository) {
        this.houseRepository = houseRepository;
    }

    @Override
    public Student apply(StudentRequestDTO studentRequestDTO) {
        Student student = new Student();
        student.setId(studentRequestDTO.id());
        student.setFirstName(studentRequestDTO.firstName());
        student.setMiddleName(studentRequestDTO.middleName());
        student.setLastName(studentRequestDTO.lastName());
        student.setFullName(studentRequestDTO.fullName());
        student.setDateOfBirth(studentRequestDTO.dateOfBirth());
        student.setSchoolYear(studentRequestDTO.schoolYear());
        student.setPrefect(studentRequestDTO.prefect());
        student.setEnrollmentYear(studentRequestDTO.enrollmentYear());
        student.setGraduationYear(studentRequestDTO.graduationYear());
        student.setGraduated(studentRequestDTO.graduated());
        Optional<House> house = houseRepository.findById(studentRequestDTO.house());
        if (house.isPresent()) {
            student.setHouse(house.get());
        } else {
            student.setHouse(null);
        }

        return student;
    }
}
