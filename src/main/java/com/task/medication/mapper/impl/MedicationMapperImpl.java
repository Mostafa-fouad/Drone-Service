package com.task.medication.mapper.impl;

import com.task.core.exception.NotFoundException;
import com.task.medication.dto.EditMedicationItemDTO;
import com.task.medication.dto.MedicationDTO;
import com.task.medication.mapper.MedicationMapper;
import com.task.medication.model.Medication;
import com.task.medication.repository.MedicationRepository;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MedicationMapperImpl implements MedicationMapper {

  private final MedicationRepository medicationRepository;

  @Override
  public MedicationDTO mapMedicationModelToMedicationDto(final Medication medication) {

    return MedicationDTO.builder()
        .id(medication.getId())
        .medicationName(medication.getMedicationName())
        .weight(medication.getWeight())
        .imageUrl(medication.getImageUrl())
        .code(medication.getCode())
        .build();
  }

  @Override
  public Medication mapEditMedicationItemDtoToMedicationModel(
      final EditMedicationItemDTO editMedicationItemDTO) {

    final Medication medication =
        medicationRepository
            .findById(editMedicationItemDTO.getMedicationItemId())
            .orElseThrow(
                () ->
                    new NotFoundException(
                        String.format(
                            "Medication Item with id %s is not found",
                            editMedicationItemDTO.getMedicationItemId())));

    medication.setMedicationName(
        Objects.nonNull(editMedicationItemDTO.getMedicationName())
            ? editMedicationItemDTO.getMedicationName()
            : medication.getMedicationName());
    medication.setCode(
        Objects.nonNull(editMedicationItemDTO.getCode())
            ? editMedicationItemDTO.getCode()
            : medication.getCode());
    medication.setWeight(
        Objects.nonNull(editMedicationItemDTO.getWeight())
            ? editMedicationItemDTO.getWeight()
            : medication.getWeight());
    medication.setImageUrl(
        Objects.nonNull(editMedicationItemDTO.getImageUrl())
            ? editMedicationItemDTO.getImageUrl()
            : medication.getImageUrl());

    return medication;
  }
}
