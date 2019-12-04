package net.matlux;

import com.google.common.collect.ImmutableMap;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.stream.Stream.empty;

public class ExampleMain {

    static <T> Stream<T> streamopt(Optional<T> opt) {
        if (opt.isPresent())
            return Stream.of(opt.get());
        else
            return empty();
    }

//    static <T> Stream<T> matchopt(Matcher m) {
//        return m.find()? Stream.of( m.group(1)):Stream.empty();
//
//    }

//    static <String> Stream<String> matchopt(Matcher m) {
//        Boolean b = true;
//        Stream<String> ss =  b? Arrays.stream(new String[]{"something"}) : Stream.empty();
//
//    }

    //static Function<? super String, ? extends Stream<String extends Object>> {

    //}

    public static void main(String[] args) {
        int min1 = Arrays.stream(new int[]{1, 2, 3, 4, 5})
                .min()
                .orElse(0);
        //assertEquals(1, min1);
        min1 = Arrays.stream(new int[]{})
                .min()
                .orElse(0);
        System.out.println("min1=" + min1);

        Thread thread = new Thread(() -> System.out.println("Hello World!"));

        int sum = Arrays.stream(new int[]{1, 2, 3})
                .filter(i -> i >= 2)
                .map(i ->{ int a = 0;
                            return i * 3;})
                .sum();
        System.out.println("sum=" + sum);


        System.out.println("Stream without terminal operation");

        Arrays.stream(new int[] { 1, 2, 3 }).map(i -> {
            System.out.println("doubling " + i);
            return i * 2;
        });

        System.out.println("Stream with terminal operation");
        Arrays.stream(new int[] { 1, 2, 3 }).map(i -> {
            System.out.println("doubling " + i);
            return i * 2;
        }).sum();

        Map<String, List<String>> people = new HashMap<>();
        people.put("John", Arrays.asList("555-1123", "555-3389"));
        people.put("Mary", Arrays.asList("555-2243", "555-5264"));
        people.put("Steve", Arrays.asList("555-6654", "555-3242"));

        List<String> phones = people.values().stream()
                .flatMap(Collection::stream)
                .collect(Collectors.toList());


        String string = "004-034556";
        String[] parts = string.split("-");
        String parts2 = string.split("-")[1];
        String part1 = parts[0]; // 004
        String part2 = parts[1]; // 034556


        String[] args2 = new String[] { "cmd", "-Dfruit=apple", "arg1","-Danimal=dog","arg2", "arg3"};

        Pattern p = Pattern.compile("-D(.*)$");


//        Function<String,Stream<String>> toGroup = x -> {
//            Matcher m = p.matcher(x);
//            return m.find()? Stream.of( m.group(1)):Stream.empty();
//
//        };

        Stream<String> res = Arrays.stream(args2).flatMap(x -> {
            Matcher m = p.matcher(x);
            return m.find()? Stream.of( m.group(1)):Stream.empty();

        });
        Stream<String> args4 = Arrays.stream(args2).flatMap(x -> {
            Matcher m = p.matcher(x);
            return m.find()? Stream.empty():Stream.of(x);

        });
        System.out.println("res=" + res.collect(Collectors.toMap(s -> s.split("=")[0],s -> s.split("=")[1])));
        System.out.println("args4=" + args4.collect(Collectors.toList()));

//        Stream<String> res = Arrays.stream(args2).filter()

//        Stream<Boolean> res = Arrays.stream(args2).flatMap(x -> {
//            Matcher m = p.matcher(x);
//            Stream<String> ss = Arrays.stream(new String[]{""});
//            return ss;
//        });System.out.println("res=" + res2);


        Stream<String> res2 = Arrays.stream(args2).flatMap(x ->  x.length() == 4 ? Stream.of(x) : Stream.empty());

        System.out.println("res2=" + res2);

//        IntStream.rangeClosed(0, 100).mapToObj(
//                i -> i % 3 == 0 ?
//                        (i % 5 == 0 ? "FizzBuzz" : "Fizz") :
//                        (i % 5 == 0 ? "Buzz" : i))
//                .forEach(System.out::println);
//
//
//
//        System.out.println("445="+toRoman(445));


        Map<String, String> map = Stream.of(new String[][] {
                { "Hello", "World" },
                { "John", "Doe" },
        }).collect(Collectors.collectingAndThen(
                Collectors.toMap(data -> data[0], data -> data[1]),
                Collections::<String, String> unmodifiableMap));

        System.out.println("map.entrySet()="+map);


        // LIST stuff
        // good old java < 7 style. Verbose and mutable/stateful
        List<String> java6list = new ArrayList();
        java6list.add("foo");
        java6list.add("bar");


        System.out.println("java6list="+java6list);


        // this is immutable
        List<Object> java8list = Arrays.asList("foo", "bar", new Object());
//        java8list.add("baz"); // calling add will raise a UnsupportedOperationException

        System.out.println("java8list="+java8list);

        // Map stuff

        // good old java < 7 style.
        Map<String, String> java6Map = new HashMap<>();
        java6Map.put("Title","My New Article");
        java6Map.put("Title2","Second Article");

        // Guava
        Map<String, String> guavaMap
                = ImmutableMap.of("Title", "My New Article", "Title2", "Second Article");

        System.out.println("articles="+guavaMap);


        // mutable class

        class Math1 {
            int factorial=1;
            int calculateFactorial(int n) {
                int acc=1;
                while(acc <= n){
                    factorial = factorial*acc;
                    acc++;
                }
                return factorial;
            }
        }
        Math1 m1 = new Math1();
        ;
        System.out.println("m1=" + m1.calculateFactorial(5));
        System.out.println("m1=" + m1.calculateFactorial(5));


        class MutableMath {
            int factorial=1;
            int n=1;
            void set(int n) {
                this.n = n;
            }

            void calculateFactorial() {
                int acc=1;
                while(acc <= n){
                    factorial = factorial*acc;
                    acc++;
                }
            }
            int getFactorial() {
                return factorial;
            }

        }
        MutableMath m3 = new MutableMath();
        m3.set(5);
        m3.calculateFactorial();
        ;
        System.out.println("m3=" + m3.getFactorial());  // 120
        m3.calculateFactorial();
        System.out.println("m3=" + m3.getFactorial());  // 14400

        // immutable class
        class Math2 {
            int calculateFactorial(int n) {
                return IntStream.range(1, n+1).reduce(Math::multiplyExact).getAsInt();
            }
        }
        Math2 m2 = new Math2();
        System.out.println("m2=" + m2.calculateFactorial(5));
        System.out.println("m2=" + m2.calculateFactorial(5));

        //
//        class Author {
//            Author(String name, int countOfBooks) {
//                this.name = name;
//                this.countOfBooks = countOfBooks;
//            }
//            final String name;
//            final int countOfBooks;
//        }
//
//        class Book {
//            Book(String name, int year, Author author) {
//                this.name = name;
//                this.year = year;
//                this.author = author;
//            }
//            final String name;
//            final int year;
//            final Author author;
//        }

        class Author {
            Author(String name, int countOfBooks) {
                this.name = name;
                this.countOfBooks = countOfBooks;
            }
            String name;
            int countOfBooks;
        }

        class Book {
            Book(String name, int year, Author author) {
                this.name = name;
                this.year = year;
                this.author = author;
            }
            String name;
            int year;
            Author author;
        }

        List<Book> books = Arrays.asList(new Book("Out of the Tar Pit",2014,new Author("Peter Marks",1)));

        List<String> allNames = new ArrayList();
//        for (Book book : books) {
//            if (book.author != null && book.year > 2005){
//                allNames.add(book.author.name);
//            }
//        }

        List<String> allNames2 = books.stream().filter(book-> book.author != null && book.year > 2005)
                .map(book -> book.author.name).collect(Collectors.toList());
                //.map(book -> b)






    }

    public final static String toRoman(int number) {
        int l =  map.floorKey(number);
        if ( number == l ) {
            return map.get(number);
        }
        return map.get(l) + toRoman(number-l);
    }
    private final static TreeMap<Integer, String> map = new TreeMap<Integer, String>();

    static {

        map.put(1000, "M");
        map.put(900, "CM");
        map.put(500, "D");
        map.put(400, "CD");
        map.put(100, "C");
        map.put(90, "XC");
        map.put(50, "L");
        map.put(40, "XL");
        map.put(10, "X");
        map.put(9, "IX");
        map.put(5, "V");
        map.put(4, "IV");
        map.put(1, "I");

    }
}
