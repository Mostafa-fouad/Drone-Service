package com.task.medication.model;

import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = Medication.TABLE_NAME)
public class Medication {

  private static final String MEDICATION_NAME_COLUMN_NAME = "medication_name";
  private static final String WEIGHT_COLUMN_NAME = "weight";
  private static final String CODE_COLUMN_NAME = "code";
  private static final String IMAGE_URL_COLUMN_NAME = "image_url";
  protected static final String TABLE_NAME = "medication";

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private UUID id;

  @Column(name = MEDICATION_NAME_COLUMN_NAME, nullable = false)
  private String medicationName;

  @Column(name = WEIGHT_COLUMN_NAME, nullable = false)
  private Integer weight;

  @Column(name = CODE_COLUMN_NAME, nullable = false)
  private String code;

  @Column(name = IMAGE_URL_COLUMN_NAME, nullable = false)
  private String imageUrl;
}
