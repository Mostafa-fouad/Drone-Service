package com.task.medication.json;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Singular;
import org.springframework.stereotype.Component;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Component
public class LoadedMedicationItems implements Serializable {

  @Singular("medications")
  private List<RegisteredMedication> medications = new ArrayList<>();
}
