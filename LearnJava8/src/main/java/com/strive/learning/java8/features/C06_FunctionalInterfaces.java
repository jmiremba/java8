package com.strive.learning.java8.features;

import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * Functional interfaces declare only one abstract method.
 * - However, it can have any number of default or static methods.
 * - A declaration of a functional interface results in a "functional interface type" that can be used with lambda expressions.
 */
public class C06_FunctionalInterfaces {
	
	@FunctionalInterface
	private interface Numeric {
		// The one abstract method ("public" and "abstract" are optional and implied)
		public abstract Number precision(Number number, int p);
		
		// You can have any number of default functions
		public default String format(Number n, String pattern) {
			DecimalFormat f = new DecimalFormat(pattern);
			return f.format(n.doubleValue());
		}
		
		// You can also have static functions
		public static BigDecimal add(Number n1, Number n2) {
			return new BigDecimal(n1.doubleValue() + n2.doubleValue());
		}
		
		// Any methods "inherited" from object do not count
		public abstract String toString();
		public abstract boolean equals(Object o);
	}

	public static void main(String[] args) {
		// How to use functional interfaces?
		BigDecimal d = Numeric.add(23, 45);
		System.out.println("The sum of 23 and 45 is "+d);
		
		// Instantiating a lambda of the functional interface
		Numeric n = (number, precision) -> {
			return new BigDecimal(number.doubleValue()).setScale(precision);
		};
		
		// Using the functional interface
		System.out.println("Updating the precision of "+d+" to 4 decimal places: "+n.precision(d, 4));
		System.out.println("Formatting "+d+" to US currency: "+n.format(d, "#,##0.00"));
	}
}