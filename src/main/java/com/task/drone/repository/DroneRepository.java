package com.task.drone.repository;

import com.task.drone.model.Drone;
import com.task.drone.model.Drone.State;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DroneRepository extends JpaRepository<Drone, UUID> {

  List<Drone> getDronesByStateIs(State state);

  Optional<Drone> getDronesBySerialNumber(UUID serialNumber);

  void deleteDroneBySerialNumber(UUID serialNumber);
}
