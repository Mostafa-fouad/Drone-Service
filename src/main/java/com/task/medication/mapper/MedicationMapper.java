package com.task.medication.mapper;

import com.task.medication.dto.EditMedicationItemDTO;
import com.task.medication.dto.MedicationDTO;
import com.task.medication.model.Medication;

public interface MedicationMapper {

  MedicationDTO mapMedicationModelToMedicationDto(Medication medication);

  Medication mapEditMedicationItemDtoToMedicationModel(EditMedicationItemDTO medication);
}
