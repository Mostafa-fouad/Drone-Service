package com.task.drone.facade.impl;

import com.task.drone.dto.DeliverMedicationItemsDTO;
import com.task.drone.dto.DroneBatteryLevelDTO;
import com.task.drone.dto.DroneDTO;
import com.task.drone.dto.DroneLoadedMedicationDTO;
import com.task.drone.dto.EditDroneDTO;
import com.task.drone.dto.LoadMedicationItemsOnDroneDTO;
import com.task.drone.dto.RegisterDroneDTO;
import com.task.drone.facade.DroneFacade;
import com.task.drone.mapper.DroneMapper;
import com.task.drone.model.Drone;
import com.task.drone.service.DroneService;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DroneFacadeImpl implements DroneFacade {

  private final DroneService droneService;
  private final DroneMapper droneMapper;

  @Override
  public UUID registerDrone(final RegisterDroneDTO registerDroneDTO) {
    return droneService.registerDrone(
        droneMapper.mapRegisterDroneDtoToDroneModel(registerDroneDTO));
  }

  @Override
  public DroneDTO editDrone(final EditDroneDTO editDroneDTO) {

    final Drone drone =
        droneService.saveDrone(droneMapper.mapEditDroneDtoToDroneModel(editDroneDTO));

    return DroneDTO.builder()
        .currentLocation(drone.getCurrentLocation())
        .batteryCapacity(drone.getBatteryCapacity())
        .state(drone.getState().getValue())
        .weightLimit(drone.getWeightLimit())
        .loadedMedicationItems(drone.getLoadedMedicationItems())
        .serialNumber(drone.getSerialNumber())
        .build();
  }

  @Override
  public void unRegisterDrone(final UUID serialNumber) {
    droneService.unRegisterDrone(serialNumber);
  }

  @Override
  public List<DroneDTO> getAvailableDrones() {
    return droneService.getAvailableDrones().stream()
        .map(droneMapper::mapDroneModelToDroneDto)
        .collect(Collectors.toList());
  }

  @Override
  public List<DroneDTO> getAllDrones() {

    return droneService.getAllDrones().stream()
        .map(droneMapper::mapDroneModelToDroneDto)
        .collect(Collectors.toList());
  }

  @Override
  public DroneBatteryLevelDTO getDroneBatteryLevel(final UUID serialNumber) {
    return droneMapper.mapDroneModelToDroneBatteryLevelDto(
        droneService.getDroneBatteryLevel(serialNumber));
  }

  @Override
  public DroneLoadedMedicationDTO getDroneLoadedMedicationItems(final UUID serialNumber) {
    return droneMapper.mapDroneModelToDroneLoadedMedicationDto(
        droneService.getDroneLoadedMedicationItems(serialNumber));
  }

  @Override
  public void loadDroneWithMedicationItems(
      final LoadMedicationItemsOnDroneDTO loadedMedicationItems) {
    droneService.loadDroneWithMedicationItems(
        droneMapper.mapLoadedMedicationItemsToDroneModel(loadedMedicationItems));
  }

  @Override
  public void deliverMedicationItems(final DeliverMedicationItemsDTO deliverMedicationItemsDTO) {
    droneService.deliverMedicationItems(
        droneMapper.mapDeliverMedicationItemsDtoToDroneModel(deliverMedicationItemsDTO));
  }

  @Override
  public void returnDroneToFleet(final UUID serialNumber) {
    droneService.saveDrone(droneMapper.mapReturnDroneToFleetToDroneModel(serialNumber));
  }

  @Override
  public void rechargeBattery(final UUID serialNumber) {
    droneService.saveDrone(droneMapper.mapChargeBatteryToDroneModel(serialNumber));
  }

  @Override
  public Map<UUID, List<Integer>> getBatteryLevelsAudit() {
    return droneService.getBatteryLevelsAudit();
  }
}
