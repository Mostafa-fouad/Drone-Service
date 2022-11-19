package com.task.drone.service;

import com.task.drone.model.Drone;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface DroneService {

  UUID registerDrone(Drone drone);

  List<Drone> getAvailableDrones();

  void unRegisterDrone(UUID serialNumber);

  List<Drone> getAllDrones();

  Drone getDroneBatteryLevel(UUID serialNumber);

  Drone getDroneLoadedMedicationItems(UUID serialNumber);

  void loadDroneWithMedicationItems(Drone drone);

  void deliverMedicationItems(Drone drone);

  Drone saveDrone(Drone drone);

  Map<UUID, List<Integer>> getBatteryLevelsAudit();
}
