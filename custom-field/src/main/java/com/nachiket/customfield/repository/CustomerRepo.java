package com.nachiket.customfield.repository;

import com.nachiket.customfield.entity.Customer;
import jakarta.persistence.Tuple;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepo extends JpaRepository<Customer, Long> {

  @Query(
  value =
      "SELECT c.customer_id,c.address,c.mobile,c.name,a.name,v.value FROM customer c INNER JOIN attribute_value v ON c.customer_id = v.customer_id INNER JOIN attribute a ON v.attribute_id = a.attribute_id",
  nativeQuery = true)
  List<Tuple> findCustom();

}
