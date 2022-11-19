package com.task.medication.json;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RegisteredMedication implements Serializable {

  private String medicationName;
  private Integer weight;
  private String code;
  private String imageUrl;
}
