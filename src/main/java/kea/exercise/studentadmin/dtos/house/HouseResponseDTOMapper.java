package kea.exercise.studentadmin.dtos.house;

import com.fasterxml.jackson.databind.introspect.AnnotatedAndMetadata;
import kea.exercise.studentadmin.models.House;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Function;

@Service
public class HouseResponseDTOMapper implements Function<House, HouseResponseDTO> {
    @Override
    public HouseResponseDTO apply(House house) {
        return new HouseResponseDTO(
                house.getName(),
                house.getFounder(),
                List.of(house.getColor1(), house.getColor2())
        );
    }
}
