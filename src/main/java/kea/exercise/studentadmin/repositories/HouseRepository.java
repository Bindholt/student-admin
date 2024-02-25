package kea.exercise.studentadmin.repositories;

import kea.exercise.studentadmin.models.House;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HouseRepository extends JpaRepository<House, String>{
}
