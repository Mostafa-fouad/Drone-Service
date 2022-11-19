package com.task.drone;

import static com.task.drone.DroneFactory.buildDrone;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import com.task.core.exception.NotFoundException;
import com.task.core.exception.ValidationException;
import com.task.drone.model.Drone;
import com.task.drone.repository.DroneRepository;
import com.task.drone.service.DroneService;
import com.task.drone.service.impl.DroneServiceImpl;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@Import({DroneServiceImpl.class})
public class DroneServiceTest {

  @Autowired private DroneService droneService;

  @MockBean private DroneRepository droneRepository;

  @Test
  void registerDrone_withValidDto_droneRegisteredSuccessfully() {

    // GIVEN
    final Drone drone = buildDrone();

    when(droneRepository.save(drone)).thenReturn(drone);

    // WHEN
    final UUID serialNumber = droneService.registerDrone(drone);

    // THEN
    assertThat(serialNumber).isEqualTo(drone.getSerialNumber());
  }

  @Test
  void registerDrones_exceedsFleetCapacity_validationExceptionThrown() {

    // GIVEN
    final Drone drone = buildDrone();
    when(droneRepository.count()).thenReturn(10L);

    // WHEN and THEN
    final String exceptionMessage =
        assertThrows(ValidationException.class, () -> droneService.registerDrone(drone))
            .getMessage();

    assertThat(exceptionMessage).isEqualTo("Drone's fleet is full");
  }

  @Test
  void unRegisterDrone_droneExists_droneUnRegisteredSuccessfully() {

    // GIVEN
    final Drone drone = buildDrone();
    when(droneRepository.getDronesBySerialNumber(drone.getSerialNumber()))
        .thenReturn(Optional.of(drone));
    doNothing().when(droneRepository).deleteDroneBySerialNumber(drone.getSerialNumber());

    // WHEN
    droneService.unRegisterDrone(drone.getSerialNumber());

    // THEN Drone unregistered successfully

  }

  @Test
  void unRegisterDrone_droneDoesNotExists_NotFoundExceptionThrown() {

    // GIVEN
    final Drone drone = buildDrone();
    when(droneRepository.getDronesBySerialNumber(drone.getSerialNumber()))
        .thenThrow(
            new NotFoundException(
                String.format("Drone with serial number %s not found.", drone.getSerialNumber())));

    // WHEN and THEN
    final String exceptionMessage =
        assertThrows(
                NotFoundException.class,
                () -> droneService.unRegisterDrone(drone.getSerialNumber()))
            .getMessage();

    assertThat(exceptionMessage)
        .isEqualTo(
            String.format("Drone with serial number %s not found.", drone.getSerialNumber()));
  }
}
