package com.stevesouza.jsonschema;


//import com.fasterxml.jackson.annotation.JsonGetter;
//import com.fasterxml.jackson.annotation.JsonProperty;


import org.codehaus.jackson.annotate.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class ErrorReport {
    private List<ErrorMessage> errors = new ArrayList<>();

    public List<ErrorMessage> getErrors() {
        return errors;
    }

    public void setErrors(List<ErrorMessage> errors) {
        this.errors = errors;
    }

    @JsonProperty
    public boolean hasErrors() {
        return !errors.isEmpty();
    }


    public void addMessage(String path, String message) {
        errors.add(new ErrorMessage(path, message));
    }

    public void addMessage(ErrorMessage errorMessage) {
        errors.add(errorMessage);
    }


    public static class ErrorMessage {

        private String path;
        private String message;

        public ErrorMessage(String path, String message) {
            this.path = path;
            this.message = message;
        }

        public String getPath() {
            return path;
        }


        public String getMessage() {
            return message;
        }


    }
}
