package com.task.medication.service.impl;

import com.task.core.exception.NotFoundException;
import com.task.medication.model.Medication;
import com.task.medication.repository.MedicationRepository;
import com.task.medication.service.MedicationService;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MedicationServiceImpl implements MedicationService {

  private final MedicationRepository medicationRepository;

  @Override
  @Transactional
  public UUID registerMedication(final Medication medication) {

    return medicationRepository.save(medication).getId();
  }

  @Override
  @Transactional
  public List<Medication> findAllMedications() {
    return medicationRepository.findAll();
  }

  @Override
  @Transactional
  public void unRegisterMedicationItem(final UUID id) {

    validateMedicationItemExistence(id);

    medicationRepository.deleteById(id);
  }

  @Override
  public Medication editMedicationItem(final Medication medication) {

    return medicationRepository.save(medication);
  }

  private void validateMedicationItemExistence(final UUID id) {

    medicationRepository
        .findById(id)
        .orElseThrow(
            () ->
                new NotFoundException(
                    String.format("Medication item with id %s is not found", id)));
  }
}
