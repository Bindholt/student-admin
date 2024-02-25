package kea.exercise.studentadmin.dtos.teacher;

import kea.exercise.studentadmin.models.House;
import kea.exercise.studentadmin.models.Teacher;
import kea.exercise.studentadmin.repositories.HouseRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.function.Function;

@Component
public class TeacherRequestDTOMapper implements Function<TeacherRequestDTO, Teacher> {

    private final HouseRepository houseRepository;

    public TeacherRequestDTOMapper(HouseRepository houseRepository) {
        this.houseRepository = houseRepository;
    }

    @Override
    public Teacher apply(TeacherRequestDTO teacherRequestDTO) {
        Teacher teacher = new Teacher();
        teacher.setId(teacherRequestDTO.id());
        teacher.setFirstName(teacherRequestDTO.firstName());
        teacher.setMiddleName(teacherRequestDTO.middleName());
        teacher.setLastName(teacherRequestDTO.lastName());
        teacher.setDateOfBirth(teacherRequestDTO.dateOfBirth());
        teacher.setHeadOfHouse(teacherRequestDTO.headOfHouse());
        teacher.setEmploymentType(teacherRequestDTO.employmentType());
        teacher.setEmploymentStart(teacherRequestDTO.employmentStart());
        teacher.setEmploymentEnd(teacherRequestDTO.employmentEnd());
        Optional<House> house = houseRepository.findById(teacherRequestDTO.house());
        if (house.isPresent()) {
            teacher.setHouse(house.get());
        } else {
            teacher.setHouse(null);
        }

        return teacher;

    }
}
