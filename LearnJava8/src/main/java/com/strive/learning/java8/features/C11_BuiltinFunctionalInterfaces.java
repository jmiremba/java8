package com.strive.learning.java8.features;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Currency;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.function.Consumer;
import java.util.function.DoubleFunction;
import java.util.function.Function;
import java.util.function.IntPredicate;
import java.util.function.LongConsumer;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;

/**
 * Before defining your own functional interfaces, consider using the built-in ones.
 * Key functional interfaces include:
 * (i) Predicate<T> = checks condition and returns boolean.
 * (ii) Consumer<T> = takes an argument but returns nothing.
 * (iii) Function<T,R> = takes an argument and returns a result.
 * (iv) Supplier<T> = takes no arguments but returns a value.
 * Primitive versions of the key functional interfaces exist
 * - e.g. Predicate = IntPredicate, LongPredicate, DoublePredicate.
 */
@SuppressWarnings("unused")
public class C11_BuiltinFunctionalInterfaces {
	
	public static void main(String[] args) {
		unaryFunctionInterface();
	}

	private static void unaryFunctionInterface() {
		// Unary
		List<Integer> numbers = new ArrayList<>(Arrays.asList(1, -3, 5, -7, 9));
		System.out.println("Before = "+numbers);
		UnaryOperator<Integer> uAbs = Math::abs;
		numbers.replaceAll(uAbs);
		System.out.println("After = "+numbers);
	}

	private static void binaryFunctionInterfaces() {
		// BiFunction
		BiFunction<String, String, String> bfConcat = (x, y) -> x+"_"+y;
		System.out.println("Concatenation: "+bfConcat.apply("hello", "world"));
		
		// BiPredicate
		BiPredicate<List<Integer>, Integer> bpContains = List::contains;
		List<Integer> aList = new ArrayList<>(Arrays.asList(10, 20, 30));
		System.out.println("Does list contain 20? = "+bpContains.test(aList, 20));
		
		// BiConsumer
		BiConsumer<List<Integer>, Integer> bcAddInt = List::add;
		bcAddInt.accept(aList, 55);
		System.out.println("After adding 55 to list = "+aList);
	}

	private static void primitiveFunctionalInterfaces() {
		// Integer
		IntPredicate pIntEven = (i) -> i % 2 == 0;
		System.out.println("Is 3 even? = "+pIntEven.test(3));
		
		// Long
		LongConsumer cLongPrintHex = (l) -> System.out.println(l+" as hex = 0x"+Long.toHexString(l));
		cLongPrintHex.accept(1234);
		
		// Double
		DoubleFunction<String> dDollars = (d) -> {
			Currency usd = Currency.getInstance("USD");
		    NumberFormat format = NumberFormat.getCurrencyInstance(Locale.US);
		    format.setCurrency(usd);
		    return (format.format(d) + " " + usd.getCurrencyCode());
		};
		System.out.println("1234.567 as dollars = "+dDollars.apply(1234.567));
	}

	private static void suppliers() {
		Supplier<Set<Integer>> sFiveRandom = () -> {
			Random r = new Random();
			Set<Integer> numbers = new HashSet<>();
			while (numbers.size() < 5) { numbers.add(r.nextInt(100)); }
			return numbers;
		};
		System.out.println("Five random numbers = "+sFiveRandom.get());
	}

	private static void functions() {
		Function<String, Integer> fLength = (s) -> s.length();
		String s = "Jubilee";
		int length = fLength.apply(s);
		System.out.println("The length of '"+s+"' = "+length);
		
		// Pipelining
		Function<String, Double> fParse = Double::parseDouble;
		Function<Double, Double> fAbs = Math::abs;
		Function<String, Double> parseAndAbsAndCeil = fParse.andThen(fAbs).andThen(Math::ceil);
		Arrays.stream("4.1, -9.2, 16".split(", "))
			.map(parseAndAbsAndCeil)
			.forEach(System.out::println);
	}

	private static void consumers() {
		// Prints the string uppercase
		Consumer<String> cPrintUpper = (s) -> System.out.println(s.toUpperCase());
		cPrintUpper.accept("Jubilee");
		
		// Using forEach
		Consumer<String> cPrint = (s) -> System.out.print(s.toUpperCase());
		List<String> strings = new ArrayList<>(Arrays.asList("Four", "O'clock"));
		strings.forEach(cPrint.andThen(s -> System.out.println(" ("+s.length()+")")));
	}

	private static void predicates() {
		// Tip: the expression should evaluate to TRUE
		Predicate<String> pIsNotNull = a -> a != null;
		Predicate<String> pIsNotEmpty = a -> a.length() > 0;
		Predicate<String> pNotNullAndNotEmpty = pIsNotNull.and(pIsNotEmpty);
		String helloStr = "hello";
		System.out.println("With 'hello', pNotNullAndNotEmpty="+pNotNullAndNotEmpty.test(helloStr));
		String nullStr = null;
		System.out.println("With 'null', pNotNullAndNotEmpty="+pNotNullAndNotEmpty.test(nullStr));
		
		// Removing a string
		List<String> strings = new ArrayList<>(Arrays.asList("hello", "world"));
		System.out.println("Initial list = "+strings);
		strings.removeIf(s -> s.contains("or"));
		System.out.println("> After removal of 'or' strings = "+strings);
		
		// Negate a predicate
		Predicate<String> pContainsOr = (p) -> p.contains("or");
		strings = new ArrayList<>(Arrays.asList("hello", "world"));
		System.out.println("Initial list = "+strings);
		strings.removeIf(pContainsOr.negate());
		System.out.println("> After retention of 'or' strings = "+strings);
	}
}
