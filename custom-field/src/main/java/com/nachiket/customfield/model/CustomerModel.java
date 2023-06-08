package com.nachiket.customfield.model;

import java.util.List;
import java.util.Map;
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
  private List<Map<String,Object>> attributeValue;

}
