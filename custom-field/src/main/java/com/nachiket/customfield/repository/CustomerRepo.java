package com.nachiket.customfield.repository;

import com.nachiket.customfield.entity.Customer;
import com.nachiket.customfield.model.CustomerModel;
import jakarta.persistence.Tuple;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepo extends JpaRepository<Customer, Long> {

  @Query(
  value =
      "SELECT c.customer_id,c.address,c.mobile,c.name,a.attribute_name,v.value FROM customer c LEFT JOIN attribute_value v ON c.customer_id = v.customer_id LEFT JOIN attribute a ON v.attribute_id = a.attribute_id",
  nativeQuery = true)
  List<Tuple> findCustom();
  @Query(
      value =
          "SELECT a.attribute_name,v.value FROM customer "
              + "c LEFT JOIN attribute_value v ON c.customer_id = v.customer_id LEFT JOIN "
              + "attribute a ON v.attribute_id = a.attribute_id where c.customer_id = :id",
      nativeQuery = true)
  List<Tuple> findUserById(@Param("id") Long id);
 }
