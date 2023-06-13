package com.nachiket.customfield.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.nachiket.customfield.entity.EntityAttributes;
import com.nachiket.customfield.entity.OrgAttributeValue;
import com.nachiket.customfield.entity.Organization;
import com.nachiket.customfield.exceptions.ResourceNotFoundException;
import com.nachiket.customfield.repository.EntityAttributeRepo;
import com.nachiket.customfield.repository.OrgAttributeValueRepo;
import com.nachiket.customfield.repository.OrganizationRepo;
import jakarta.persistence.Tuple;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
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

  @Autowired
  private Helper helper;

  public Organization createOrganizationWithAttribute(Map<String, Object> object) {
    Organization organization = new Organization();
    organization.setName(object.get("name").toString());
    organization.setWebsite(object.get("website").toString());
    Organization entity = organizationRepo.save(organization);

    boolean attributePresent = helper.isAttributePresent(object);
    if (!attributePresent) {
      throw new ResourceNotFoundException("Attribute not present");
    }
    OrgAttributeValue orgAttributeValue = new OrgAttributeValue();
    for (Map.Entry<String, Object> entry : object.entrySet()) {
      String key = entry.getKey();
      Object value = entry.getValue();
      if (!key.equalsIgnoreCase("name") && !key.equalsIgnoreCase("website")) {
        //Check if attribute is present or not ?
        Optional<EntityAttributes> attributeName = entityAttributeRepo.findByAttributeName(key);
        Long attributeId = attributeName.get().getId();
        orgAttributeValue.setAttributeId(attributeId);
        orgAttributeValue.setEntityId(entity.getId());
        orgAttributeValue.setAttributeValue(value.toString());
      }
    }
    OrgAttributeValue entityOrgAttributeValue = orgAttributeValueRepo.save(
        orgAttributeValue);

    return entity;
  }

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

        OrgAttributeValue entityOrgAttributeValue = orgAttributeValueRepo.save(
            orgAttributeValue);
      }
    }
    return entity;
  }

  public String getOrganizationWithAttributesById(Long id) {
    Organization organization = organizationRepo.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Organization "
            + "not found"));
    List<Tuple> orgAttributeWithValue = organizationRepo.getOrgAttributeWithValue(id);
    //Convert this tuple into the JSON object
    String stringJson = convert(orgAttributeWithValue);

    return stringJson;
  }

  public void deleteOrganizationById(Long id) {
    Organization organization = organizationRepo.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Organization "
            + "not found"));
    organizationRepo.deleteOrgAttributeWithValue(organization.getId());
  }

  public List<String> getAllOrganizationWithAttributes() {
    List<Tuple> orgAttributeWithValue = organizationRepo.getAllOrgAttributeWithValue();
    //Convert this tuple into the JSON object
    List<String> stringList = convertAll(orgAttributeWithValue);

    return stringList;
  }

  public String convert(List<Tuple> tupleList) {
    ObjectMapper objectMapper = new ObjectMapper();
    JsonNodeFactory nodeFactory = objectMapper.getNodeFactory();

    ObjectNode mainJson = nodeFactory.objectNode();

    for (Tuple tuple : tupleList) {
      // Extract common columns
      Long id = tuple.get("id", Long.class);
      String name = tuple.get("name", String.class);
      String website = tuple.get("website", String.class);

      // Add common columns to the main JsonObject
      mainJson.put("id", id);
      mainJson.put("name", name);
      mainJson.put("website", website);

      // Add different columns JsonObject as a nested object
      mainJson.put(tuple.get("attribute_name", String.class), tuple.get("attribute_value",
          String.class));
    }

    try {
      return objectMapper.writeValueAsString(mainJson);
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }

  public List<String> convertAll(List<Tuple> tupleList) {
    ObjectMapper objectMapper = new ObjectMapper();
    JsonNodeFactory nodeFactory = objectMapper.getNodeFactory();

    List<String> jsonObjectList = new ArrayList<>();
    Set<Long> uniqueIds = new HashSet<>();

    for (Tuple tuple : tupleList) {
      Long id = tuple.get("id", Long.class);
      ObjectNode mainJson = nodeFactory.objectNode();

      // Skip if ID is already processed
      // if id is present but tuple is attribute name
      if (uniqueIds.contains(id) && (tuple.get("attribute_name", String.class).isEmpty()
          && tuple.get("attribute_value", String.class).isEmpty())) {
        continue;
      }

      // Extract common columns
      String name = tuple.get("name", String.class);
      String website = tuple.get("website", String.class);

      // Add common columns to the main JsonObject
      mainJson.put("id", id);
      mainJson.put("name", name);
      mainJson.put("website", website);
      mainJson.put(tuple.get("attribute_name", String.class),
          tuple.get("attribute_value", String.class));

      try {
        String jsonString = objectMapper.writeValueAsString(mainJson);
//        JsonReader jsonReader = Json.createReader(new StringReader(jsonString));
//        JsonObject jsonObject = jsonReader.readObject();
//        jsonReader.close();

//        jsonObjectList.add(jsonObject);

        jsonObjectList.add(jsonString);
        // Add ID to the set of processed IDs
        uniqueIds.add(id);
      } catch (Exception e) {
        e.getMessage();
      }
    }

    return jsonObjectList;
  }

}
