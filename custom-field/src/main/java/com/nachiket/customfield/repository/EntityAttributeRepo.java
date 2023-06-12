package com.nachiket.customfield.repository;

import com.nachiket.customfield.entity.EntityAttributes;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EntityAttributeRepo extends JpaRepository<EntityAttributes, Long> {

  Optional<EntityAttributes> findByAttributeName(String name);

}
