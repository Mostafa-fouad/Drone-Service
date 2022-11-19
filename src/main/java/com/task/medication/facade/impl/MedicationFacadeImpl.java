package com.task.medication.facade.impl;

import com.task.medication.dto.EditMedicationItemDTO;
import com.task.medication.dto.MedicationDTO;
import com.task.medication.dto.RegisterMedicationDTO;
import com.task.medication.facade.MedicationFacade;
import com.task.medication.mapper.MedicationMapper;
import com.task.medication.model.Medication;
import com.task.medication.service.MedicationService;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MedicationFacadeImpl implements MedicationFacade {

  private final MedicationService medicationService;
  private final MedicationMapper medicationMapper;

  @Override
  public UUID registerMedication(final RegisterMedicationDTO registerMedicationDTO) {

    return medicationService.registerMedication(
        Medication.builder()
            .medicationName(registerMedicationDTO.getMedicationName())
            .code(registerMedicationDTO.getCode())
            .weight(registerMedicationDTO.getWeight())
            .imageUrl(registerMedicationDTO.getImageUrl())
            .build());
  }

  @Override
  public List<MedicationDTO> findAllMedications() {

    return medicationService.findAllMedications().stream()
        .map(medicationMapper::mapMedicationModelToMedicationDto)
        .collect(Collectors.toList());
  }

  @Override
  public void unRegisterMedicationItem(UUID id) {
    medicationService.unRegisterMedicationItem(id);
  }

  @Override
  public MedicationDTO editMedicationItem(final EditMedicationItemDTO editMedicationItemDTO) {

    return medicationMapper.mapMedicationModelToMedicationDto(
        medicationService.editMedicationItem(
            medicationMapper.mapEditMedicationItemDtoToMedicationModel(editMedicationItemDTO)));
  }
}
