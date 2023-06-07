package com.nachiket.customfield.model;

import com.nachiket.customfield.entity.Attribute;
import com.nachiket.customfield.entity.Customer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomerModel {
  private long customerId;
  private String address;
  private String mobile;
  private String name;
//  private Value values;

  private long id;
  private String value;
  private Attribute attribute_id;
  private Customer customer_id;

}
