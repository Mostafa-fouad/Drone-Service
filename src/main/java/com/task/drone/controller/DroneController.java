package com.task.drone.controller;

import static com.task.drone.controller.DroneController.DRONE_SERVICE_ROOT_PATH;

import com.task.drone.dto.DeliverMedicationItemsDTO;
import com.task.drone.dto.DroneBatteryLevelDTO;
import com.task.drone.dto.DroneDTO;
import com.task.drone.dto.DroneLoadedMedicationDTO;
import com.task.drone.dto.EditDroneDTO;
import com.task.drone.dto.LoadMedicationItemsOnDroneDTO;
import com.task.drone.dto.RegisterDroneDTO;
import com.task.drone.facade.DroneFacade;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import javax.validation.Valid;
import lombok.AllArgsConstructor;
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
@RequestMapping(path = DRONE_SERVICE_ROOT_PATH)
@AllArgsConstructor
public class DroneController {

  private static final String AVAILABLE_DRONES_PATH = "/available-drones";
  private static final String DRONE_BATTERY_LEVEL_PATH = "/battery-level/{serialNumber}";
  private static final String DRONE_LOADED_MEDICATION_ITEMS_PATH =
      "/loaded-medication-items/{serialNumber}";
  private static final String LOAD_MEDICATION_ITEMS_PATH = "/load-medication-items";
  private static final String DELIVER_MEDICATION_ITEMS_PATH = "/deliver-medication-items";
  private static final String RETURN_DRONE_TO_FLEET_PATH = "/back-to-fleet/{serialNumber}";
  private static final String RECHARGE_BATTERY_PATH = "/recharge-battery/{serialNumber}";
  private static final String BATTERIES_LEVEL_AUDIT_PATH = "/batteries-levels-audit";
  private static final String UNREGISTER_DRONE_PATH = "/{serialNumber}";
  static final String DRONE_SERVICE_ROOT_PATH = "/drone-service";

  private final DroneFacade droneFacade;

  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "201",
            description = "The new drone has been registered successfully into the fleet"),
        @ApiResponse(responseCode = "400", description = "Bad request")
      })
  @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Void> registerDrone(
      @Valid @RequestBody final RegisterDroneDTO registerDroneDTO) {

    return ResponseEntity.created(
            URI.create(droneFacade.registerDrone(registerDroneDTO).toString()))
        .build();
  }

  @ApiResponses(
      value = {
        @ApiResponse(responseCode = "204", description = "Drone has been updated successfully"),
        @ApiResponse(responseCode = "404", description = "Drone not found")
      })
  @PatchMapping
  public ResponseEntity<DroneDTO> editDrone(@Valid @RequestBody final EditDroneDTO editDroneDTO) {

    return ResponseEntity.status(HttpStatus.NO_CONTENT).body(droneFacade.editDrone(editDroneDTO));
  }

  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "Drone has been unregistered successfully"),
        @ApiResponse(responseCode = "404", description = "Drone not found")
      })
  @DeleteMapping(path = UNREGISTER_DRONE_PATH)
  public ResponseEntity<Void> unRegisterDrone(@PathVariable final UUID serialNumber) {

    droneFacade.unRegisterDrone(serialNumber);

    return ResponseEntity.ok().build();
  }

  @ApiResponse(
      responseCode = "200",
      description = "List of all registered drones are fetched successfully")
  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<List<DroneDTO>> findAllDrones() {

    return ResponseEntity.ok(droneFacade.getAllDrones());
  }

  @ApiResponse(
      responseCode = "200",
      description = "List of all available drones to be loaded fetched successfully")
  @GetMapping(path = AVAILABLE_DRONES_PATH, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<List<DroneDTO>> findAvailableDrones() {

    return ResponseEntity.ok(droneFacade.getAvailableDrones());
  }

  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "Drone's battery level by its serial number has fetched successfully"),
        @ApiResponse(responseCode = "404", description = "Drone not found")
      })
  @GetMapping(path = DRONE_BATTERY_LEVEL_PATH, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<DroneBatteryLevelDTO> getDroneBatteryLevel(
      @PathVariable final UUID serialNumber) {

    return ResponseEntity.ok(droneFacade.getDroneBatteryLevel(serialNumber));
  }

  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "List of loaded medications have been fetched successfully"),
        @ApiResponse(responseCode = "404", description = "Drone not found")
      })
  @GetMapping(
      path = DRONE_LOADED_MEDICATION_ITEMS_PATH,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<DroneLoadedMedicationDTO> getDroneLoadedMedicationItems(
      @PathVariable final UUID serialNumber) {

    return ResponseEntity.ok(droneFacade.getDroneLoadedMedicationItems(serialNumber));
  }

  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "204",
            description = "List of medication items have loaded successfully"),
        @ApiResponse(responseCode = "400", description = "Bad request"),
        @ApiResponse(responseCode = "404", description = "Drone/Medication Item(s) not found")
      })
  @PatchMapping(path = LOAD_MEDICATION_ITEMS_PATH)
  public ResponseEntity<Void> loadDroneWithMedicationItems(
      @Valid @RequestBody final LoadMedicationItemsOnDroneDTO loadedMedicationItems) {

    droneFacade.loadDroneWithMedicationItems(loadedMedicationItems);

    return ResponseEntity.noContent().build();
  }

  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "204",
            description = "List of medication items have loaded successfully"),
        @ApiResponse(responseCode = "400", description = "Bad request"),
        @ApiResponse(responseCode = "404", description = "Drone/Medication Item(s) not found")
      })
  @PatchMapping(path = DELIVER_MEDICATION_ITEMS_PATH)
  public ResponseEntity<Void> deliverMedicationItems(
      @Valid @RequestBody final DeliverMedicationItemsDTO deliverMedicationItemsDTO) {

    droneFacade.deliverMedicationItems(deliverMedicationItemsDTO);

    return ResponseEntity.noContent().build();
  }

  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "204",
            description = "Drone has returned to the fleet successfully"),
        @ApiResponse(responseCode = "404", description = "Drone not found")
      })
  @PatchMapping(path = RETURN_DRONE_TO_FLEET_PATH)
  public ResponseEntity<Void> returnDroneToFleet(@PathVariable final UUID serialNumber) {

    droneFacade.returnDroneToFleet(serialNumber);

    return ResponseEntity.noContent().build();
  }

  @ApiResponses(
      value = {
        @ApiResponse(responseCode = "204", description = "Drone has recharged successfully"),
        @ApiResponse(responseCode = "404", description = "Drone not found")
      })
  @PatchMapping(path = RECHARGE_BATTERY_PATH)
  public ResponseEntity<Void> rechargeDroneBattery(@PathVariable final UUID serialNumber) {

    droneFacade.rechargeBattery(serialNumber);

    return ResponseEntity.noContent().build();
  }

  @ApiResponse(
      responseCode = "200",
      description = "Drones' battery levels audit have been returned successfully")
  @GetMapping(path = BATTERIES_LEVEL_AUDIT_PATH)
  public ResponseEntity<Map<UUID, List<Integer>>> getBatteryLevelsAudit() {

    return ResponseEntity.ok(droneFacade.getBatteryLevelsAudit());
  }
}
