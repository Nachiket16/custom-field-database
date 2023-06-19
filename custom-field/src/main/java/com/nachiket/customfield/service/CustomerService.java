package com.nachiket.customfield.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nachiket.customfield.entity.Customer;
import com.nachiket.customfield.model.CustomerModel;
import com.nachiket.customfield.repository.CustomerRepo;
import jakarta.persistence.Tuple;
import jakarta.persistence.TupleElement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.json.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

@Service
public class CustomerService {

  @Autowired
  private CustomerRepo customerRepo;

  public Map<String, Object> getAllUserWithValue() {
    List<Tuple> tuples = customerRepo.findCustom();
    Map<String, Object> aliasMap = new LinkedHashMap<>();

    if (tuples.isEmpty()) {
      System.out.println("The list of tuples is empty.");
      return aliasMap;
    }

    for (Tuple tuple : tuples) {
      for (TupleElement<?> tupleElement : tuple.getElements()) {
        String alias = tupleElement.getAlias();
        Object value = tuple.get(tupleElement);
        aliasMap.put(alias, value);
      }
    }

    return aliasMap;
  }

  public CustomerModel getUserByAttribute(@RequestParam Long id) {
    Customer customer = customerRepo.findById(id)
        .orElseThrow(() -> new RuntimeException("No User found"));
    List<Tuple> tuples = customerRepo.findUserById(customer.getCustomerId());

    CustomerModel model = new CustomerModel();
    model.setCustomerId(customer.getCustomerId());
    model.setName(customer.getName());
    model.setAddress(customer.getAddress());
    model.setMobile(customer.getMobile());
    List<Map<String, Object>> mapList = new ArrayList<>();
    Map<String, Object> aliasMap = new LinkedHashMap<>();
    Map<String, Object> objectMap = new HashMap<>();
    for (Tuple tuple : tuples) {
      for (TupleElement<?> tupleElement : tuple.getElements()) {
        String alias = tupleElement.getAlias();
        Object value = tuple.get(tupleElement);
        aliasMap.put(alias, value);
//        mapList.add(aliasMap);
      }
      try {
        objectMap.put(aliasMap.get("attribute_name").toString(), aliasMap.get("value"));
      }catch (Exception e){
        e.getMessage();
      }

    }
    mapList.add(objectMap);
    model.setAttributeValue(mapList);

    return model;
  }


  public List<Customer> getAllUser() {
    List<Customer> allCustomerAndValue = customerRepo.findAll();
    return allCustomerAndValue;
  }

  public Customer getUser(@RequestParam Long id) {
    Customer customer = customerRepo.findById(id)
        .orElseThrow(() -> new RuntimeException("No User found"));
    return customer;
  }


  public Customer saveCustomer(Customer customer) {
    Customer save = customerRepo.save(customer);
    return save;
  }

  public void createObject(String object){
    ObjectMapper objectMapper = new ObjectMapper();

    Customer customer;
    try {
       customer = objectMapper.readValue(object, Customer.class);
    } catch (JsonProcessingException e) {
      throw new RuntimeException(e);
    }
    System.out.println("Customer : "+customer);
  }

}
