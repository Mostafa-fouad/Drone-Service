package com.task.medication;

import static com.task.medication.IntegrationTestMedicationFactory.buildMedication;
import static org.assertj.core.api.Assertions.assertThat;

import com.task.BaseIT;
import com.task.medication.model.Medication;
import com.task.medication.repository.MedicationRepository;
import com.task.medication.service.MedicationService;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class MedicationServiceIT extends BaseIT {

  @Autowired private MedicationRepository medicationRepository;
  @Autowired private MedicationService medicationService;

  @AfterEach
  void cleanDB() {

    medicationRepository.deleteAll();
  }

  @Test
  void registerMedicationItem_withValidModel_medicationRegisteredSuccessfully() {

    // GIVEN
    // WHEN
    final UUID id = medicationService.registerMedication(buildMedication());

    // THEN
    assertThat(id).isEqualTo(medicationRepository.findAll().get(0).getId());
  }

  @Test
  void
      givenMedicationItemsAreRegistered_whenListAllMedicationItems_allMedicationItemsAreListedSuccessfully() {

    // GIVEN
    final int expectedMedicationItemsAmount = 5;
    registerMedicationItems();

    // WHEN
    final List<Medication> medicationItems = medicationService.findAllMedications();

    // THEN
    assertThat(medicationItems).isNotEmpty();
    assertThat(medicationItems).hasSize(expectedMedicationItemsAmount);
  }

  @Test
  void
      givenMedicationItemsAreRegistered_unRegisterMedicationItem_MedicationItemUnRegisteredSuccessfully() {

    // GIVEN
    final UUID id = medicationService.registerMedication(buildMedication());

    // WHEN
    medicationService.unRegisterMedicationItem(id);
    final Optional<Medication> medication = medicationRepository.findById(id);

    // THEN
    assertThat(medication).isEmpty();
  }

  private void registerMedicationItems() {

    for (int i = 0; i < 5; i++) {
      medicationService.registerMedication(buildMedication());
    }
  }
}
