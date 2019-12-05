package com.stevesouza.jsonschema;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonschema.core.report.ProcessingReport;
import com.github.fge.jsonschema.main.JsonSchemaFactory;
import com.stevesouza.jackson.JsonUtil;
import org.junit.Test;

import java.io.InputStream;


public class JsonSchemaValidatorFgeTest {

    private static final String SCHEMA = "{ \"properties\": { \"foo\": { \"type\": \"string\" } } }";
    private static final String INSTANCE = "{\n\"foo\": 42\n}";
    private static final String SCHEMA_FILE = "json_file_schema.json";
    private static final String JSON_FILE = "json_file.json";
    private static final String JSON_FILE_ERRORS = "json_file_errors.json";
    private static final String LOCATION_JSON_FILE = "location_json.json";
    private static final String LOCATION_SCHEMA_FILE = "location_json_schema2.json";



//    @Test
//    public void invalid() throws Exception {
//        JsonSchema schema = getJsonSchemaFromStringContent("{\"enum\":[1, 2, 3, 4],\"enumErrorCode\":\"Not in the list\"}");
//        JsonNode node = getJsonNodeFromStringContent("7");
//        Set<ValidationMessage> errors = schema.validate(node);
//        assertThat(errors).hasSize(1);
//    }
//
//    @Test
//    public void valid() throws Exception {
//        JsonSchema schema = getJsonSchemaFromClasspath(SCHEMA_FILE);
//        JsonNode node = getJsonNodeFromClasspath(JSON_FILE);
//        Set<ValidationMessage> errors = schema.validate(node);
//        System.err.println(JsonUtil.toJsonString(errors));
//        assertThat(errors).hasSize(0);
//    }
//
//    @Test
//    public void invalid2() throws Exception {
//        JsonNode schema = getJsonNodeFromClasspath(SCHEMA_FILE);
//        JsonNode json = getJsonNodeFromClasspath(JSON_FILE_ERRORS);
//        Set<ValidationMessage> errors = schema.validate(node);
//        System.err.println(JsonUtil.toJsonString(errors));
//        assertThat(errors).hasSize(6);
//    }



    public void validate(String schemaFileName, String jsonFileName) throws Exception {
        JsonNode schema = getJsonNodeFromClasspath(schemaFileName);
        JsonNode json = getJsonNodeFromClasspath(jsonFileName);
        JsonValidator validator = new JsonValidator();
        ErrorReport report = validator.validate(schema,json);
        System.err.println(JsonUtil.toJsonString(report));
        /*
        numErrors=4
[ {
  "type" : "type",
  "code" : "1029",
  "path" : "$.owner.coowner.address.state",
  "arguments" : [ "null", "string" ],
  "message" : "$.owner.coowner.address.state: null found, string expected"
}, {
  "type" : "type",
  "code" : "1029",
  "path" : "$.subapplicationId",
  "arguments" : [ "integer", "string" ],
  "message" : "$.subapplicationId: integer found, string expected"
}, {
  "type" : "type",
  "code" : "1029",
  "path" : "$.payload.insurance.propertyInsured",
  "arguments" : [ "string", "boolean" ],

  "message" : "$.payload.insurance.propertyInsured: string found, boolean expected"
}, {
  "type" : "type",
  "code" : "1029",
  "path" : "$.psiProjectId",
  "arguments" : [ "string", "integer" ],
  "message" :
         */

    }
//
//    @Test
//    public void invalid3() throws Exception {
//        validate("schema1.json", "json1.json");
//    }
//
//    @Test
//    public void invalid4() throws Exception {
//        validate("schema2.json", "json2.json");
//    }
//
//    @Test
//    public void invalid5() throws Exception {
//        validate("3schema.json", "3json.json");
//    }
//
    @Test
    public void invalid6() throws Exception {
        validate("schema_new.json", "json_new.json");
    }
//
//    @Test
//    public void invalid_location() throws Exception {
//        JsonSchema schema = getJsonSchemaFromClasspath(LOCATION_SCHEMA_FILE);
//        JsonNode node = getJsonNodeFromClasspath(JSON_FILE);
//        Set<ValidationMessage> errors = schema.validate(node);
//        System.err.println(JsonUtil.toJsonString(errors));
//        assertThat(errors).hasSize(6);
//    }
//
    protected JsonNode getJsonNodeFromClasspath(String name) throws Exception {
        InputStream is1 = Thread.currentThread().getContextClassLoader()
                .getResourceAsStream(name);
        ObjectMapper mapper = new ObjectMapper();
        JsonNode node = mapper.readTree(is1);
        return node;
    }
//
//    protected JsonNode getJsonNodeFromStringContent(String content) throws Exception {
//        ObjectMapper mapper = new ObjectMapper();
//        JsonNode node = mapper.readTree(content);
//        return node;
//    }
//
//    protected JsonNode getJsonNodeFromUrl(String url) throws Exception {
//        ObjectMapper mapper = new ObjectMapper();
//        JsonNode node = mapper.readTree(new URL(url));
//        return node;
//    }
//
//    protected JsonSchema getJsonSchemaFromClasspath(String name) throws Exception {
//        JsonSchemaFactory factory = JsonSchemaFactory.getInstance();
//        InputStream is = Thread.currentThread().getContextClassLoader()
//                .getResourceAsStream(name);
//        SchemaValidatorsConfig config = new SchemaValidatorsConfig();
//        config.setTypeLoose(false);
//        JsonSchema schema = factory.getSchema(is, config);
//        return schema;
//    }
//
//
//    protected JsonSchema getJsonSchemaFromStringContent(String schemaContent) throws Exception {
//        JsonSchemaFactory factory = JsonSchemaFactory.getInstance();
//        JsonSchema schema = factory.getSchema(schemaContent);
//        return schema;
//    }
//
//
//    protected JsonSchema getJsonSchemaFromJsonNode(JsonNode jsonNode) throws Exception {
//        JsonSchemaFactory factory = JsonSchemaFactory.getInstance();
//        JsonSchema schema = factory.getSchema(jsonNode);
//        return schema;
//    }




}
