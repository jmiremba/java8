package com.strive.learning.java8.features;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.OptionalDouble;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.function.Function;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.Stream;

@SuppressWarnings("unused")
public class C12_StreamAPI {
	
	public static void main(String[] args) {
		usingFlatMap();
	}
	
	private static void usingFlatMap() {
		// Process multiple collections as once
		List<List<Integer>> intsOfInts = Arrays.asList(
				Arrays.asList(1, 3, 5), Arrays.asList(2, 4, 5));
		intsOfInts.stream()
			.flatMap(ints -> ints.stream())
			.sorted()
			.map(i -> i * i)
			.forEach(System.out::println);
	}

	private static void saveResultsToCollection() {
		// Collect to list
		String frenchNumbers = "un:deux:trois:quatre:cinq";
		List<String> numbersList = Pattern.compile(":").splitAsStream(frenchNumbers)
				.collect(Collectors.toList());
		System.out.println("numbersList = " + numbersList);
		
		// Collect to set
		String roseIsARose = "a rose is a rose is a rose";
		Set<String> wordsSet = Pattern.compile(" ").splitAsStream(roseIsARose)
				.collect(Collectors.toSet());
		System.out.println("wordsSet = " + wordsSet);
		
		// Collect to map
		String computerName = "com.strive.learning.machine";
		try {
		    InetAddress addr = InetAddress.getLocalHost();
		    computerName = addr.getHostName();
		} catch (UnknownHostException ex) {}
		Map<String, Integer> wordLengthMap = Pattern.compile("\\.").splitAsStream(computerName)
//				.collect(Collectors.toMap(w -> w, w -> w.length()));
				.collect(Collectors.toMap(Function.identity(), w -> w.length())); // Simplified
		System.out.println("computerName = "+computerName+", wordLengthMap = "+wordLengthMap);
		
		// Specific collection
		SortedSet<String> wordsSet2 = Pattern.compile(" ").splitAsStream(roseIsARose)
				.collect(Collectors.toCollection(TreeSet::new));
		System.out.println("wordsSet2 = " + wordsSet2);
		
		// Grouping
		String youNeverKnow = "you never know what you have until you clean your room";
		Map<Integer, Set<String>> wordGroups = Pattern.compile(" ").splitAsStream(youNeverKnow)
				.collect(Collectors.groupingBy(String::length, Collectors.toSet()));
		System.out.println("wordGroups = " + wordGroups);
		
		// Partition = strings with length of 4
		Map<Boolean, List<String>> wordLength4 = Pattern.compile(" ").splitAsStream(youNeverKnow)
				.collect(Collectors.partitioningBy(w -> w.length() == 4));
		System.out.println("Words of length==4? " + wordLength4.get(true));
		System.out.println("Words of length<>4? " + wordLength4.get(false));
	}

	private static void sortCollection() {
		// List of words
		List<String> words = Arrays.asList("follow your heart but take your brain with you".split(" "));
		System.out.println("Original list of words = " + words);
		
		// Lexicographical
		System.out.print("Sorted lexicographically = [ ");
		words.stream().sorted().forEach(s -> System.out.printf("%s ", s));
		System.out.println("]");
		
		// Sort by length
		Comparator<String> lengthComparator = (s1, s2) -> {
			return Integer.valueOf(s1.length()).compareTo(Integer.valueOf(s2.length()));
		};
		System.out.print("Sorted by length = [ ");
		words.stream().sorted(lengthComparator).forEach(s -> System.out.printf("%s ", s));
		System.out.println("]");
		
		// Sort by length, then alphabetically
		System.out.print("Sorted by length, then alphabetical = [ ");
		words.stream().sorted(lengthComparator.thenComparing(String::compareToIgnoreCase))
			.forEach(s -> System.out.printf("%s ", s));
		System.out.println("]");
		
		// Reverse the order
		System.out.print("Sorted by length, then alphabetical (reversed) = [ ");
		words.stream().sorted(lengthComparator.thenComparing(String::compareToIgnoreCase).reversed())
			.forEach(s -> System.out.printf("%s ", s));
		System.out.println("]");
	}

	private static void primitiveOptionalClasses() {
		// Temperatures
		double[] temps = { 24.5, 23.6, 27.9, 21.1, 23.5, 25.5, 28.3 };
		
		// Maximum
		OptionalDouble highestTemp = DoubleStream.of(temps).max();
		System.out.println("Highest temp? " + highestTemp);
		
		// Actual value
		if(highestTemp.isPresent()) {
			System.out.println("> Actual temp = " + highestTemp.getAsDouble());
		}
		
		// If statements are not true functional programming
		DoubleStream.of(temps).max().ifPresent(d -> System.out.println("Highest temp (functional)? " + d));
		
		// We've talked about max, but there are other calculation methods
		System.out.println("Total temps? " + DoubleStream.of(temps).count());
		DoubleStream.of(temps).min().ifPresent(d -> System.out.println("Lowest temp? " + d));
		DoubleStream.of(temps).average().ifPresent(d -> System.out.println("Average temp? " + d));
		System.out.println("Sum of temps? " + DoubleStream.of(temps).sum());
		
		// Statistics
		String paragraph = "While Donald Trump continues to draw bipartisan criticism for refusing to release his tax returns, "
				+ "his running mate has recently suggested that he may soon release his own.";
		IntSummaryStatistics wordStatistics = Pattern.compile(" ").splitAsStream(paragraph)
				.mapToInt(word -> word.length())
				.summaryStatistics();
		System.out.printf("WORD STATS:"
				+ "\nNumber of words = %d"
				+ "\nSum of the length of the words = %d"
				+ "\nMinimum word size = %d"
				+ "\nMaximum word size %d"
				+ "\nAverage word size = %f\n", 
				wordStatistics.getCount(), wordStatistics.getSum(), wordStatistics.getMin(),
				wordStatistics.getMax(), wordStatistics.getAverage());
	}
	
	private static void creatingOptionalClass() {
		// Empty optional
		Optional<String> oEmpty = Optional.empty();
		System.out.println("oEmpty = " + oEmpty);
		
		// Initialized optional
		Optional<String> oInitialized = Optional.of("Jubilee");
		System.out.println("oInitialized = " + oInitialized);
		
		// Optional with null
		Optional<String> oNull = Optional.ofNullable(null);
		System.out.println("oNull = " + oNull);
		
		// Optionals can be considered a stream
		Optional<String> oUntidy = Optional.of(" abcdefg\t ");
		oUntidy.map(String::trim).map(String::toUpperCase).ifPresent(s -> System.out.println("["+s+"]"));
		
		// To act on an optional containing a null (or when operations fail)
		Optional<String> oIsNull = Optional.ofNullable(null);
		System.out.println(oIsNull.map(String::trim).orElse("There was an error processing "+oIsNull));
		
		// Throw an exception if there is a problem
		try {
			Optional<String> oIsException = Optional.ofNullable(null);
			oIsException.map(String::trim).orElseThrow(IllegalArgumentException::new);
		} catch(Exception ex) {
			ex.printStackTrace();
		}
	}

	/*
	 * Holder for a value that can be null.
	 */
	private static void optionalClass() {
		// Temperatures
		List<Double> temps = Arrays.asList(24.5, 23.6, 27.9, 21.1, 23.5, 25.5, 28.3);
		
		// Maximum
		Optional<Double> highestTemp = temps.stream().max(Double::compareTo);
		System.out.println("Highest temp? " + highestTemp);
		
		// Actual value
		if(highestTemp.isPresent()) {
			System.out.println("> Actual temp = " + highestTemp.get());
		}
		
		// If statements are not true functional programming
		temps.stream().max(Double::compareTo)
			.ifPresent(d -> System.out.println("Highest temp (functional)? " + d));
	}

	/*
	 * Methods ending in "match" or starting with "find" are generally search methods.
	 * Most important match and find methods:
	 * 1. anyMatch = Returns true if any element matches the predicate.
	 * 2. allMatch = Returns true if ALL elements match the predicate, or if stream is empty.
	 * 3. noneMatch = Returns true if NONE of the elements matches the predicate, or if stream is empty.
	 * 4. findFirst = Returns the first element in the stream, or an empty Optional otherwise.
	 * 5. findAny = Returns one of the elements in the stream, or an empty Optional.
	 */
	private static void searchDataFromStream() {
		List<Integer> temps = Arrays.asList(-56, -57, -55, 52, -48, -51, 49);
		boolean anyMatch = temps.stream().anyMatch(t -> t > 0);
		System.out.println("Any temps over 0? " + anyMatch);
		boolean allMatch = temps.stream().allMatch(t -> t < 0);
		System.out.println("Are all temps under 0? " + allMatch);
		boolean noneMatch = temps.stream().noneMatch(t -> (t>=1 && t<=10));
		System.out.println("No temps are 1-10 degrees: " + noneMatch);
		
		// Lowest temperature
		Optional<Integer> oLowestTemp = temps.stream()
				.sorted()
				.findFirst();
		System.out.println("Lowest temperature: " + oLowestTemp.orElse(null));
	}

	private static void extractDataFromStream() {
		// Counting items in a stream
		long count = Stream.of(1, 2, 3, 4, 5)
				.map(n -> n*n)
				.peek(n -> System.out.printf("%d ", n))
				.count();
		System.out.println("\nStream has "+count+" elements");
		
		// You can also use the primitive versions
		double sum = DoubleStream.of(1, 2, 3, 4, 5).sum();
		System.out.println("Sum of first 5 numbers = "+sum);
	}
}
