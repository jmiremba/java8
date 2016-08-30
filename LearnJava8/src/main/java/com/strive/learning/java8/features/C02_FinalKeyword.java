package com.strive.learning.java8.features;

public class C02_FinalKeyword {
	public static void main(String[] args) {
	}
}

/**
 * Non-inheritable class.
 * (1) Prevent behavior changes by subclassing.
 * (2) Improved performance.
 */
final class Container {}

abstract class Contained {
	private Contained shape;
	
	// Cannot be modified at runtime, assigned only once.
	@SuppressWarnings("unused")
	private final int count = 0;
	
	/**
	 * Cannot be overridden in child classes.
	 */
	public final void setParent(Contained shape) {
		this.shape = shape;
	}
	
	/**
	 * Can be overridden since it has no "final" restriction.
	 * @return
	 */
	public Contained getParent() {
		return shape;
	}
}