package com.stevesouza.assertj;

import org.junit.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.StringReader;
import java.nio.file.FileVisitOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.function.*;
import java.util.regex.Pattern;
import java.util.stream.*;

import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * examples for assertj:
 *  https://github.com/joel-costigliola/assertj-examples/tree/master/assertions-examples/src/test/java/org/assertj/examples
 */
public class AssertJFeaturesTest {

    @Test
    public void testDescribedAs() {
        // test will 'fail' but only print the messages as the exceptions are gobbled.
        try {
            assertThat(true).describedAs("My describedAs assertion message").isFalse();
        } catch (AssertionError e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void testAs() {
        // Same as describedAs, but as is a key word in groovy so the preferred approach is to use describedAs
        try {
            assertThat(true).as("My as assertion message").isFalse();
        } catch (AssertionError e) {
            System.out.println(e.getMessage());
        }
    }
    @Test
    public void testDescribedAsWithArgs() {
        // describedAs formatting follows:
        //   http://docs.oracle.com/javase/7/docs/api/java/util/Formatter.html
        try {
            int myNum=123;
            String myName="steve";
            // note %d could also be used for the number instead of %s
            assertThat(true).describedAs("My describedAs assertion message with args name='%s', num=%s", myName, myNum).isFalse();
        } catch (AssertionError e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void testMockitoVerity() {
        MyClass obj = mock(MyClass.class);
        obj.myMethod("hi");
        // the following 2 are the same
        verify(obj).myMethod(anyString());
        verify(obj, times(1)).myMethod(anyString());


        obj.myOtherMethod();
        obj.myOtherMethod();
        verify(obj, times(2)).myOtherMethod();
    }

    public class MyClass {
        public void myMethod(String str) {

        }

        public void myOtherMethod() {

        }
    }

}