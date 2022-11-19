package com.task;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@OpenAPIDefinition(
    info =
        @Info(
            title = "Drone Service",
            version = "1.0",
            description = "Drone Service to control all drones in the fleet"))
public class DroneApplication {

  public static void main(String[] args) {
    SpringApplication.run(DroneApplication.class, args);
  }
}
