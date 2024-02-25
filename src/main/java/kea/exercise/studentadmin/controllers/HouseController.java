package kea.exercise.studentadmin.controllers;

import kea.exercise.studentadmin.dtos.house.HouseResponseDTO;
import kea.exercise.studentadmin.services.HouseService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/houses")
public class HouseController {
    private final HouseService houseService;

    public HouseController(HouseService houseService) {
        this.houseService = houseService;
    }

    @GetMapping
    public List<HouseResponseDTO> getAll() {
        return houseService.findAll();
    }


}
