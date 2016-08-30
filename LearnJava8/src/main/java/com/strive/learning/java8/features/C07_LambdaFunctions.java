package com.strive.learning.java8.features;

/**
 * Introduced in Java 8, a major new feature.
 * Also known as "Closures", supported in Groovy (which compiles to Java).
 * Lambdas support functional programming paradigm = lambda functions can be stored in variables, passed around to methods,
 * 	and returned from other methods.
 * Syntax: (params,...) -> { body }
 * - Params do not have to declare type; it is inferred from the interface.
 * - Parentheses are optional if there is only one param.
 * - The body can be an expression (single line) or a block (in curly brackets).
 * - Return is inferred: no need to add "return" as last line, but all paths must then return appropriate type.
 * Lambdas are effectively "anonymous functions"
 * - They are not static or instance members of the class in which they are defined. 
 * - If you use this keyword inside a lambda function, it refers to the object in the scope in which the lambda is defined.
 * Lambda expressions can use local variables, but the compiler treats them as effectively final - cannot be changed inside the expression.
 * 
 */
public class C07_LambdaFunctions {
	@FunctionalInterface
	private interface LambdaFunction {
		void call();
	}

	@FunctionalInterface
	private interface BlockLambda {
		String evenOrOdd(int i);
	}
	
	public static void main(String[] args) {
		LambdaFunction f = () -> System.out.println("Lambda function called");
		f.call();
		
		// Block lambda
		BlockLambda f2 = (i) -> {
			boolean isEven = (i % 2 == 0);
			return isEven ? "Even" : "Odd";
		};
		System.out.println("Is 23 even or odd? "+f2.evenOrOdd(23));
	}
}
