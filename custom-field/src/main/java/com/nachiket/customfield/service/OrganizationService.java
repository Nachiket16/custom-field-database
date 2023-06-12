package com.nachiket.customfield.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.nachiket.customfield.entity.EntityAttributes;
import com.nachiket.customfield.entity.OrgAttributeValue;
import com.nachiket.customfield.entity.Organization;
import com.nachiket.customfield.repository.EntityAttributeRepo;
import com.nachiket.customfield.repository.OrgAttributeValueRepo;
import com.nachiket.customfield.repository.OrganizationRepo;
import jakarta.persistence.Tuple;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrganizationService {

  @Autowired
  private OrganizationRepo organizationRepo;
  @Autowired
  private EntityAttributeRepo entityAttributeRepo;
  @Autowired
  private OrgAttributeValueRepo orgAttributeValueRepo;

  public Organization createOrganization(Map<String, Object> object) {
    //Convert JSON OBJECT INTO THE OBJ OF THE CLASS ORGANIZATION
    Organization organization = new Organization();
    EntityAttributes attribute = new EntityAttributes();

    organization.setName(object.get("name").toString());
    organization.setWebsite(object.get("website").toString());
    Organization entity = organizationRepo.save(organization);
    //TODO : Now check for the attributes and the value
    // If the attributes are present then add the Id ref into the value
    // if not then add a new attribute into the table and ref into the value table
    Object attributeType = object.get("attributeType");

    for (Map.Entry<String, Object> entry : object.entrySet()) {
      OrgAttributeValue orgAttributeValue = new OrgAttributeValue();
      String key = entry.getKey();
      Object value = entry.getValue();
      if (!key.equalsIgnoreCase("name") && !key.equalsIgnoreCase("website")) {
        //Check if attribute is present or not ?
        Optional<EntityAttributes> attributeName = entityAttributeRepo.findByAttributeName(key);
        if (attributeName.isPresent()) {
          //If attribute is present take the ID and store as the ref into the value table
          Long attributeId = attributeName.get().getId();
          orgAttributeValue.setAttributeId(attributeId);
          orgAttributeValue.setEntityId(entity.getId());
          orgAttributeValue.setAttributeValue(value.toString());
        } else {
          // create new attribute  and then store
          attribute.setAttributeName(key);
          attribute.setDefaultValue(null);
          //TODO: Need more efficient method -> VarcharJdbcType(?)

          if (attributeType == null) {
            attribute.setDefaultValue("Varchar(255)");
          } else {
            attribute.setAttributeType(attributeType.toString());
          }
          EntityAttributes entityAttributes = entityAttributeRepo.save(attribute);
          orgAttributeValue.setAttributeId(entityAttributes.getId());
          orgAttributeValue.setEntityId(entity.getId());
          orgAttributeValue.setAttributeValue(value.toString());
        }

        OrgAttributeValue entityOrgAttributeValue = orgAttributeValueRepo.save(orgAttributeValue);
      }
    }
    return entity;
  }

//  public List<JSONObject> getAllOrganizationWithAttributes(){
//    //Create native query which will return attributes
//    List<Tuple> allOrgAttributeWithValue = organizationRepo.getAllOrgAttributeWithValue();
//    return null;
//  }

  public String getOrganizationWithAttributesById(Long id) {
    List<Tuple> orgAttributeWithValue = organizationRepo.getOrgAttributeWithValue(id);
    //Convert this tuple into the JSON object
    String stringJson = convert(orgAttributeWithValue);

    return stringJson;
  }

  public String convert(List<Tuple> tupleList) {
    ObjectMapper objectMapper = new ObjectMapper();
    JsonNodeFactory nodeFactory = objectMapper.getNodeFactory();

    ObjectNode mainJson = nodeFactory.objectNode();

    for (Tuple tuple : tupleList) {
      // Extract common columns
      String name = tuple.get("name", String.class);
      String website = tuple.get("website", String.class);

      // Extract different columns
      String attributeName = tuple.get("attribute_name", String.class);
      String attributeValue = tuple.get("attribute_value", String.class);


      // Add common columns to the main JsonObject
      mainJson.put("name", name);
      mainJson.put("website", website);

      // Add different columns JsonObject as a nested object
      mainJson.put(tuple.get("attribute_name", String.class),tuple.get("attribute_value",
          String.class));
    }

    try {
      return objectMapper.writeValueAsString(mainJson);
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }

}
