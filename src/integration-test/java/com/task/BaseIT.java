package com.task;

import org.jetbrains.annotations.NotNull;
import org.junit.ClassRule;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.utility.DockerImageName;

@SpringBootTest(classes = {DroneApplication.class})
@ContextConfiguration(initializers = BaseIT.Initializer.class)
@AutoConfigureMockMvc
@ActiveProfiles(profiles = {"integration-test", "local"})
public class BaseIT {
  private static final String POSTGRES_IMAGE_NAME = "postgres:latest";
  private static final String DB_NAME = "drone";
  private static final String DB_USERNAME = "drone-user";
  private static final String DB_PASSWORD = "DroneTask";

  @ClassRule
  public static PostgreSQLContainer postgresdb =
      new PostgreSQLContainer<>(DockerImageName.parse(POSTGRES_IMAGE_NAME))
          .withUsername(DB_USERNAME)
          .withPassword(DB_PASSWORD)
          .withDatabaseName(DB_NAME)
          .withReuse(true);

  public static class Initializer
      implements ApplicationContextInitializer<ConfigurableApplicationContext> {

    @Override
    public void initialize(
        @NotNull final ConfigurableApplicationContext configurableApplicationContext) {
      postgresdb.start();

      TestPropertyValues values =
          TestPropertyValues.of("spring.datasource.url=" + postgresdb.getJdbcUrl());
      values.applyTo(configurableApplicationContext);
    }
  }
}
