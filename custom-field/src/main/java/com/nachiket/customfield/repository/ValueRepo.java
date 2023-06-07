package com.nachiket.customfield.repository;

import com.nachiket.customfield.entity.Value;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ValueRepo extends JpaRepository<Value, Long> {

}
