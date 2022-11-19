package com.task.drone.dto;

import com.task.medication.json.LoadedMedicationItems;
import java.io.Serializable;
import java.util.UUID;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class DroneDTO implements Serializable {

  private UUID serialNumber;
  private String model;
  private Integer weightLimit;
  private Integer batteryCapacity;
  private String state;
  private String currentLocation;
  private LoadedMedicationItems loadedMedicationItems;
}
