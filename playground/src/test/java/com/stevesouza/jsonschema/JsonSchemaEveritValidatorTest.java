package com.stevesouza.jsonschema;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.networknt.schema.JsonSchema;
import com.networknt.schema.JsonSchemaFactory;
import com.networknt.schema.SchemaValidatorsConfig;
import com.networknt.schema.ValidationMessage;
import com.stevesouza.jackson.JsonUtil;
import org.apache.camel.json.simple.JsonObject;
import org.everit.json.schema.Schema;
import org.everit.json.schema.ValidationException;
import org.everit.json.schema.Validator;
import org.everit.json.schema.event.ValidationListener;
import org.everit.json.schema.loader.SchemaLoader;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class JsonSchemaEveritValidatorTest {

    private static final String SCHEMA = "{ \"properties\": { \"foo\": { \"type\": \"string\" } } }";
    private static final String INSTANCE = "{\n\"foo\": 42\n}";
    private static final String SCHEMA_FILE = "json_file_schema.json";
    private static final String JSON_FILE = "json_file.json";
    private static final String JSON_FILE_ERRORS = "json_file_errors.json";
    private static final String LOCATION_JSON_FILE = "location_json.json";
    private static final String LOCATION_SCHEMA_FILE = "location_json_schema2.json";

    private void delme(String schemaFileName, String jsonFileName) throws IOException {
        Validator validator = Validator.builder().build();
        try {
            try (InputStream inputStream = Thread.currentThread().getContextClassLoader()
                    .getResourceAsStream(schemaFileName)) {
                JSONObject rawSchema = new JSONObject(new JSONTokener(inputStream));
                Schema schema = SchemaLoader.load(rawSchema);
                JSONObject json = new JSONObject(Thread.currentThread().getContextClassLoader()
                        .getResourceAsStream(jsonFileName));
                schema.validate(json); // throws a ValidationException if this object is invalid

            validator.performValidation(schema, json);

            }
        } catch (ValidationException e) {
            System.err.println(e.getCausingExceptions());
            System.err.println(e.toJSON().toString(2));
            System.err.println(e.getCausingExceptions().get(0).getPointerToViolation());

        }
    }
    @Test
    public void invalid2() throws Exception {
        delme(LOCATION_SCHEMA_FILE, LOCATION_JSON_FILE);
    }

    @Test
    public void invalid() throws Exception {
        JsonSchema schema = getJsonSchemaFromStringContent("{\"enum\":[1, 2, 3, 4],\"enumErrorCode\":\"Not in the list\"}");
        JsonNode node = getJsonNodeFromStringContent("7");
        Set<ValidationMessage> errors = schema.validate(node);
        assertThat(errors).hasSize(1);
    }

    @Test
    public void valid() throws Exception {
        JsonSchema schema = getJsonSchemaFromClasspath(SCHEMA_FILE);
        JsonNode node = getJsonNodeFromClasspath(JSON_FILE);
        Set<ValidationMessage> errors = schema.validate(node);
        System.err.println(JsonUtil.toJsonString(errors));
        assertThat(errors).hasSize(0);
    }



    @Test
    public void invalid_location() throws Exception {
        JsonSchema schema = getJsonSchemaFromClasspath(LOCATION_SCHEMA_FILE);
        JsonNode node = getJsonNodeFromClasspath(JSON_FILE);
        Set<ValidationMessage> errors = schema.validate(node);
        System.err.println(JsonUtil.toJsonString(errors));
        assertThat(errors).hasSize(6);
    }

    protected JsonNode getJsonNodeFromClasspath(String name) throws Exception {
        InputStream is1 = Thread.currentThread().getContextClassLoader()
                .getResourceAsStream(name);
        ObjectMapper mapper = new ObjectMapper();
        JsonNode node = mapper.readTree(is1);
        return node;
    }

    protected JsonNode getJsonNodeFromStringContent(String content) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode node = mapper.readTree(content);
        return node;
    }

    protected JsonNode getJsonNodeFromUrl(String url) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode node = mapper.readTree(new URL(url));
        return node;
    }

    protected JsonSchema getJsonSchemaFromClasspath(String name) throws Exception {
        JsonSchemaFactory factory = JsonSchemaFactory.getInstance();
        InputStream is = Thread.currentThread().getContextClassLoader()
                .getResourceAsStream(name);
        SchemaValidatorsConfig config = new SchemaValidatorsConfig();
        config.setTypeLoose(false);
        JsonSchema schema = factory.getSchema(is, config);
        return schema;
    }


    protected JsonSchema getJsonSchemaFromStringContent(String schemaContent) throws Exception {
        JsonSchemaFactory factory = JsonSchemaFactory.getInstance();
        JsonSchema schema = factory.getSchema(schemaContent);
        return schema;
    }


    protected JsonSchema getJsonSchemaFromJsonNode(JsonNode jsonNode) throws Exception {
        JsonSchemaFactory factory = JsonSchemaFactory.getInstance();
        JsonSchema schema = factory.getSchema(jsonNode);
        return schema;
    }




}
