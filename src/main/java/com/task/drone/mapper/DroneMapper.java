package com.task.drone.mapper;

import com.task.drone.dto.DeliverMedicationItemsDTO;
import com.task.drone.dto.DroneBatteryLevelDTO;
import com.task.drone.dto.DroneDTO;
import com.task.drone.dto.DroneLoadedMedicationDTO;
import com.task.drone.dto.EditDroneDTO;
import com.task.drone.dto.LoadMedicationItemsOnDroneDTO;
import com.task.drone.dto.RegisterDroneDTO;
import com.task.drone.model.Drone;
import java.util.UUID;

public interface DroneMapper {

  Drone mapRegisterDroneDtoToDroneModel(RegisterDroneDTO registerDroneDTO);

  DroneDTO mapDroneModelToDroneDto(Drone droneModel);

  Drone mapEditDroneDtoToDroneModel(final EditDroneDTO editDroneDTO);

  DroneBatteryLevelDTO mapDroneModelToDroneBatteryLevelDto(Drone droneModel);

  DroneLoadedMedicationDTO mapDroneModelToDroneLoadedMedicationDto(Drone droneModel);

  Drone mapDeliverMedicationItemsDtoToDroneModel(
      DeliverMedicationItemsDTO deliverMedicationItemsDTO);

  Drone mapLoadedMedicationItemsToDroneModel(LoadMedicationItemsOnDroneDTO loadedMedicationItems);

  Drone mapReturnDroneToFleetToDroneModel(UUID serialNumber);

  Drone mapChargeBatteryToDroneModel(UUID serialNumber);
}
