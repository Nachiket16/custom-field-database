package com.nachiket.customfield.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "attribute")
public class Attribute {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "attribute_id")
  private long attributeId;

//  @ManyToOne(fetch = FetchType.EAGER)
//  @JoinColumn(name = "customer_id")
//  private Customer customer;

  @OneToOne(mappedBy = "attribute", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  private Value values;

  private String name;



}
