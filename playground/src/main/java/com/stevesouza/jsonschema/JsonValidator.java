package com.stevesouza.jsonschema;

import com.fasterxml.jackson.databind.JsonNode;
import com.github.fge.jsonschema.core.exceptions.ProcessingException;
import com.github.fge.jsonschema.core.report.LogLevel;
import com.github.fge.jsonschema.core.report.ProcessingReport;
import com.github.fge.jsonschema.main.JsonSchema;
import com.github.fge.jsonschema.main.JsonSchemaFactory;
import org.springframework.stereotype.Component;

import java.util.stream.StreamSupport;

@Component
public class JsonValidator {
    final JsonSchemaFactory factory = JsonSchemaFactory.byDefault();

    public ErrorReport validate(JsonNode jsonSchemaNode, JsonNode jsonToValidate) {
        try {
            JsonSchema jsonSchema = factory.getJsonSchema(jsonSchemaNode);
            ProcessingReport report = jsonSchema.validateUnchecked(jsonToValidate, true);
            return createErrorReport(report);
        } catch (ProcessingException e) {
            throw new JsonValidatorException(e);
        }
    }

    private ErrorReport createErrorReport(ProcessingReport report) {
        ErrorReport errorReport = new ErrorReport();
        StreamSupport.stream(report.spliterator(), false)
                .filter(processingMessage -> LogLevel.ERROR.equals(processingMessage.getLogLevel()) || LogLevel.FATAL.equals(processingMessage.getLogLevel()))
                .forEach(
                        processingMessage -> {
                            JsonNode node = processingMessage.asJson();
                            ErrorReport.ErrorMessage message = new ErrorReport.ErrorMessage(node.get("instance").get("pointer").asText(), node.get("message").asText());
                            errorReport.addMessage(message);
                        });
        return errorReport;
    }


    private JsonSchema toJsonSchema(JsonNode jsonSchemaNode) {
        try {
            return factory.getJsonSchema(jsonSchemaNode);
        } catch (ProcessingException e) {
            throw new JsonValidatorException(e);
        }
    }

    public static class JsonValidatorException extends RuntimeException {
        public JsonValidatorException(Throwable throwable) {
            super(throwable);
        }
    }


}
