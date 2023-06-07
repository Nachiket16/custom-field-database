package com.nachiket.customfield.controller;

import com.nachiket.customfield.entity.Customer;
import com.nachiket.customfield.model.CustomerModel;
import com.nachiket.customfield.service.CustomerService;
import jakarta.persistence.Tuple;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class CustomerController {

  @Autowired
  private CustomerService customerService;

  @GetMapping("/hello")
  public String hello(){
    return "Hello from the controller";
  }

  @GetMapping("/value")
  public ResponseEntity<Map<String, Object>> getAllUserWithValue() {
    Map<String, Object> allUser = customerService.getAllUserWithValue();
    return ResponseEntity.ok(allUser);
  }

  @GetMapping
  public ResponseEntity<List<Customer>> getAllUSer() {
    List<Customer> allUser = customerService.getAllUser();
    return ResponseEntity.ok(allUser);
  }


  @PostMapping
  public ResponseEntity<Customer> createUser(@RequestBody Customer customer) {
    Customer customer1 = customerService.saveCustomer(customer);
    return ResponseEntity.status(HttpStatus.CREATED).body(customer1);
  }


}
