package com.nachiket.customfield.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "entity_attributes")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EntityAttributes {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String attributeName;
  private String attributeType;
  private String defaultValue;

}
