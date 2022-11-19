package com.task.drone.dto;

import com.task.medication.json.LoadedMedicationItems;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Builder
@Component
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class DroneLoadedMedicationDTO implements Serializable {

  private LoadedMedicationItems loadedMedicationItems;
}
