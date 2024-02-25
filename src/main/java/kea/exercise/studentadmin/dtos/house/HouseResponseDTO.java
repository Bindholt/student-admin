package kea.exercise.studentadmin.dtos.house;


import java.time.LocalDate;
import java.util.List;

public record HouseResponseDTO (
        String name,
        String founder,
        List<String> colors
){ }