package com.strive.learning.java8.features;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

public class C08_Generics {

	public static void main(String[] args) {
		// Using an integer
		Integer i = Integer.valueOf(23);
		System.out.println("Integer: "+(new BoxPrinter<Integer>(i)));
		
		// Using a big decimal
		BigDecimal d = new BigDecimal("23.456").setScale(2, RoundingMode.HALF_UP);
		System.out.println("BigDecimal: "+(new BoxPrinter<BigDecimal>(d)));
		
		// Age
		PersonAge<String, Integer> ages = new PersonAge<>("Jubilee", 33);
		System.out.println(ages.getName()+" is "+ages.getAge()+" years old");
		
		// Generic method
		List<String> list = GenericsUtilities.sizedList(2);
		GenericsUtilities.fill(list, "Jubilee");
		System.out.println("List: "+list);
		
		// Wildcard
		List<Long> list2 = GenericsUtilities.sizedList(3);
		GenericsUtilities.fill(list2, 4L);
		GenericsUtilities.println(list);
		GenericsUtilities.println(list2);
	}
}

// Generic methods
class GenericsUtilities {
	public static <T> void fill(List<T> list, T value) {
		for(int i = 0; i < list.size(); i++) {
			list.set(i, value);
		}
	}

	public static <T> List<T> sizedList(int capacity) {
		List<T> list = new ArrayList<>();
		for(int i = 0; i < capacity; i++) {
			list.add(null);
		}
		return list;
	}
	
	public static void println(List<?> list) {
		for(Object i : list) {
			System.out.println(i);
		}
	}
}

// Generic class using specific types
@SuppressWarnings("hiding")
class PersonAge<String, Integer> {
	private String name;
	private Integer age;
	public PersonAge(String n, Integer a) {
		this.name = n;
		this.age = a;
	}
	public String getName() {
		return name;
	}
	public Integer getAge() {
		return age;
	}
}

/**
 * Generic class.
 */
class BoxPrinter<T> {
	private T value;
	public BoxPrinter(T arg) {
		this.value = arg;
	}
	public String toString() {
		return "["+this.value+"]";
	}
}
