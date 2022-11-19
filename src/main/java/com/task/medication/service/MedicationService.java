package com.task.medication.service;

import com.task.medication.model.Medication;
import java.util.List;
import java.util.UUID;

public interface MedicationService {

  UUID registerMedication(Medication medication);

  List<Medication> findAllMedications();

  void unRegisterMedicationItem(UUID id);

  Medication editMedicationItem(Medication medication);
}
