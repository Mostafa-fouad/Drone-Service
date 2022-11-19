package com.task.medication;

import com.task.medication.model.Medication;
import java.util.UUID;

public class IntegrationTestMedicationFactory {

  protected static Medication buildMedication() {

    return Medication.builder()
        .medicationName("MedicationName" + UUID.randomUUID().toString().substring(0, 8))
        .imageUrl("ImageURL")
        .code("MEDICATION_CODE" + UUID.randomUUID().toString().substring(0, 8))
        .weight(10)
        .build();
  }
}
