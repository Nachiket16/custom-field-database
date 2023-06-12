package com.nachiket.customfield.repository;

import com.nachiket.customfield.entity.Organization;
import jakarta.persistence.Tuple;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface OrganizationRepo extends JpaRepository<Organization, Long> {

//  List<Tuple> getAllOrgAttributeWithValue();

  @Query(
      value =
          "SELECT org.name,org.website,ea.attribute_name,oav.attribute_value "
              + "FROM organizations as org "
              + "LEFT JOIN org_attribute_value as oav ON org.id = oav.entity_id "
              + "LEFT JOIN entity_attributes as ea ON ea.id = oav.attribute_id where org.id = :id",
      nativeQuery = true)
  List<Tuple> getOrgAttributeWithValue(@Param("id") Long id);

}
