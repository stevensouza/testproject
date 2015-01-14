package com.stevesouza.java8;

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

public class Java8FeaturesTest {
    static List<String> stringList = Arrays.asList("Lambdas", "Default Method", "Stream API", "Date and Time API", "Lambdas");
    static List<Name> names = Arrays.asList(
            new Name("steve","souza"),
            new Name("jeff", "beck"),
            new Name("william", "reid"),
            new Name("jim", "reid")
            );

    @Test
    public void testLambdas() {
        // Can use lambdas with Functional interfaces - One method class.
        int i=10;
        // noArgs/noReturn
        Runnable runnable = () -> System.out.println("hello");
        runnable.run();
        runnable = () -> {
            System.out.println("hello");
            System.out.println("world");
        };
        runnable.run();

        // equivalent functions: Integer function(String)
        Function<String, Integer> function = (str) -> i;
        assertThat(function.apply("steve")).isEqualTo(10);
        function = (String str) -> {return i;};
        assertThat(function.apply("steve")).isEqualTo(10);

        // UnaryOperator is a function that takes the same arg as it returns:  String function(String)
        UnaryOperator<String> unaryOperator = (str)->str+i;
        assertThat(unaryOperator.apply("steve")).isEqualTo("steve10");
        unaryOperator = (String str)->{return str+i;};
        assertThat(unaryOperator.apply("steve")).isEqualTo("steve10");

        // Predicate always returns a boolean Tboolean: boolean function(String)
        Predicate<String> isGreaterThan4 = (String str)->{return str.length()>4;};
        Predicate<String> isJoe = (String str)->{return "joe".equals(str);};

        assertThat(isGreaterThan4.test("steve")).isTrue();
        assertThat(isGreaterThan4.negate().test("steve")).isFalse();
        assertThat(isGreaterThan4.and(isJoe).test("steve")).isFalse();
        assertThat(isGreaterThan4.or(isJoe).test("steve")).isTrue();
        assertThat(isGreaterThan4.negate().or(isJoe).test("steve")).isFalse();

        // BinaryOperator:  String function(String, String)
        //  equivalent forms
        BinaryOperator<String> binaryOperator = (String str1, String str2)->{return str1+str2;};
        assertThat(binaryOperator.apply("steve","souza")).isEqualTo("stevesouza");
        binaryOperator = (str1, str2)->str1+str2;
        assertThat(binaryOperator.apply("steve","souza")).isEqualTo("stevesouza");

        // Consumer - TNone.  takes arg returns nothing
        Consumer<String> consumer = (String str)->{System.out.println(str);};
        consumer.accept("steve");

        // Supplier - voidT - factory is good example.  String function()
        Supplier<String> factorySupplier = ()->"helloworld";
        assertThat(factorySupplier.get()).isEqualTo("helloworld");
        factorySupplier = ()->{
            int myNum=15;
            System.out.println("just playin");
            return "helloworld"+myNum;
        };
        assertThat(factorySupplier.get()).isEqualTo("helloworld15");

    }

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
        assertThat(stringList.stream().
                limit(2).
                count()).isEqualTo(2);
        assertThat(stringList.stream().
                distinct().
                count()).isEqualTo(4);
        // skip is like limit except it skips the first n and leaves the remaining.
        assertThat(stringList.stream().
                skip(2).
                count()).isEqualTo(3);
    }

    @Test
    public void testFind() {
        assertThat(stringList.stream().
                findAny().
                isPresent()).isTrue();
        assertThat(stringList.stream().
                findAny().
                get()).isIn(stringList);

        assertThat(stringList.stream().
                findFirst().
                isPresent()).isTrue();
        assertThat(stringList.stream().
                findFirst().
                get()).isIn(stringList);
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

        // note in sorting, uppercase comes before upper case.
        // You must provide the Comparator for any type T.  This is not true of the primitive streams where you can just
        //  call max(), min(), average(), sum() etc.
        stream1 = Stream.of("x", "a", "X");
        assertThat(stream1.max(Comparator.naturalOrder()).get()).isEqualTo("x");

        // reversing order
        stream1 = Stream.of("x", "a", "X");
        assertThat(stream1.max(Comparator.reverseOrder()).get()).isEqualTo("X");

        // The hard way of doing the comparison yourself by providing the full Comparator.
        stream1 = Stream.of("x", "mom", "Xhi");
        assertThat(stream1.max((String s1, String s2) -> s1.compareToIgnoreCase(s2)).get()).isEqualTo("Xhi");

        // easier as you just pass in a getter method and Comparator.comparing returns the Comparator that calls
        // this on each object to do the actual comparison
        stream1 = Stream.of("x", "mom", "Xhi");
        assertThat(stream1.max(Comparator.comparing((str)->str.toUpperCase())).get()).isEqualTo("Xhi");

        // Note: stream.map(...) does a 1 to one mapping of input stream to output stream.
        // to convert back to a collection you must call stream.collect(...)
        stream1 = Stream.of("hi", "mom", "X");
        assertThat(stream1.
                map(str -> str.toUpperCase()).
                collect(toList())).isEqualTo(Arrays.asList("HI","MOM","X"));

        // flatmap denormalizes a collection i.e each value is a Collection and call collection.stream() on each value.
        List<Integer> listOfLists=Stream.of(Arrays.asList(1,2), Arrays.asList(3,4)).
                flatMap((list) -> list.stream()).
                collect(toList());
        assertThat(listOfLists).isEqualTo(Arrays.asList(1,2,3,4));

        stream1 = Stream.of("a", "hi", "mom", "X");
        assertThat(stream1.min((s1, s2) -> s1.compareToIgnoreCase(s2)).get()).isEqualTo("a");

        // orElse is like get, but a default is provided.  get will throw and exception if the element isn't there
        stream1 = Stream.of("a", "hi", "mom", "X");
        assertThat(stream1.
                min((s1, s2) -> s1.compareToIgnoreCase(s2)).
                orElse("default")).isEqualTo("a");

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
        assertThat(stream1.
                sorted().
                findFirst().
                get()).isEqualTo("a");

        stream1 = Stream.of("b", "a", "d", "c");
        assertThat(stream1.
                sorted(Comparator.reverseOrder()).
                findFirst().
                get()).isEqualTo("d");

        stream1 = Stream.of("bbb", "aaa", "c", "dd");
        assertThat(stream1.
                sorted(Comparator.comparingInt(str -> str.length())).
                findFirst().
                get()).isEqualTo("c");

        // same as above but Class::method syntax
        stream1 = Stream.of("bbb", "aaa", "c", "dd");
        assertThat(stream1.
                sorted(Comparator.comparingInt(String::length)).
                findFirst().
                get()).isEqualTo("c");

        stream1 = Stream.of("bbb", "aaa", "cccc", "dd");
        assertThat(stream1.
                sorted(Comparator.comparingInt(String::length).
                        reversed()).
                findFirst().
                get()).isEqualTo("cccc");

    }

    // Note they aren't tested below but java.util.jar.JarFile.stream() - returns Stream<JarEntry>
    // and java.util.jar.ZipFile.stream() - returns Stream<ZipEntry>
    // are also there.
    @Test
    public void testOtherStreamCreations() throws Exception {
        // Object and primitive Arrays can be converted to streams with:  Arrays.stream(...)
        String[] strArray=stringList.toArray(new String[0]);
        Stream<String> stream1=Arrays.stream(strArray);
        assertThat(stream1.count()).isEqualTo(5);
        stream1=Arrays.stream(strArray);
        Stream<String> stream2=Arrays.stream(strArray);
        stream1=Stream.concat(stream1,stream2);
        assertThat(stream1.count()).isEqualTo(10);

        // BufferedReader.lines returns Stream<String> which is tokenized at lines.
        // Note to do the same with files you can use:
        //   Stream<String> linesStream=Files.lines(new File("myfile.txt").toPath());
        StringReader stringReader=new StringReader("line1\nline2\nline3\n\n\n");
        BufferedReader bufferedReader = new BufferedReader(stringReader);
        Stream<String> stream3=bufferedReader.lines();
        assertThat(stream3.count()).isEqualTo(5);

        // Regular expression stream of tokens.
        Pattern pattern=Pattern.compile(",");
        assertThat(pattern.splitAsStream("steven,thomas,souza").count()).isEqualTo(3);

        // Characters in a string.
        CharSequence charSequence="steve souza";  // String inherits from CharSequence
        assertThat(charSequence.chars().count()).isEqualTo(11);
        assertThat(charSequence.
                chars().
                distinct().
                count()).isEqualTo(9); // duplicate s and e

        // java.nio.file.Files provides streams of Path's.
        Stream<Path> stream4=Files.list(new File(".").toPath());
        // print all files in the directory
        stream4.forEach(path->System.out.println(path));
        stream4=Files.list(new File(".").toPath());
        // Get summary stats of file size of all files in the directory.  Note the lambdas could use shorter/less explicit forms too.
        LongSummaryStatistics summaryStats1=stream4.
                map((Path path)->path.toFile()).  // could do Path::toFile
                filter((File file) -> file.isFile()).
                mapToLong((File file)->file.length()).
                summaryStatistics();
        System.out.println(summaryStats1);
        assertThat(summaryStats1.getCount()).isNotNegative();
        assertThat(summaryStats1.getSum()).isNotNegative();
        assertThat(summaryStats1.getMin()).isNotNegative();
        assertThat(summaryStats1.getMax()).isNotNegative();
        assertThat(summaryStats1.getAverage()).isNotNegative();

        // walk current directory and into its subdirectories and get a count of files.
        stream4=Files.walk(new File(".").toPath(), FileVisitOption.FOLLOW_LINKS);
        assertThat(stream4.
                map(Path::toFile).  // use short form of calling a function
                filter(File::canRead).
                count()).isNotNegative();

        // Some examples of creating random data streams.  Note you can generate ints, longs, doubles.
        Random random=new Random();
        IntStream intStream1=random.ints(10);
        intStream1.sorted().forEach(System.out::println);
        intStream1=random.ints(10);
        assertThat(intStream1.count()).isEqualTo(10);

        SplittableRandom splittableRandom = new SplittableRandom();
        LongStream longStream1=splittableRandom.longs().limit(20);
        assertThat(longStream1.count()).isEqualTo(20);

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
        assertThat(stream.
                mapToInt(String::length).
                distinct().
                count()).isEqualTo(4);

        stream = Stream.of("aa", "bbb", "cccc", "ddddd");
        assertThat(stream.
                mapToInt(String::length).distinct().
                max().
                getAsInt()).isEqualTo(5);

        stream = Stream.of("aa", "bbb", "cccc", "ddddd");
        assertThat(stream.
                map(String::length).
                collect(toList()).
                size()).isEqualTo(4);

        // flatMap merges a group of Streams/collections when they are values i.e. denormalizes
        // so the following would be flattened so it looks like a continuous stream.
        // flatmap expects streams.
        List<List<Integer>> list = new ArrayList();
        list.add(Arrays.asList(1, 2));
        list.add(Arrays.asList(3, 4));
        list.add(Arrays.asList(5,6));

        assertThat(list.stream().
                flatMap(strm -> strm.stream()).
                collect(toList()).
                size()).isEqualTo(6);

        list = new ArrayList();
        list.add(Arrays.asList(1, 2));
        list.add(Arrays.asList(3, 4));
        list.add(Arrays.asList(5,6));
        assertThat(list.stream().
                flatMap(List::stream).
                collect(toList()).
                size()).isEqualTo(6);

        list = new ArrayList();
        list.add(Arrays.asList(1, 2));
        list.add(Arrays.asList(3, 4));
        list.add(Arrays.asList(5,6));
        // note List::stream won't work below
        assertThat(list.stream().
                flatMap(strm -> strm.stream()).
                mapToInt(Integer::intValue).
                sum()).isEqualTo(21);
    }

    @Test
    public void testCollector() {
        List<String> list = Arrays.asList("sun","moon","star","earth");
        assertThat(list.stream().
                collect(joining())).isEqualTo("sunmoonstarearth");
        assertThat(list.stream().
                collect(joining(","))).isEqualTo("sun,moon,star,earth");
        assertThat(names.stream().
                map(Name::getLast).
                collect(joining(","))).isEqualTo("souza,beck,reid,reid");
        // same as above
        assertThat(names.stream().
                map(name -> "n=" + name.getLast()).
                collect(joining(","))).isEqualTo("n=souza,n=beck,n=reid,n=reid");
        // note containsOnly ensures only those elements are there in any order. To have order counted then containsExactly should
        // be used.
        assertThat(names.stream().
                map(Name::getLast).
                collect(Collectors.toSet())).containsOnly("souza", "beck", "reid");
        assertThat(names.stream().
                collect(Collectors.toMap(Name::getFirst, Name::getLast))).
                containsOnlyKeys("steve", "jeff", "william", "jim");

        Map<String, List<Name>> map1 = names.stream().
                collect(Collectors.groupingBy(Name::getLast));
        assertThat(map1.size()).isEqualTo(3);

        // Note the above is the same as if we provided the downstream collector to provide a list...
        map1 = names.stream().
                collect(Collectors.groupingBy(Name::getLast, toList()));
        assertThat(map1.size()).isEqualTo(3);

        // ...but other "downstream" collectors are possible.  This one does a group by lastName and a count on the grouping.
        //  count(*) group by lastName
        Map<String, Long> mapCounting = names.stream().
                collect(Collectors.groupingBy(Name::getLast, Collectors.counting()));
        assertThat(mapCounting.size()).isEqualTo(3);
        assertThat(mapCounting.get("reid")).isEqualTo(2);// count of 2 for reid

        // Take the aggregate map from above  (lastName, count) and find the entry with the highest count.
        Optional<String> maxCountKey=mapCounting.entrySet().stream().
                max(Map.Entry.comparingByValue()).
                map(Map.Entry::getKey);
        assertThat(maxCountKey.get()).isEqualTo("reid");

        // alternatively - and in this case I think simpler.
        String maxKey = mapCounting.entrySet().stream().
                max(Map.Entry.comparingByValue()).
                get().
                getKey();
        assertThat(maxKey).isEqualTo("reid");

        //  Some of the summarizing downstream collectors which are in the Collectors class follow:
        //   * For any data type: counting, minBy, maxBy
        //   * For primitive streams you have things like: summingInt, averagingInt which do total and average
        //   * For primitive streams you can also do all aggregates:  summarizingInt, summarizingDouble, summarisingLong

        //  take the stream of Name objects and: sum(salary), count(), average(salary)...all aggregates... group by lastName
        Map<String, Integer> map2 = names.stream().
                collect(Collectors.groupingBy(Name::getLast, Collectors.summingInt(Name::getSalary)));
        IntSummaryStatistics stats = map2.values().stream().
                mapToInt(i -> i).
                summaryStatistics();
        assertThat(stats.getSum()).isEqualTo(40);

        // max(firstName) group by lastName
        Map<String, Optional<Name>> map3 = names.stream().
                collect(Collectors.groupingBy(Name::getLast, Collectors.maxBy(Comparator.comparing(Name::getFirst))));
        assertThat(map3.get("reid").get().getFirst()).isEqualTo("william");

        // group by lastName, firstName
        Map<String, Map<String, List<Name>>> map4 = names.stream().
                collect(Collectors.groupingBy(Name::getLast, Collectors.groupingBy(Name::getFirst)));
        assertThat(map4.get("reid").get("william").get(0).getFirst()).isEqualTo("william");

        // partitioningBy is like groupingBy but the groupings are only true/false
        Map<Boolean, List<String>> booleanMap = list.stream().
                collect(Collectors.partitioningBy(str -> str.length() > 3));
        assertThat(booleanMap.get(true)).hasSize(3);
        assertThat(booleanMap.get(false)).hasSize(1);

        // The following takes all elements in a group and maps them.  In this case it takes all the values of the group
        // and concatenates them i.e. reid=william,jim
        Map<String, String> map5 = names.stream().
                collect(
                        Collectors.groupingBy(Name::getLast,
                                Collectors.mapping(Name::getFirst, Collectors.joining(","))) // concatenates names in a group.
                );
        assertThat(map5.get("reid")).isEqualTo("william,jim");
        System.out.println(map5);
    }

    @Test
    public void testReduce() {
        // note reduce is a special case of collect,  It returns an int or OptionalInt depending on the reduce method used
        // Per the javadocs Sum, min, max, and average are all special cases of reduction.

        // note all of the following are equivalent.
        int sum1 = IntStream.of(1,2,3).sum();
        int sum2 = IntStream.of(1,2,3).reduce(0, (a,b)->a+b);
        // note use of OptionalInt.  Also note seed value starts at 0 when left off
        int sum3 = IntStream.of(1,2,3).
                reduce((a, b) -> a + b).
                getAsInt();
        assertThat(sum1).isEqualTo(6);
        assertThat(sum2).isEqualTo(6);
        assertThat(sum3).isEqualTo(6);
        // starting value to add is not 0 but 1.
        assertThat(IntStream.of(1,2,3).
                reduce(1, (a, b) -> a + b)).
                isEqualTo(7);
    }

    @Test
    public void testInterfaceDefaultMethods() {
        MyClass myClass=new MyClass();
        assertThat(myClass.hello()).isEqualTo("hello");
        assertThat(myClass.goodbye()).isEqualTo("goodbye");
        // Note interface static methods only allow access via the interface name (not the class name: MyClass.getInt() is illegal).
        // This is different behaviour from a static method implemented in a base class.
        assertThat(MyInterface.getInt()).isEqualTo(10);
    }



    private static class Name {

        public Name(String first, String last) {
            this.first=first;
            this.last=last;
        }

        private String first;
        public String getFirst() {
            return first;
        }

        private String last;
        public String getLast() {
            return last;
        }

        public int getSalary() {
            return 10;
        }

    }



    // interface with default and static method
    //  note that 'public' is optional in interface function definitions.
    private static interface MyInterface {
        static final int myInt=10;
        public String hello();

        default public String goodbye() {
            return "goodbye";
        }

        public static int getInt() {
            return myInt;
        }
    }

    private static class MyClass implements MyInterface {

        @Override
        public String hello() {
            return "hello";
        }
    }

}