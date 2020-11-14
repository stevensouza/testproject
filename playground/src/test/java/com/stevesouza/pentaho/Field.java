package com.stevesouza.pentaho;


public class Fields {

    private String name;

    private String type;
    private String format;
    private String position;
    private int length;
    private int precision;

    public Fields(String name, String type, String format, String position, int length, int precision) {
        this.name = name;
        this.type = type;
        this.format = format;
        this.position = position;
        this.length = length;
        this.precision = precision;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public int getPrecision() {
        return precision;
    }

    public void setPrecision(int precision) {
        this.precision = precision;
    }

}
