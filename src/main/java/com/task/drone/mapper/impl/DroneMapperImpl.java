package com.task.drone.mapper.impl;

import com.task.core.exception.NotFoundException;
import com.task.drone.dto.DeliverMedicationItemsDTO;
import com.task.drone.dto.DroneBatteryLevelDTO;
import com.task.drone.dto.DroneDTO;
import com.task.drone.dto.DroneLoadedMedicationDTO;
import com.task.drone.dto.EditDroneDTO;
import com.task.drone.dto.LoadMedicationItemsOnDroneDTO;
import com.task.drone.dto.RegisterDroneDTO;
import com.task.drone.mapper.DroneMapper;
import com.task.drone.model.Drone;
import com.task.drone.repository.DroneRepository;
import com.task.medication.json.LoadedMedicationItems;
import com.task.medication.json.RegisteredMedication;
import com.task.medication.model.Medication;
import com.task.medication.repository.MedicationRepository;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DroneMapperImpl implements DroneMapper {

  private static final String DRONE_NOT_FOUND_EXCEPTION_MESSAGE =
      "Drone with serial number %s not found.";
  private static final String MEDICATION_NOT_FOUND_EXCEPTION_MESSAGE =
      "Medication with Id %s is not found";
  private static final String DRONE_INITIAL_LOCATION = "Fleet";
  private static final int BATTERY_FULL_CHARGE = 100;

  private final DroneRepository droneRepository;
  private final MedicationRepository medicationRepository;

  @Override
  public Drone mapRegisterDroneDtoToDroneModel(final RegisterDroneDTO registerDroneDTO) {
    return Drone.builder()
        .state(Drone.State.IDLE)
        .currentLocation(DRONE_INITIAL_LOCATION)
        .batteryCapacity(BATTERY_FULL_CHARGE)
        .serialNumber(UUID.randomUUID())
        .weightLimit(registerDroneDTO.getWeightLimit())
        .model(Drone.Model.valueOf(registerDroneDTO.getModel()))
        .build();
  }

  @Override
  public Drone mapEditDroneDtoToDroneModel(final EditDroneDTO editDroneDTO) {

    final Drone drone =
        droneRepository
            .getDronesBySerialNumber(editDroneDTO.getDroneSerialNumber())
            .orElseThrow(
                () ->
                    new NotFoundException(
                        String.format(
                            DRONE_NOT_FOUND_EXCEPTION_MESSAGE,
                            editDroneDTO.getDroneSerialNumber())));

    drone.setModel(
        Objects.nonNull(editDroneDTO.getModel())
            ? Drone.Model.valueOf(editDroneDTO.getModel())
            : drone.getModel());
    drone.setWeightLimit(
        Objects.nonNull(editDroneDTO.getWeightLimit())
            ? editDroneDTO.getWeightLimit()
            : drone.getWeightLimit());

    return drone;
  }

  @Override
  public DroneDTO mapDroneModelToDroneDto(final Drone droneModel) {
    return DroneDTO.builder()
        .serialNumber(droneModel.getSerialNumber())
        .weightLimit(droneModel.getWeightLimit())
        .batteryCapacity(droneModel.getBatteryCapacity())
        .model(droneModel.getModel().getValue())
        .state(droneModel.getState().getValue())
        .currentLocation(droneModel.getCurrentLocation())
        .loadedMedicationItems(droneModel.getLoadedMedicationItems())
        .build();
  }

  @Override
  public DroneBatteryLevelDTO mapDroneModelToDroneBatteryLevelDto(final Drone droneModel) {
    return DroneBatteryLevelDTO.builder()
        .batteryLevel(droneModel.getBatteryCapacity())
        .serialNumber(droneModel.getSerialNumber())
        .build();
  }

  @Override
  public DroneLoadedMedicationDTO mapDroneModelToDroneLoadedMedicationDto(final Drone droneModel) {
    return DroneLoadedMedicationDTO.builder()
        .loadedMedicationItems(droneModel.getLoadedMedicationItems())
        .build();
  }

  @Override
  public Drone mapDeliverMedicationItemsDtoToDroneModel(
      final DeliverMedicationItemsDTO deliverMedicationItemsDTO) {

    final Drone currentDrone =
        droneRepository
            .getDronesBySerialNumber(deliverMedicationItemsDTO.getDroneSerialNumber())
            .orElseThrow(
                () ->
                    new NotFoundException(
                        String.format(
                            DRONE_NOT_FOUND_EXCEPTION_MESSAGE,
                            deliverMedicationItemsDTO.getDroneSerialNumber())));
    final Drone drone = currentDrone.clone();
    drone.setCurrentLocation(deliverMedicationItemsDTO.getAddress());
    drone.setState(Drone.State.DELIVERING);

    return drone;
  }

  @Override
  public Drone mapLoadedMedicationItemsToDroneModel(
      final LoadMedicationItemsOnDroneDTO medicationItemsOnDroneDTO) {

    final Drone drone =
        droneRepository
            .getDronesBySerialNumber(medicationItemsOnDroneDTO.getDroneSerialNumber())
            .orElseThrow(
                () ->
                    new NotFoundException(
                        String.format(
                            DRONE_NOT_FOUND_EXCEPTION_MESSAGE,
                            medicationItemsOnDroneDTO.getDroneSerialNumber())));
    final Optional<Medication> optionalMedication =
        medicationRepository.findById(medicationItemsOnDroneDTO.getMedicationId());

    if (optionalMedication.isEmpty()) {

      throw new NotFoundException(
          String.format(
              MEDICATION_NOT_FOUND_EXCEPTION_MESSAGE, medicationItemsOnDroneDTO.getMedicationId()));
    }

    final Medication medication = optionalMedication.get();
    final RegisteredMedication registeredMedication =
        RegisteredMedication.builder()
            .medicationName(medication.getMedicationName())
            .weight(medication.getWeight())
            .imageUrl(medication.getImageUrl())
            .code(medication.getCode())
            .build();

    if (Objects.nonNull(drone.getLoadedMedicationItems())) {

      drone.getLoadedMedicationItems().getMedications().add(registeredMedication);
    } else {
      final LoadedMedicationItems loadedMedicationItems =
          LoadedMedicationItems.builder().medications(List.of(registeredMedication)).build();

      drone.setLoadedMedicationItems(loadedMedicationItems);
    }

    drone.setState(Drone.State.LOADING);
    return drone;
  }

  @Override
  public Drone mapReturnDroneToFleetToDroneModel(final UUID serialNumber) {

    final Drone drone =
        droneRepository
            .getDronesBySerialNumber(serialNumber)
            .orElseThrow(
                () ->
                    new NotFoundException(
                        String.format(DRONE_NOT_FOUND_EXCEPTION_MESSAGE, serialNumber)));

    drone.setState(Drone.State.IDLE);
    drone.setCurrentLocation(DRONE_INITIAL_LOCATION);

    return drone;
  }

  @Override
  public Drone mapChargeBatteryToDroneModel(UUID serialNumber) {

    final Drone drone =
        droneRepository
            .getDronesBySerialNumber(serialNumber)
            .orElseThrow(
                () ->
                    new NotFoundException(
                        String.format(DRONE_NOT_FOUND_EXCEPTION_MESSAGE, serialNumber)));
    drone.setBatteryCapacity(BATTERY_FULL_CHARGE);

    return drone;
  }
}
