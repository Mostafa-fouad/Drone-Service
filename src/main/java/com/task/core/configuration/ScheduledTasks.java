package com.task.core.configuration;

import static com.task.drone.audit.BatteriesLevelAudit.BATTERIES_LEVELS_AUDIT;

import com.task.drone.model.Drone;
import com.task.drone.repository.DroneRepository;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class ScheduledTasks {

  private final DroneRepository droneRepository;

  @Scheduled(fixedDelay = 2, timeUnit = TimeUnit.SECONDS)
  public void checkBatteryStatusAndUpdateAuditLogTask() {

    droneRepository.findAll().forEach(this::addEventIfBatteryLevelChanged);
  }

  private void addEventIfBatteryLevelChanged(final @NotNull Drone drone) {

    final List<Integer> droneBatteryLevelsAudit =
        BATTERIES_LEVELS_AUDIT.get(drone.getSerialNumber());

    if (Objects.isNull(droneBatteryLevelsAudit)) return;

    final int lastAddedBatteryLevelIndex =
        BATTERIES_LEVELS_AUDIT.get(drone.getSerialNumber()).size() - 1;

    final int lastAddedBatteryLevel = droneBatteryLevelsAudit.get(lastAddedBatteryLevelIndex);

    if (lastAddedBatteryLevel != drone.getBatteryCapacity()) {

      logAndAddEvent(droneBatteryLevelsAudit, drone);
    }
  }

  private void logAndAddEvent(
      final @NotNull List<Integer> droneBatteryLevelsAudit, final @NotNull Drone drone) {

    final UUID droneSerialNumber = drone.getSerialNumber();
    final int batteryCapacity = drone.getBatteryCapacity();
    log.info(
        String.format(
            "Drone with serial number %s has battery level %s",
            droneSerialNumber, batteryCapacity));
    droneBatteryLevelsAudit.add(batteryCapacity);
    log.info(
        String.format(
            "Drone with serial number %s has been updated in the battery audit map",
            droneSerialNumber));
  }
}
