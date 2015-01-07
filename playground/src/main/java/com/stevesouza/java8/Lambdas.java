package com.stevesouza.java8;

import java.util.Arrays;
import java.util.List;

/**
 * Created by stevesouza on 1/7/15.
 */
public class Lambdas {

    public static void firstLambda() {
        List features = Arrays.asList("Lambdas", "Default Method", "Stream API", "Date and Time API");
        features.forEach(n -> System.out.println(n));
    }

    public static void secondLambda() {
        List features = Arrays.asList("Lambdas", "Default Method", "Stream API", "Date and Time API");
        features.forEach(System.out::println);
    }


    public static void main(String[] args) {
        firstLambda();
        secondLambda();
    }
}
