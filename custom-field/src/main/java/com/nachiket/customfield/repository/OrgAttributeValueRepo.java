package com.nachiket.customfield.repository;

import com.nachiket.customfield.entity.EntityAttributes;
import com.nachiket.customfield.entity.OrgAttributeValue;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrgAttributeValueRepo extends JpaRepository<OrgAttributeValue, Long> {

  Optional<List<OrgAttributeValue>> findByEntityId(Long Id);


}
