package com.task.drone.model;

import static com.task.drone.model.Drone.TABLE_NAME;

import com.fasterxml.jackson.annotation.JsonValue;
import com.task.medication.json.LoadedMedicationItems;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import java.time.Instant;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.UpdateTimestamp;

@Setter
@Getter
@ToString
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = TABLE_NAME)
@TypeDef(name = Drone.JSON_B_TYPE, typeClass = JsonBinaryType.class)
public class Drone implements Cloneable {

  private static final String MODEL_COLUMN_NAME = "model";
  private static final String SERIAL_NUMBER_COLUMN_NAME = "serial_number";
  private static final String WEIGHT_LIMIT_COLUMN_NAME = "weight_limit";
  private static final String BATTERY_CAPACITY_COLUMN_NAME = "battery_capacity";
  private static final String STATE_COLUMN_NAME = "state";
  private static final String CURRENT_LOCATION_COLUMN_NAME = "current_location";
  private static final String REGISTERED_DATE_COLUMN_NAME = "registered_date";
  private static final String UPDATED_DATE_COLUMN_NAME = "updated_date";
  private static final String LOADED_MEDICATION_ITEMS_COLUMN_NAME = "loaded_medication_items";

  static final String JSON_B_TYPE = "jsonb";
  static final String TABLE_NAME = "drone";

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column(name = SERIAL_NUMBER_COLUMN_NAME, nullable = false)
  private UUID serialNumber;

  @Enumerated(EnumType.STRING)
  @Column(name = MODEL_COLUMN_NAME, nullable = false)
  private Model model;

  @Column(name = WEIGHT_LIMIT_COLUMN_NAME, nullable = false)
  private Integer weightLimit;

  @Column(name = BATTERY_CAPACITY_COLUMN_NAME, nullable = false)
  private Integer batteryCapacity;

  @Enumerated(EnumType.STRING)
  @Column(name = STATE_COLUMN_NAME, nullable = false)
  private State state;

  @Column(name = CURRENT_LOCATION_COLUMN_NAME, nullable = false)
  private String currentLocation;

  @Type(type = JSON_B_TYPE)
  @Column(name = LOADED_MEDICATION_ITEMS_COLUMN_NAME, columnDefinition = JSON_B_TYPE)
  private LoadedMedicationItems loadedMedicationItems;

  @CreationTimestamp
  @Column(name = REGISTERED_DATE_COLUMN_NAME, nullable = false)
  private Instant registeredDate;

  @UpdateTimestamp
  @Column(name = UPDATED_DATE_COLUMN_NAME, nullable = false)
  private Instant updatedDate;

  @Override
  public Drone clone() {
    try {
      return (Drone) super.clone();
    } catch (CloneNotSupportedException e) {
      throw new AssertionError();
    }
  }

  public enum Model {
    LIGHT_WEIGHT("LIGHT_WEIGHT"),
    MIDDLE_WEIGHT("MIDDLE_WEIGHT"),
    CRUISER_WEIGHT("CRUISER_WEIGHT"),
    HEAVY_WEIGHT("HEAVY_WEIGHT");

    private final String value;

    Model(String value) {
      this.value = value;
    }

    @JsonValue
    public String getValue() {
      return value;
    }

    @Override
    public String toString() {
      return String.valueOf(value);
    }
  }

  public enum State {
    IDLE("IDLE"),
    LOADING("LOADING"),
    LOADED("LOADED"),
    DELIVERING("DELIVERING"),
    DELIVERED("DELIVERED"),
    RETURNING("RETURNING");

    private final String value;

    State(String value) {
      this.value = value;
    }

    @JsonValue
    public String getValue() {
      return value;
    }

    @Override
    public String toString() {
      return String.valueOf(value);
    }
  }
}
