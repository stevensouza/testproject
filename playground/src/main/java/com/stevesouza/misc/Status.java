package com.stevesouza.misc;

import java.util.Arrays;

public enum Status {
    ACCEPTED("A"),
    REJECTED("R"),
    WAITING("W");

    private String id;
    Status(String id) {
        this.id = id;
    }

    public String id() {
        return id;
    }

    public static Status toStatus(String name) {
        return Status.valueOf(name.toUpperCase());
    }

    public static boolean contains(String name) {
        return Arrays.asList(Status.values()).stream().anyMatch(status -> name.equals(status.name()));
    }
}

