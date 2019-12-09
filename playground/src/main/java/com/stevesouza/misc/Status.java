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

    /**
     *
     * @param name case insensitive string comparison to enum's name field.  For example 'accepted' would match Status.ACCEPTED
     * @return returns the matching Status
     */
    public static Status toStatus(String name) {
        return Status.valueOf(name.toUpperCase());
    }


    /**
     *
     * @param name case sensitive name value to see if that string is a Status.name() value.
     * @return true if the string is a Status enum.
     */
    public static boolean contains(String name) {
        return Arrays.asList(Status.values()).stream().anyMatch(status -> name.equals(status.name()));
    }
}

