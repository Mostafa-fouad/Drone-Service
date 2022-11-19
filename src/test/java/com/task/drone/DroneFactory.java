package com.task.drone;

import com.task.drone.model.Drone;
import com.task.medication.json.LoadedMedicationItems;
import java.time.Instant;
import java.util.UUID;

public class DroneFactory {

  protected static Drone buildDrone() {

    return Drone.builder()
        .serialNumber(UUID.randomUUID())
        .state(Drone.State.IDLE)
        .currentLocation("Fleet")
        .batteryCapacity(100)
        .model(Drone.Model.HEAVY_WEIGHT)
        .loadedMedicationItems(LoadedMedicationItems.builder().build())
        .weightLimit(500)
        .registeredDate(Instant.now())
        .updatedDate(Instant.now())
        .build();
  }
}
