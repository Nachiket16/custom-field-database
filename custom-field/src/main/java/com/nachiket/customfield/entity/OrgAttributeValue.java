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
@Table(name = "org_attribute_value")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrgAttributeValue {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  private Long id;
  private Long entityId;
  private Long attributeId;
  private String attributeValue;
}
