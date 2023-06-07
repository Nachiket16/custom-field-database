package com.nachiket.customfield.controller;

import com.nachiket.customfield.entity.Attribute;
import com.nachiket.customfield.service.AttributeService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/attribute/")
public class AttributeController {

  @Autowired
  private AttributeService attributeService;

  @GetMapping
  public ResponseEntity<List<Attribute>> getAllUSer() {
    List<Attribute> allAttribute = attributeService.getAllAttribute();
    return ResponseEntity.ok(allAttribute);
  }


  @PostMapping
  public ResponseEntity<Attribute> createUser(@RequestBody Attribute attribute) {
    Attribute attribute1 = attributeService.saveAttribute(attribute);
    return ResponseEntity.status(HttpStatus.CREATED).body(attribute1);
  }


}
