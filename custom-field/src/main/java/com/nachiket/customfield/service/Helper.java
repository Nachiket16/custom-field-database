package com.nachiket.customfield.service;

import com.nachiket.customfield.entity.EntityAttributes;
import com.nachiket.customfield.entity.OrgAttributeValue;
import com.nachiket.customfield.exceptions.ResourceNotFoundException;
import com.nachiket.customfield.repository.EntityAttributeRepo;
import java.util.Map;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class Helper {

  @Autowired
  private EntityAttributeRepo entityAttributeRepo;

  public boolean isAttributePresent(Map<String, Object> object) {

    for (Map.Entry<String, Object> entry : object.entrySet()) {
      OrgAttributeValue orgAttributeValue = new OrgAttributeValue();
      String key = entry.getKey();
      Object value = entry.getValue();
      if (!key.equalsIgnoreCase("name") && !key.equalsIgnoreCase("website")) {
        EntityAttributes attributeName = entityAttributeRepo.findByAttributeName(key)
            .orElseThrow(()-> new ResourceNotFoundException("Given attribute not found"));
      }
    }

    return true;
  }

}
