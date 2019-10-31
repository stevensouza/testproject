package com.stevesouza.jsonschema;

import com.stevesouza.Utils;
import com.stevesouza.jackson.JsonUtil;
import com.stevesouza.jmx.NumberDelta;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.leadpony.justify.api.JsonSchema;
import org.leadpony.justify.api.JsonValidationService;
import org.leadpony.justify.api.Problem;
import org.leadpony.justify.api.ProblemHandler;

import javax.json.JsonReader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static org.assertj.core.api.Assertions.assertThat;

public class JsonSchemaJustifyTest {

    private static JsonValidationService service = JsonValidationService.newInstance();
    private static final String SCHEMA = "{ \"properties\": { \"foo\": { \"type\": \"string\" } } }";
    private static final String INSTANCE = "{\n\"foo\": 42\n}";
    private static final String SCHEMA_FILE = "json_file_schema.json";
    private static final String JSON_FILE = "json_file.json";
    private static final String JSON_FILE_ERRORS = "json_file_errors.json";


    private static List<Problem> createProblems(String schemaDoc, String instanceDoc) {
        JsonSchema schema = service.readSchema(new StringReader(schemaDoc));
        List<Problem> problems = new ArrayList<>();
//        ProblemHandler handler = problems::addAll;
        ProblemHandler handler = ProblemHandler.collectingTo(problems);
        try (JsonReader reader = service.createReader(new StringReader(instanceDoc), schema, handler)) {
            reader.readValue();
        }
        return problems;
    }

    private static List<Problem> validate(String schemaFile, String jsonFile) {
        JsonSchema schema = service.readSchema(Utils.resourceInputStream(schemaFile));
        List<Problem> problems = new ArrayList<>();
        ProblemHandler handler = ProblemHandler.collectingTo(new ArrayList<>());
        try (JsonReader reader = service.createReader(Utils.resourceInputStream(jsonFile), schema, handler)) {
            reader.readValue();
        }
        return problems;
    }

    @Test
    public void getSchemaValidate_success() {
        List<Problem> problems = validate(SCHEMA_FILE, JSON_FILE);
        System.err.println(JsonUtil.toJsonString(problems));
    }

    @Test
    public void getSchemaValidate_errors() {
        List<Problem> problems = validate(SCHEMA_FILE, JSON_FILE_ERRORS);
        System.err.println(problems);
        System.err.println(JsonUtil.toJsonString(problems));
    }

    @Test
    public void getMessageShouldReturnMessage() {
        Problem problem = createProblems(SCHEMA, INSTANCE).get(0);

        assertThat(problem.getPointer()).isEqualTo("/foo");
        assertThat(problem.getMessage()).isEqualTo("The value must be of string type, but actual type is integer.");
    }




//    @Before
//    public void setUp() throws Exception {
//
//    }
//
//    @After
//    public void tearDown() throws Exception {
//
//    }
/*
  private static Logger log;
    private static JsonValidationService service;

    private static final String SCHEMA = "{ \"properties\": { \"foo\": { \"type\": \"string\" } } }";
    private static final String INSTANCE = "{\n\"foo\": 42\n}";

    private static Problem createProblem(String schemaDoc, String instanceDoc) {
        JsonSchema schema = service.readSchema(new StringReader(schemaDoc));
        List<Problem> problems = new ArrayList<>();
        ProblemHandler handler = problems::addAll;
        try (JsonReader reader = service.createReader(new StringReader(instanceDoc), schema, handler)) {
            reader.readValue();
        }
        return problems.get(0);
    }

    @Test
    public void getMessageShouldReturnMessage() {
        Problem problem = createProblem(SCHEMA, INSTANCE);
        String message = problem.getMessage(Locale.ROOT);

        assertThat(message).isEqualTo("The value must be of string type, but actual type is integer.");
    }

    @Test
    public void getMessageShouldReturnDifferentMessageByLocale() {
        Problem problem = createProblem(SCHEMA, INSTANCE);
        String message1 = problem.getMessage(Locale.ROOT);
        String message2 = problem.getMessage(Locale.JAPANESE);

        assertThat(message1).isNotEmpty();
        assertThat(message2).isNotEmpty();
        assertThat(message1).isNotEqualTo(message2);
    }

    @Test
    public void getContextualMessageShouldReturnMessageIncludingLocation() {
        Problem problem = createProblem(SCHEMA, INSTANCE);
        String message = problem.getContextualMessage(Locale.ROOT);

        assertThat(message).startsWith("[2,9]");
    }

    @Test
    public void getLocationShouldReturnLocation() {
        Problem problem = createProblem(SCHEMA, INSTANCE);
        JsonLocation location = problem.getLocation();

        assertThat(location.getLineNumber()).isEqualTo(2);
        assertThat(location.getColumnNumber()).isEqualTo(9);
    }

    @Test
    public void getPointerShouldReturnPointer() {
        Problem problem = createProblem(SCHEMA, INSTANCE);
        String pointer = problem.getPointer();

        assertThat(pointer).isEqualTo("/foo");
    }

    @Test
    public void getSchemaShouldReturnSchema() {
        Problem problem = createProblem(SCHEMA, INSTANCE);
        JsonSchema schema = problem.getSchema();

        assertThat(schema).hasToString("{\"type\":\"string\"}");
    }

    @Test
    public void getSchemaShouldReturnBooleanSchema() {
        Problem problem = createProblem("false", INSTANCE);
        JsonSchema schema = problem.getSchema();

        assertThat(schema).isSameAs(JsonSchema.FALSE);
    }

    @Test
    public void getKeywordShouldReturnKeyword() {
        Problem problem = createProblem(SCHEMA, INSTANCE);
        String keyword = problem.getKeyword();

        assertThat(keyword).isEqualTo("type");
    }

    @Test
    public void getKeywordShouldReturnNull() {
        Problem problem = createProblem("false", INSTANCE);
        String keyword = problem.getKeyword();

        assertThat(keyword).isNull();
    }

    @Test
    public void isResolvableShouldReturnTrue() {
        Problem problem = createProblem(SCHEMA, INSTANCE);
        boolean actual = problem.isResolvable();

        assertThat(actual).isTrue();
    }

    @Test
    public void isResolvableShouldReturnFalse() {
        Problem problem = createProblem("false", INSTANCE);
        boolean actual = problem.isResolvable();

        assertThat(actual).isFalse();
    }


public static class ProblemTestCase {
    public JsonValue schema;
    public JsonValue data;
    public int lines;
}

    @ParameterizedTest
    @JsonSource("problemtest-message.json")
    public void printShouldOutputMessageAsExpected(ProblemTestCase test) {
        Problem problem = createProblem(test);
        StringBuilder builder = new StringBuilder();
        Consumer<String> consumer = line -> {
            if (builder.length() > 0) {
                builder.append('\n');
            }
            builder.append(line);
        };
        problem.print(consumer, Locale.ROOT);
        String message = builder.toString();
        log.info(message);
        String[] lines = message.split("\n", -1);
        assertThat(lines.length).isEqualTo(test.lines);
    }

    private static Problem createProblem(ProblemTestCase test) {
        return createProblem(test.schema.toString(), test.data.toString());
    }
 */


//    @Test
//    public void testDefaultDelta() throws Exception {
//        assertThat(delta.getDelta()).isEqualTo(0);
//    }
//
//    @Test
//    public void testWithValue1() throws Exception {
//        delta.setValue(1);
//        assertThat(delta.getDelta()).isEqualTo(1);
//    }
//
//    @Test
//    public void testWith2Numbers() throws Exception {
//        delta.setValue(1).setValue(101);
//        assertThat(delta.getDelta()).isEqualTo(100);
//    }
//
//    @Test
//    public void testWith3Numbers() throws Exception {
//        delta.setValue(1);
//        delta.setValue(101);
//        delta.setValue(1001);
//        assertThat(delta.getDelta()).isEqualTo(900);
//    }
//
//    @Test
//    public void testWithNegative() throws Exception {
//        delta.setValue(-100);
//        delta.setValue(100);
//        assertThat(delta.getDelta()).isEqualTo(200);
//    }
//
//    @Test
//    public void testWithNegative2() throws Exception {
//        delta.setValue(100);
//        delta.setValue(-100);
//        assertThat(delta.getDelta()).isEqualTo(-200);
//    }
}
