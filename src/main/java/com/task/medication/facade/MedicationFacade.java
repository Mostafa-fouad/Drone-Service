package com.task.medication.facade;

import com.task.medication.dto.EditMedicationItemDTO;
import com.task.medication.dto.MedicationDTO;
import com.task.medication.dto.RegisterMedicationDTO;
import java.util.List;
import java.util.UUID;

public interface MedicationFacade {

  UUID registerMedication(RegisterMedicationDTO registerMedicationDTO);

  List<MedicationDTO> findAllMedications();

  void unRegisterMedicationItem(UUID id);

  MedicationDTO editMedicationItem(EditMedicationItemDTO editMedicationItemDTO);
}
