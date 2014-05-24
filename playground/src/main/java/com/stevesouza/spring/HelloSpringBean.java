package com.stevesouza.spring;

import org.springframework.beans.factory.annotation.Value;

/**
 * Created by stevesouza on 5/24/14.
 */
public class HelloSpringBean {

    @Value ("Hi #{systemProperties['user.name']}, and Hi Spring!")
    private String message;

    public String helloSpring() {
        return message;
    }
}
