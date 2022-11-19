package com.task.drone.dto;

import java.util.UUID;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class DeliverMedicationItemsDTO {

  private UUID droneSerialNumber;
  private String address;
}
