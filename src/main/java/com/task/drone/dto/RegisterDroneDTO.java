package com.task.drone.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.task.medication.json.LoadedMedicationItems;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class RegisterDroneDTO {

  @NotBlank
  @JsonProperty("model")
  private String model;

  @Max(500)
  @Min(1)
  @NotNull
  @JsonProperty("weightLimit")
  private Integer weightLimit;

  @JsonProperty("loadedMedicationItems")
  private LoadedMedicationItems loadedMedicationItems;
}
