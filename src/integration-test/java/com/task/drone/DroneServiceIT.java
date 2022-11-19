package com.task.drone;

import static com.task.drone.IntegrationTestDroneFactory.buildDrone;
import static org.assertj.core.api.Assertions.assertThat;

import com.task.BaseIT;
import com.task.drone.model.Drone;
import com.task.drone.repository.DroneRepository;
import com.task.drone.service.DroneService;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class DroneServiceIT extends BaseIT {

  @Autowired private DroneService droneService;

  @Autowired private DroneRepository droneRepository;

  @AfterEach
  void cleanDB() {

    droneRepository.deleteAll();
  }

  @Test
  void registerDrone_withValidModel_droneRegisteredSuccessfully() {

    // GIVEN
    // WHEN
    final UUID serialNumber = droneService.registerDrone(buildDrone());

    // THEN
    assertThat(serialNumber).isEqualTo(droneRepository.findAll().get(0).getSerialNumber());
  }

  @Test
  void givenDronesAreRegistered_whenListRegisteredDrones_allRegisteredDroneAreListed() {

    // GIVEN
    final int expectedAllDronesAmount = 10;
    registerDronesIntoFleet(false);

    // WHEN
    final List<Drone> drones = droneService.getAllDrones();

    // THEN
    assertThat(drones).isNotEmpty();
    assertThat(drones).hasSize(expectedAllDronesAmount);
  }

  @Test
  void givenDronesAreRegistered_whenListAvailableRegisteredDrones_OnlyDronesCanBeLoadedAreListed() {

    // GIVEN
    final int expectedAvailableDronesAmount = 7;
    registerDronesIntoFleet(true);

    // WHEN
    final List<Drone> drones = droneService.getAvailableDrones();

    // THEN
    assertThat(drones).isNotEmpty();
    assertThat(drones).hasSize(expectedAvailableDronesAmount);
  }

  @Test
  void givenDroneAreRegistered_whenUnRegisterThisDrone_droneUnRegisteredSuccessfully() {

    // GIVEN
    final Drone drone = buildDrone();
    final UUID serialNumber = droneService.registerDrone(drone);
    final Optional<Drone> registeredDrone = droneRepository.getDronesBySerialNumber(serialNumber);

    assertThat(registeredDrone).isPresent();

    // WHEN
    droneService.unRegisterDrone(serialNumber);

    // THEN
    final Optional<Drone> unRegisteredDrone = droneRepository.getDronesBySerialNumber(serialNumber);
    assertThat(unRegisteredDrone).isEmpty();
  }

  private void registerDronesIntoFleet(final boolean registerDronesWithDifferentStates) {

    if (!registerDronesWithDifferentStates) {
      for (int i = 0; i < 10; i++) {

        droneRepository.save(buildDrone());
      }
    } else {

      for (int i = 0; i < 10; i++) {

        if (i == 1 || i == 3 || i == 5) {

          droneRepository.save(buildDrone(Drone.State.DELIVERING));
        } else {
          droneRepository.save(buildDrone(Drone.State.IDLE));
        }
      }
    }
  }
}
