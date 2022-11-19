package com.task.drone.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.UUID;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class EditDroneDTO {

  @NotNull
  @JsonProperty("droneSerialNumber")
  private UUID droneSerialNumber;

  @JsonProperty("model")
  private String model;

  @Max(500)
  @Min(1)
  @JsonProperty("weightLimit")
  private Integer weightLimit;
}
