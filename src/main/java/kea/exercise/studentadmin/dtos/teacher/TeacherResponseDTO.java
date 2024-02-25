package kea.exercise.studentadmin.dtos.teacher;

import kea.exercise.studentadmin.models.EmpType;
import java.time.LocalDate;

public record TeacherResponseDTO (
        Long id,
        String firstName,
        String middleName,
        String lastName,
        LocalDate dateOfBirth,
        String house,
        boolean headOfHouse,
        EmpType employmentType,
        LocalDate employmentStart,
        LocalDate employmentEnd
){ }