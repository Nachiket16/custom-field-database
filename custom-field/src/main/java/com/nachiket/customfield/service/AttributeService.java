package com.nachiket.customfield.service;

import com.nachiket.customfield.entity.Attribute;
import com.nachiket.customfield.repository.AttributeRepo;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AttributeService {

  @Autowired
  private AttributeRepo attributeRepo;

  public List<Attribute> getAllAttribute() {
    List<Attribute> all = attributeRepo.findAll();
    System.out.println(all);
    return all;
  }

  public Attribute saveAttribute(Attribute attribute) {
    Attribute save = attributeRepo.save(attribute);
    return save;
  }

}
