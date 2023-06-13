package com.nachiket.customfield.controller;

import com.nachiket.customfield.entity.Organization;
import com.nachiket.customfield.model.ApiResponseMsg;
import com.nachiket.customfield.service.OrganizationService;
import com.sun.net.httpserver.Authenticator.Success;
import jakarta.persistence.Tuple;
import java.util.List;
import java.util.Map;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/organization")
public class OrganizationController {

  //TODO: Problems- No validation check for the input payload, random data can be added
  @Autowired
  private OrganizationService organizationService;

  @PostMapping("/add")
  public ResponseEntity<Organization> createOrganization(
      @RequestBody Map<String, Object> organiztion) {
    Organization organization = organizationService.createOrganization(organiztion);
    return ResponseEntity.ok(organization);
  }

  //FOR ATTRIBUTE PRESENT OR ELSE RETURN ERROR
  @PostMapping("/addWithAttribute")
  public ResponseEntity<Organization> addOrganizationHavingAttribute(
      @RequestBody Map<String, Object> organiztion) {
    Organization organization = organizationService.createOrganizationWithAttribute(organiztion);
    return ResponseEntity.ok(organization);
  }

  @GetMapping("/get")
  public ResponseEntity<List<String>> getAllOrganization() {
    List<String> allOrganizationWithAttributes = organizationService.getAllOrganizationWithAttributes();
    return ResponseEntity.ok(allOrganizationWithAttributes);
  }

  @GetMapping("/{getById}")
  public ResponseEntity<String> getAllOrganization(@PathVariable("getById") Long getById) {
    String allOrganizationWithAttributes =
        organizationService.getOrganizationWithAttributesById(getById);
    return ResponseEntity.ok(allOrganizationWithAttributes);
  }

  @DeleteMapping("/{deleteById}")
  public ResponseEntity<ApiResponseMsg> deleteOrganizationByID(
      @PathVariable("deleteById") Long deleteById) {
    organizationService.deleteOrganizationById(deleteById);
    ApiResponseMsg msg = ApiResponseMsg
        .builder()
        .msg("User is deleted Successfully")
        .success(true)
        .status(HttpStatus.OK)
        .build();
    return new ResponseEntity(msg, HttpStatus.OK);
  }

}
