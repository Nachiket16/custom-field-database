package com.nachiket.customfield.repository;

import com.nachiket.customfield.entity.OrgAttributeValue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrgAttributeValueRepo extends JpaRepository<OrgAttributeValue, Long> {

}
