package com.nachiket.customfield.service;

import com.nachiket.customfield.entity.Customer;
import com.nachiket.customfield.repository.CustomerRepo;
import jakarta.persistence.Tuple;
import jakarta.persistence.TupleElement;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {

  @Autowired
  private CustomerRepo customerRepo;

  public Map<String, Object> getAllUserWithValue() {
    List<Tuple> tuples = customerRepo.findCustom();
    Map<String, Object> aliasMap = new HashMap<>();

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

  public List<Customer> getAllUser() {
    List<Customer> allCustomerAndValue = customerRepo.findAll();
    return allCustomerAndValue;
  }

  public Customer saveCustomer(Customer customer) {
    Customer save = customerRepo.save(customer);
    return save;
  }
}
