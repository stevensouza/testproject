package com.stevesouza.java8;

import org.junit.Test;

import java.util.*;
import java.util.function.IntPredicate;
import java.util.stream.*;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.joining;



import static org.assertj.core.api.Assertions.anyOf;
import static org.assertj.core.api.Assertions.assertThat;

public class Java8FeaturesTest {
    static List<String> stringList = Arrays.asList("Lambdas", "Default Method", "Stream API", "Date and Time API", "Lambdas");
    static List<Name> names = Arrays.asList(
            new Name("steve","souza"),
            new Name("jeff", "beck"),
            new Name("william", "reid"),
            new Name("jim", "reid")
            );


    @Test
    public void testForEach() {
        // Takes Consumer<T> - T->void
        // non-deterministic order - parallel ready so better to use over forEachOrdered if you can
        stringList.forEach(n -> System.out.println(n));
        // simplified form of above.
        stringList.forEach(System.out::println);

        IntStream stream = IntStream.iterate(1, i -> i + 1).limit(10);
        stream.forEach(System.out::println);

        stream = IntStream.iterate(1, i -> i + 1).limit(10);
        // deterministic order - not parallel ready
        stream.forEachOrdered(System.out::println);

        // i think i read that ordered processing takes away the ability to run in parallel.
        // or at least effectively
        stream = IntStream.iterate(1, i -> i + 1).limit(10);
        stream.parallel().forEachOrdered(System.out::println);
    }

    @Test
    public void testCount() {
        assertThat(stringList.stream().limit(2).count()).isEqualTo(2);
        assertThat(stringList.stream().distinct().count()).isEqualTo(4);
        // skip is like limit except it skips the first n and leaves the remaining.
        assertThat(stringList.stream().skip(2).count()).isEqualTo(3);
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
        IntStream stream = IntStream.iterate(1, i -> i + 1).limit(10);
        assertThat(stream.max().getAsInt()).isEqualTo(10);

        stream = IntStream.iterate(1, i -> i + 1).limit(10);
        assertThat(stream.min().getAsInt()).isEqualTo(1);

        stream = IntStream.iterate(1, i -> i + 1).limit(10);
        assertThat(stream.sum()).isEqualTo(55);

        stream = IntStream.iterate(1, i -> i + 1).limit(10);
        assertThat(stream.average().getAsDouble()).isEqualTo(5.5);

        stream = IntStream.of(1, 2, 3, 4, 5);
        assertThat(stream.average().getAsDouble()).isEqualTo(3);

        stream = IntStream.of(1, 2, 3, 4, 5);
        assertThat(stream.filter((i) -> true).count()).isEqualTo(5);

        stream = IntStream.of(1, 2, 3, 4, 5);
        assertThat(stream.filter(i -> i % 2 == 0).count()).isEqualTo(2);

        stream = IntStream.of(1, 2, 3, 4, 5);
        IntPredicate predicate = i -> i % 2 == 0;
        assertThat(stream.filter(predicate).count()).isEqualTo(2);

        stream = IntStream.of(1, 2, 3, 4, 50);
        assertThat(stream.filter(value -> value == 50).max().getAsInt()).isEqualTo(50);

        stream = IntStream.of(1, 2, 3, 4, 5);
        assertThat(stream.parallel().count()).isEqualTo(5);

        // includes endpoints so has elements 1..5
        stream = IntStream.rangeClosed(1, 5);
        assertThat(stream.average().getAsDouble()).isEqualTo(3);

        DoubleStream dstream = DoubleStream.of(1, 2, 3);
        assertThat(dstream.count()).isEqualTo(3);

        LongStream lstream = LongStream.builder().add(1).add(2).build();
        assertThat(lstream.count()).isEqualTo(2);

        // getting a stream from a collection
        Stream<String> stream1 = stringList.stream();
        assertThat(stream1.distinct().count()).isEqualTo(4);

        stream1 = Stream.of("hi", "mom", "X");
        assertThat(stream1.max((String s1, String s2) -> s1.compareToIgnoreCase(s2)).get()).isEqualTo("X");

        stream1 = Stream.of("a", "hi", "mom", "X");
        assertThat(stream1.min((s1, s2) -> s1.compareToIgnoreCase(s2)).get()).isEqualTo("a");

        // orElse is like get, but a default is provided.  get will throw and exception if the element isn't there
        stream1 = Stream.of("a", "hi", "mom", "X");
        assertThat(stream1.min((s1, s2) -> s1.compareToIgnoreCase(s2)).orElse("default")).isEqualTo("a");

        // collect
        // static import on Collectors.toList()
        //  import static java.util.stream.Collectors.toList;
        stream1 = Stream.of("a", "hi", "mom", "X");
        assertThat(stream1.collect(toList())).hasSize(4);

        // not-static use of toSet()
        stream1 = Stream.of("a", "hi", "mom", "X");
        assertThat(stream1.collect(Collectors.toSet())).hasSize(4);

        stream1 = Stream.empty();
        assertThat(stream1.count()).isEqualTo(0);

        stream1 = Stream.of("a", "hi", "mom", "X");
        assertThat(stream1.filter(str -> str.length() < 2).count()).isEqualTo(2);

        stream1 = Stream.of("b", "a", "d", "c");
        assertThat(stream1.sorted().findFirst().get()).isEqualTo("a");

        stream1 = Stream.of("b", "a", "d", "c");
        assertThat(stream1.sorted(Comparator.reverseOrder()).findFirst().get()).isEqualTo("d");

        stream1 = Stream.of("bbb", "aaa", "c", "dd");
        assertThat(stream1.sorted(Comparator.comparingInt(str -> str.length())).findFirst().get()).isEqualTo("c");

        // same as above but Class::method syntax
        stream1 = Stream.of("bbb", "aaa", "c", "dd");
        assertThat(stream1.sorted(Comparator.comparingInt(String::length)).findFirst().get()).isEqualTo("c");

        stream1 = Stream.of("bbb", "aaa", "cccc", "dd");
        assertThat(stream1.sorted(Comparator.comparingInt(String::length).reversed()).findFirst().get()).isEqualTo("cccc");

    }

    @Test
    public void testSummaryStats() {
        IntStream stream = IntStream.of(1, 2, 3, 4, 5);
        IntSummaryStatistics stats = stream.summaryStatistics();
        assertThat(stats.getCount()).isEqualTo(5);
        assertThat(stats.getAverage()).isEqualTo(3);
        assertThat(stats.getMax()).isEqualTo(5);
        assertThat(stats.getMin()).isEqualTo(1);
        assertThat(stats.getSum()).isEqualTo(15);
    }

    @Test
    public void testMatch() {
        Stream<String> stream = Stream.of("a", "b", "c", "d");
        assertThat(stream.allMatch(s->s.length()==1)).isTrue();

        stream = Stream.of("a", "b", "c", "d");
        assertThat(stream.allMatch(s->s.length()==2)).isFalse();

        stream = Stream.of("a", "b", "c", "d");
        assertThat(stream.noneMatch(s -> s.length() == 2)).isTrue();

        stream = Stream.of("a", "b", "c", "d");
        assertThat(stream.anyMatch(s -> s.length() == 1)).isTrue();

        stream = Stream.of("a", "b", "c", "d");
        assertThat(stream.anyMatch(s->s.length()==2)).isFalse();
    }


    @Test
    public void testMap() {
        assertThat(stringList.stream().mapToInt(s->1).distinct().count()).isEqualTo(1);

        Stream<String> stream = Stream.of("aa", "bbb", "cccc", "ddddd");
        assertThat(stream.mapToInt(String::length).distinct().count()).isEqualTo(4);

        stream = Stream.of("aa", "bbb", "cccc", "ddddd");
        assertThat(stream.mapToInt(String::length).distinct().max().getAsInt()).isEqualTo(5);

        stream = Stream.of("aa", "bbb", "cccc", "ddddd");
        assertThat(stream.map(String::length).collect(toList()).size()).isEqualTo(4);

        // flatMap merges a group of Streams/collections.
        // so the following would be flattened so it looks like a continuous stream.
        // flatmap expects streams.
        List<List<Integer>> list = new ArrayList();
        list.add(Arrays.asList(1, 2));
        list.add(Arrays.asList(3, 4));
        list.add(Arrays.asList(5,6));

        assertThat(list.stream().flatMap(strm->strm.stream()).collect(toList()).size()).isEqualTo(6);

        list = new ArrayList();
        list.add(Arrays.asList(1, 2));
        list.add(Arrays.asList(3, 4));
        list.add(Arrays.asList(5,6));
        assertThat(list.stream().flatMap(List::stream).collect(toList()).size()).isEqualTo(6);

        list = new ArrayList();
        list.add(Arrays.asList(1, 2));
        list.add(Arrays.asList(3, 4));
        list.add(Arrays.asList(5,6));
        // note List::stream won't work below
        assertThat(list.stream().flatMap(strm->strm.stream()).mapToInt(Integer::intValue).sum()).isEqualTo(21);

    }

    @Test
    public void testCollector() {
        List<String> list = Arrays.asList("sun","moon","star","earth");
        assertThat(list.stream().collect(joining())).isEqualTo("sunmoonstarearth");
        assertThat(list.stream().collect(joining(","))).isEqualTo("sun,moon,star,earth");
        assertThat(names.stream().map(Name::getLast).collect(joining(","))).isEqualTo("souza,beck,reid,reid");
        // same as above
        assertThat(names.stream().map(name->"n="+name.getLast()).collect(joining(","))).isEqualTo("n=souza,n=beck,n=reid,n=reid");
        // note containsOnly ensures only those elements are there in any order. To have order counted then containsExactly should
        // be used.
        assertThat(names.stream().map(Name::getLast).collect(Collectors.toSet())).containsOnly("souza", "beck", "reid");
        assertThat(names.stream().collect(Collectors.toMap(Name::getFirst, Name::getLast))).containsOnlyKeys("steve", "jeff", "william", "jim");

        Map<String, List<Name>> list1 = names.stream().collect(Collectors.groupingBy(Name::getLast));
        assertThat(list1.size()).isEqualTo(3);

        // take the stream of Name objects and group by lastName, salary
        Map<String, Integer> map = names.stream().collect(Collectors.groupingBy(Name::getLast, Collectors.summingInt(Name::getSalary)));
        IntSummaryStatistics stats = map.values().stream().mapToInt(i->i).summaryStatistics();
        assertThat(stats.getSum()).isEqualTo(40);

        // partitioningBy is like groupingBy but the groupings are only true/false
        Map<Boolean, List<String>> booleanMap = list.stream().collect(Collectors.partitioningBy(str->str.length()>3));
        assertThat(booleanMap.get(true)).hasSize(3);
        assertThat(booleanMap.get(false)).hasSize(1);

    }

    private static class Name {
        private String first;

        public String getFirst() {
            return first;
        }

        public String getLast() {
            return last;
        }

        public int getSalary() {
            return 10;
        }

        private String last;
        public Name(String first, String last) {
            this.first=first;
            this.last=last;
        }
    }

}