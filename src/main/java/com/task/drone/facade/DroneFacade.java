package com.task.drone.facade;

import com.task.drone.dto.DeliverMedicationItemsDTO;
import com.task.drone.dto.DroneBatteryLevelDTO;
import com.task.drone.dto.DroneDTO;
import com.task.drone.dto.DroneLoadedMedicationDTO;
import com.task.drone.dto.EditDroneDTO;
import com.task.drone.dto.LoadMedicationItemsOnDroneDTO;
import com.task.drone.dto.RegisterDroneDTO;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface DroneFacade {

  UUID registerDrone(RegisterDroneDTO registerDroneDTO);

  DroneDTO editDrone(EditDroneDTO editDroneDTO);

  void unRegisterDrone(UUID serialNumber);

  List<DroneDTO> getAvailableDrones();

  List<DroneDTO> getAllDrones();

  DroneBatteryLevelDTO getDroneBatteryLevel(UUID serialNumber);

  DroneLoadedMedicationDTO getDroneLoadedMedicationItems(UUID serialNumber);

  void loadDroneWithMedicationItems(LoadMedicationItemsOnDroneDTO loadedMedicationItems);

  void deliverMedicationItems(DeliverMedicationItemsDTO deliverMedicationItemsDTO);

  void returnDroneToFleet(UUID serialNumber);

  void rechargeBattery(UUID serialNumber);

  Map<UUID, List<Integer>> getBatteryLevelsAudit();
}
