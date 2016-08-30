package com.strive.learning.java8.features;

public class C05_Interfaces {

	// Nested interface (in a class).
	// Can also be public or protected.
	private interface Interface02 {}
	
	// Implementing an interface in an abstract class, you don't need to implement methods
	private abstract class AbstractClass01 implements Rollable {}
	
	// Ultimately, concrete classes must implement the interface's abstract methods
	private class Class03 extends AbstractClass01 implements Interface02 {
		@Override
		public void roll(double degrees) {}
		
		// Default methods can be overridden in concrete classes.
		@Override
		public void printRolls() {
			System.out.println("Overridden printRolls()");
		}
	}
	
	public static void main(String[] args) {
		// Calling default method of interface
		Class03 class3 = (new C05_Interfaces()).new Class03();
		class3.printRolls();
		
		// Calling static method of interface
		Rollable.doPrinting();
	}
}

// Declaring an interface
interface Rollable {
	// Can have final static variables
	public static final double MIN_DEGREES = 7;
	
	// Abstract method (through not explicitly declared so)
	// You can use the "abstract" keyword no problem.
	public void roll(double degrees);
	
	// Static method
	public static void doPrinting() {
		System.out.println("Doing the actual printing");
	}
	
	/**
	 * Default methods:
	 * - Aid in interface evolution without breaking implementing classes.
	 * - Provide default implementations of methods that are automatically available to implementors.
	 * - Primarily there to support lambda expressions.
	 * - Cannot be synchronized or final.
	 */
	default void printRolls() {
		System.out.println("Printing the rolls");
	}
	
	// Nested interface (in an interface).
	// Can only be public or default.
	public interface Interface01 {}
}

// Implement the interface
class RollableCircle implements Rollable {
	@Override
	public void roll(double degrees) {
		System.out.println("Rolling circle by "+degrees+" degrees (minimum="+MIN_DEGREES+")");
	}
}