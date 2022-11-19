package com.task.drone.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.UUID;
import javax.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class LoadMedicationItemsOnDroneDTO {

  @NotNull
  @JsonProperty("droneSerialNumber")
  private UUID droneSerialNumber;

  @NotNull
  @JsonProperty("medicationSerialNumber")
  private UUID medicationId;
}
