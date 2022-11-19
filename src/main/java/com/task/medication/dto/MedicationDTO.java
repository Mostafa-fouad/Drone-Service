package com.task.medication.dto;

import java.util.UUID;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class MedicationDTO {

  private final UUID id;
  private final String medicationName;
  private final Integer weight;
  private final String code;
  private final String imageUrl;
}
