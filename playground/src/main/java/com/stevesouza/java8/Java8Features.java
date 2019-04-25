package com.stevesouza.java8;

import java.util.Arrays;
import java.util.List;

/**
 * Created by stevesouza on 1/7/15.
 * <p>
 * See Java8FeaturesTest for more java8 features
 */
public class Java8Features {
    static List features = Arrays.asList("Lambdas", "Default Method", "Stream API", "Date and Time API");


    public static void main(String[] args) {
        features.forEach(n -> System.out.println(n));
        features.forEach(System.out::println);
        features.stream().limit(2).count();
    }
}
