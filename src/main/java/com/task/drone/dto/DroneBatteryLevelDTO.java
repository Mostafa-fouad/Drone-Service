package com.task.drone.dto;

import java.util.UUID;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class DroneBatteryLevelDTO {

  private UUID serialNumber;
  private Integer batteryLevel;
}
