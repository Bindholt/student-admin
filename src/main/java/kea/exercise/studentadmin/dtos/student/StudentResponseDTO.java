package kea.exercise.studentadmin.dtos.student;

import java.time.LocalDate;

public record StudentResponseDTO (
        int id,
        String firstName,
        String middleName,
        String lastName,
        LocalDate dateOfBirth,
        boolean prefect,
        int enrollmentYear,
        int graduationYear,
        boolean graduated,
        String house,
        int schoolYear
){ }
