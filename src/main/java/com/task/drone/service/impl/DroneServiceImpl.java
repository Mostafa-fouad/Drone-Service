package com.task.drone.service.impl;

import static com.task.drone.audit.BatteriesLevelAudit.BATTERIES_LEVELS_AUDIT;

import com.task.core.exception.NotFoundException;
import com.task.core.exception.ValidationException;
import com.task.drone.model.Drone;
import com.task.drone.model.Drone.State;
import com.task.drone.repository.DroneRepository;
import com.task.drone.service.DroneService;
import com.task.medication.json.RegisteredMedication;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DroneServiceImpl implements DroneService {

  private static final String DRONE_NOT_FOUND_EXCEPTION_MESSAGE =
      "Drone with serial number %s not found.";
  private static final int FLEET_CAPACITY = 10;
  private static final int TRIP_BATTERY_CONSUMPTION = 25;
  private static final int MINIMUM_BATTERY_LEVEL = 25;
  private final DroneRepository droneRepository;

  @Override
  @Transactional
  public UUID registerDrone(final Drone drone) {

    if (droneRepository.count() == FLEET_CAPACITY) {
      throw new ValidationException("Drone's fleet is full");
    }

    BATTERIES_LEVELS_AUDIT.put(drone.getSerialNumber(), new ArrayList<>(Arrays.asList(100)));
    return saveDrone(drone).getSerialNumber();
  }

  @Override
  @Transactional
  public List<Drone> getAvailableDrones() {
    return droneRepository.getDronesByStateIs(State.IDLE);
  }

  @Override
  @Transactional
  public void unRegisterDrone(final UUID serialNumber) {

    validateDroneExistence(serialNumber);

    droneRepository.deleteDroneBySerialNumber(serialNumber);
  }

  @Override
  @Transactional
  public List<Drone> getAllDrones() {
    return droneRepository.findAll();
  }

  @Override
  @Transactional
  public Drone getDroneBatteryLevel(final UUID serialNumber) {
    return droneRepository.getDronesBySerialNumber(serialNumber).orElseThrow();
  }

  @Override
  @Transactional
  public Drone getDroneLoadedMedicationItems(final UUID serialNumber) {
    return droneRepository
        .getDronesBySerialNumber(serialNumber)
        .orElseThrow(
            () ->
                new NotFoundException(
                    String.format(DRONE_NOT_FOUND_EXCEPTION_MESSAGE, serialNumber)));
  }

  @Override
  @Transactional
  public void loadDroneWithMedicationItems(final Drone drone) {

    validateDroneWeight(drone);
    validateDroneBatteryLevelAndUpdate(drone);

    saveDrone(drone);

    drone.setState(State.LOADED);

    saveDrone(drone);
  }

  @Override
  @Transactional
  public void deliverMedicationItems(final Drone drone) {

    validateDroneBatteryLevelAndUpdate(drone);
    validateDroneState(drone);

    saveDrone(drone);
    drone.setBatteryCapacity(drone.getBatteryCapacity() - TRIP_BATTERY_CONSUMPTION);
    saveDrone(drone);
  }

  @Override
  public Drone saveDrone(final Drone drone) {

    return droneRepository.save(drone);
  }

  @Override
  public Map<UUID, List<Integer>> getBatteryLevelsAudit() {

    return BATTERIES_LEVELS_AUDIT;
  }

  private void validateDroneExistence(final UUID serialNumber) {

    droneRepository
        .getDronesBySerialNumber(serialNumber)
        .orElseThrow(
            () ->
                new NotFoundException(
                    String.format(DRONE_NOT_FOUND_EXCEPTION_MESSAGE, serialNumber)));
  }

  private void validateDroneWeight(final Drone drone) {

    final List<RegisteredMedication> loadedMedicationItems =
        drone.getLoadedMedicationItems().getMedications();

    final int totalMedicationItemsWeight =
        loadedMedicationItems.stream().map(RegisteredMedication::getWeight).reduce(0, Integer::sum);

    if (totalMedicationItemsWeight > drone.getWeightLimit()) {

      throw new ValidationException(
          "Medication items are too heavy to be loaded, try to load lighter items.");
    }
  }

  private void validateDroneBatteryLevelAndUpdate(final Drone drone) {

    if (State.LOADING.equals(drone.getState())
        && drone.getBatteryCapacity() < MINIMUM_BATTERY_LEVEL) {

      throw new ValidationException(
          "drone's battery is low, Can't be Loaded with medication items.");
    }
  }

  private void validateDroneState(final Drone drone) {

    final Drone droneInDB =
        droneRepository
            .getDronesBySerialNumber(drone.getSerialNumber())
            .orElseThrow(
                () ->
                    new NotFoundException(
                        String.format(DRONE_NOT_FOUND_EXCEPTION_MESSAGE, drone.getSerialNumber())));

    final State currentDroneState = droneInDB.getState();

    if (currentDroneState.equals(State.DELIVERING)) {
      throw new ValidationException(
          "Drone is delivering another order, please wait until it returns back to the fleet.");
    }
  }
}
