package com.strive.learning.java8.features;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.regex.Pattern;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * Stream = sequence of elements.
 * - Can perform sequential operations using stream(), and parallel operations using parallelStream() off a collection.
 * - Streams provide pipelining capability = filter, map, search the data.
 * - Stream operations can be "chained" to create a "stream pipeline":
 * 	(a) Source = created from a collection or array using Stream methods.
 * 	(b) Intermediate operations = optional, can be chained together.
 * 	(c) Terminal operations = produce a result.
 */
@SuppressWarnings("unused")
public class C10_Streams {

	public static void main(String[] args) throws IOException {
		terminalOperations();
	}

	private static void terminalOperations() {
		IntStream stream = IntStream.range(1, 10);
//		System.out.println("Count = "+stream.count());
//		System.out.println("Maximum = "+stream.max());
//		System.out.println("Minimum = "+stream.min());
//		System.out.println("Array = "+Arrays.toString(stream.toArray()));
//		System.out.println("Sum = "+stream.sum());
		System.out.println("Average = "+stream.average());
	}

	private static void intermediateOperations() {
		long count = IntStream.range(1, 51)	// First 50 numbers
				.filter(i -> i % 2 == 0)	// Only multiples of 2
				.map(i -> {
					List<Integer> numbers = new ArrayList<>();
					String.valueOf(i).chars().forEach(a -> numbers.add(Integer.valueOf(a)));
					return numbers.stream().mapToInt(y -> y).sum();
				})							// Process
				.distinct()					// Unique
				.sorted()					// Sort
				.limit(10)					// Only the first 10
				.peek(i -> System.out.printf("%d ", i))
				.count();					// Terminal
		System.out.println("\nNumber of items = "+count);
	}

	private static void streamSources() throws IOException {
		// Many ways of creating a stream of integers from 1 to 10.
		IntStream stream1 = IntStream.range(1, 10);
		System.out.println(stream1);
		IntStream stream2 = IntStream.iterate(1, i -> i+1).limit(10);
		System.out.println(stream2);
		IntStream stream3 = Arrays.stream(new int[] {1, 2, 3, 4, 5, 6, 7, 8, 9, 10});
		System.out.println(stream3);
		Stream<Integer> stream4 = Arrays.stream(new Integer[] {1, 2, 3, 4, 5, 6, 7, 8, 9, 10});
		System.out.println(stream4);
		Stream<Integer> stream5 = Stream.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
		System.out.println(stream5);
		Stream<Object> stream6 = Stream.builder().add(1).add(2).add(3).add(4).add(5).add(6).add(7).add(8).add(9).add(10).build();
		System.out.println(stream6);
		
		// Stream from collection
		List<String> strings = Arrays.asList("eeny", "meeny", "miny", "mo");
		Stream<String> stream7 = strings.stream();
		System.out.println(stream7);
		
		// Stream of file contents
		File currDir = new File("/Development/properties/extjs-practicum.properties");
		Files.lines(currDir.toPath()).forEach(System.out::println);
		
		// Splitting a string
		System.out.println("\nREGEX SPLIT:");
		Pattern.compile(" ").splitAsStream("Java 8 Streams").forEach(System.out::println);
		
		// Splitting a string
		System.out.println("\nRANDOM FIVE NUMBERS:");
		new Random().ints().limit(5).forEach(System.out::println);
		
		// Sort characters of the String
		System.out.println("\nSORT CHARACTERS IN 'JUBILEE':");
		"JUBILEE".chars().sorted().forEach(c -> System.out.printf("%c ", c));
	}

	private static void streamPipelineExample() {
		Arrays.stream(Object.class.getMethods())	// Source
			.map(method -> method.getName())		// Intermediate operation
			.distinct()								// Intermediate operation
			.sorted()								// Intermediate operation
			.forEach(System.out::println);			// Terminal operation
	}

	private static void methodReferences() {
		// Supposing we have a collection
		List<String> names = Arrays.asList("Jubilee", "Angelene", "Moses", "Nelson");
		
		/*
		 * Print using method references.
		 * Only route messages, so you cannot do anything further with the passed object.
		 * - Not suitable if you need to manipulate the element further.
		 */
		names.forEach(System.out::println); // Same as: n -> System.out.println(n)
	}

	private static void usingForEach() {
		// Supposing we have a collection
		List<String> names = Arrays.asList("Jubilee", "Angelene", "Moses", "Nelson");
		// Collection interface forEach using lambda
		names.forEach(n -> System.out.println(n));
	}
}
