package com.stevesouza.pentaho;


public class Field {

    private String name;
    private String type;
    private int length;
    private int precision;

    public Field(String name, String type, int length, int precision) {
        this.name = name;
        this.type = type;
        this.length = length;
        this.precision = precision;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public int getLength() {
        return length;
    }

    public int getPrecision() {
        return precision;
    }



}
