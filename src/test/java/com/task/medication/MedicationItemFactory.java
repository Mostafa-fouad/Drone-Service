package com.task.medication;

import com.task.medication.model.Medication;
import java.util.UUID;

public class MedicationItemFactory {

  protected static Medication buildMedication() {

    return Medication.builder()
        .id(UUID.randomUUID())
        .medicationName("MedicationItemName")
        .code("MEDICATION_ITEM")
        .weight(10)
        .imageUrl("MedicationItemImageURL")
        .build();
  }
}
