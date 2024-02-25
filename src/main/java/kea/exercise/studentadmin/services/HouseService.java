package kea.exercise.studentadmin.services;

import kea.exercise.studentadmin.dtos.house.HouseResponseDTO;
import kea.exercise.studentadmin.dtos.house.HouseResponseDTOMapper;
import kea.exercise.studentadmin.dtos.student.StudentResponseDTO;
import kea.exercise.studentadmin.repositories.HouseRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class HouseService {
    private final HouseRepository houseRepository;
    private final HouseResponseDTOMapper houseResponseDTOMapper;

    public HouseService(HouseRepository houseRepository, HouseResponseDTOMapper houseResponseDTOMapper) {
        this.houseRepository = houseRepository;
        this.houseResponseDTOMapper = houseResponseDTOMapper;
    }

    public List<HouseResponseDTO> findAll() {
        return houseRepository.findAll().stream().map(houseResponseDTOMapper).toList();
    }

    public Optional<HouseResponseDTO> findById(String name) {
        return houseRepository.findById(name).map(houseResponseDTOMapper);
    }

}
