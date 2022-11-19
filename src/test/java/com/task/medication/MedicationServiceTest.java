package com.task.medication;

import static com.task.medication.MedicationItemFactory.buildMedication;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import com.task.core.exception.NotFoundException;
import com.task.medication.model.Medication;
import com.task.medication.repository.MedicationRepository;
import com.task.medication.service.MedicationService;
import com.task.medication.service.impl.MedicationServiceImpl;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@Import({MedicationServiceImpl.class})
public class MedicationServiceTest {

  @Autowired private MedicationService medicationService;

  @MockBean private MedicationRepository medicationRepository;

  @Test
  void registerMedicationItem_withValidDto_medicationItemRegisteredSuccessfully() {

    // GIVEN
    final Medication medication = buildMedication();
    when(medicationRepository.save(medication)).thenReturn(medication);

    // WHEN
    final UUID id = medicationService.registerMedication(medication);

    // THEN
    assertThat(id).isEqualTo(medication.getId());
  }

  @Test
  void unRegisterMedicationItem_medicationItemExists_medicationItemUnRegisteredSuccessfully() {

    // GIVEN
    final Medication medication = buildMedication();
    when(medicationRepository.save(medication)).thenReturn(medication);

    // WHEN
    doNothing().when(medicationRepository).deleteById(medication.getId());

    // THEN medication item unregistered successfully
  }

  @Test
  void unRegisterMedicationItem_medicationItemDoesNotExists_NotFoundExceptionThrown() {

    // GIVEN
    final Medication medication = buildMedication();
    when(medicationRepository.getById(medication.getId()))
        .thenThrow(
            new NotFoundException(
                String.format("Medication item with id %s is not found", medication.getId())));

    // WHEN and THEN
    final String exceptionMessage =
        assertThrows(
                NotFoundException.class,
                () -> medicationService.unRegisterMedicationItem(medication.getId()))
            .getMessage();

    assertThat(exceptionMessage)
        .isEqualTo(String.format("Medication item with id %s is not found", medication.getId()));
  }
}
