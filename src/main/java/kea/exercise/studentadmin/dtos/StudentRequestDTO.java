package kea.exercise.studentadmin.dtos;

import java.time.LocalDate;

public record StudentRequestDTO (
    int id,
    String firstName,
    String middleName,
    String lastName,
    String fullName,
    LocalDate dateOfBirth,
    boolean prefect,
    int enrollmentYear,
    int graduationYear,
    boolean graduated,
    String house,
    int schoolYear
){
    public StudentRequestDTO {
        if (fullName != null) {
            String[] names = fullName.split(" ");
            if (names.length == 1) {
                firstName = names[0];
            } else if (names.length == 2) {
                firstName = names[0];
                lastName = names[1];
            } else {
                firstName = names[0];
                middleName = names[1];
                lastName = names[2];
            }
        }
    }
}