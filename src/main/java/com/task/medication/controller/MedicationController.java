package com.task.medication.controller;

import static com.task.medication.controller.MedicationController.MEDICATION_ROOT_PATH;

import com.task.medication.dto.EditMedicationItemDTO;
import com.task.medication.dto.MedicationDTO;
import com.task.medication.dto.RegisterMedicationDTO;
import com.task.medication.facade.MedicationFacade;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import java.net.URI;
import java.util.List;
import java.util.UUID;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = MEDICATION_ROOT_PATH)
@RequiredArgsConstructor
public class MedicationController {

  static final String UNREGISTER_MEDICATION_ITEM_PATH = "/{id}";
  static final String MEDICATION_ROOT_PATH = "/medication";

  private final MedicationFacade medicationFacade;

  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "201",
            description = "The new medication item has been registered successfully"),
        @ApiResponse(responseCode = "400", description = "Bad request")
      })
  @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Void> registerMedication(
      @Valid @RequestBody final RegisterMedicationDTO registerMedicationDTO) {

    return ResponseEntity.created(
            URI.create(medicationFacade.registerMedication(registerMedicationDTO).toString()))
        .build();
  }

  @ApiResponse(
      responseCode = "200",
      description = "List of all medication items have been returned successfully")
  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<List<MedicationDTO>> findAllMedications() {

    return ResponseEntity.ok(medicationFacade.findAllMedications());
  }

  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "Medication item has been unregistered successfully"),
        @ApiResponse(responseCode = "404", description = "Medication item is not found")
      })
  @DeleteMapping(path = UNREGISTER_MEDICATION_ITEM_PATH)
  public ResponseEntity<Void> unRegisterMedicationItem(@PathVariable final UUID id) {

    medicationFacade.unRegisterMedicationItem(id);

    return ResponseEntity.noContent().build();
  }

  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "204",
            description = "Medication item has been updated successfully"),
        @ApiResponse(responseCode = "404", description = "Medication item is not found")
      })
  @PatchMapping
  public ResponseEntity<MedicationDTO> editMedicationItem(
      @Valid @RequestBody final EditMedicationItemDTO editMedicationItemDTO) {

    return ResponseEntity.status(HttpStatus.NO_CONTENT)
        .body(medicationFacade.editMedicationItem(editMedicationItemDTO));
  }
}
