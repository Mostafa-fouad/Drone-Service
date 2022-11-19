package com.task.medication.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.UUID;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.URL;

@Builder
@Getter
@Setter
public class EditMedicationItemDTO {

  private static final String MEDICATION_NAME_REGEX = "^[A-Za-z0-9_-]*$";
  private static final String MEDICATION_CODE_REGEX = "^[A-Z0-9_]*$";

  @NotNull
  @JsonProperty("medicationItemId")
  private final UUID medicationItemId;

  @Pattern(regexp = MEDICATION_NAME_REGEX)
  @JsonProperty("medicationName")
  private final String medicationName;

  @JsonProperty("weight")
  private final Integer weight;

  @Pattern(regexp = MEDICATION_CODE_REGEX)
  @JsonProperty("code")
  private final String code;

  @URL
  @JsonProperty("imageUrl")
  private final String imageUrl;
}
