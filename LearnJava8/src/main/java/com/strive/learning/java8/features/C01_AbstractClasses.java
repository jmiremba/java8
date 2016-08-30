package com.strive.learning.java8.features;

public class C01_AbstractClasses {
	public static void main(String args[]) {
		// Shape
		Shape shape = new Rectangle(10, 30);
		System.out.println("The area is: "+shape.area());
	}
}

/**
 * Parent class.
 */
abstract class Shape {
	public abstract double area();
}

/**
 * Derived class extends the abstract.
 */
class Rectangle extends Shape {
	private int length, width;
	
	public Rectangle(int length, int width) {
		this.length = length;
		this.width = width;
	}
	
	@Override
	public double area() {
		return length * width;
	}
}