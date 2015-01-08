package com.stevesouza.java8;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.anyOf;
import static org.assertj.core.api.Assertions.assertThat;

public class Java8FeaturesTest {
    static List stringList = Arrays.asList("Lambdas", "Default Method", "Stream API", "Date and Time API", "Lambdas");



    @Test
    public void testForEach() {
        // Takes Consumer<T> - T->void
        stringList.forEach(n -> System.out.println(n));
        // simplified form of above.
        stringList.forEach(System.out::println);

        IntStream stream=IntStream.iterate(1, i->i+1).limit(10);
        stream.forEach(System.out::println);
    }
    @Test
    public void testCount() {
        assertThat(stringList.stream().limit(2).count()).isEqualTo(2);
        assertThat(stringList.stream().distinct().count()).isEqualTo(4);
    }

    @Test
    public void testFind() {
        assertThat(stringList.stream().findAny().isPresent()).isTrue();
        assertThat(stringList.stream().findAny().get()).isIn(stringList);

        assertThat(stringList.stream().findFirst().isPresent()).isTrue();
        assertThat(stringList.stream().findFirst().get()).isIn(stringList);
    }

    @Test
    public void testStreams() {
        // note without limit the following would be an infinite loop
        IntStream stream=IntStream.iterate(1, i->i+1).limit(10);
        assertThat(stream.max().getAsInt()).isEqualTo(10);

        stream=IntStream.iterate(1, i->i+1).limit(10);
        assertThat(stream.min().getAsInt()).isEqualTo(1);

        stream=IntStream.iterate(1, i->i+1).limit(10);
        assertThat(stream.sum()).isEqualTo(55);

        stream=IntStream.iterate(1, i->i+1).limit(10);
        assertThat(stream.average().getAsDouble()).isEqualTo(5.5);

        stream=IntStream.of(1, 2, 3, 4, 5);
        assertThat(stream.average().getAsDouble()).isEqualTo(3);

        stream=IntStream.of(1, 2, 3, 4, 5);
        assertThat(stream.filter((i)->true).count()).isEqualTo(5);

        stream=IntStream.of(1, 2, 3, 4, 5);
        assertThat(stream.filter(i-> i%2==0).count()).isEqualTo(2);

        stream=IntStream.of(1, 2, 3, 4, 50);
        assertThat(stream.filter(value-> value==50).max().getAsInt()).isEqualTo(50);

        stream=IntStream.of(1, 2, 3, 4, 5);
        assertThat(stream.parallel().count()).isEqualTo(5);
    }
}