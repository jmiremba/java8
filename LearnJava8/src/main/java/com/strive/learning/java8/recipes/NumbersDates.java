package com.strive.learning.java8.recipes;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.util.Locale;

public class NumbersDates {
	public static void main(String[] args) {
		monetaryValues();
	}

	private static void monetaryValues() {
		// Use BigDecimal for monetary calculations
		BigDecimal balance = new BigDecimal(1234.56).setScale(2, RoundingMode.HALF_UP);
		BigDecimal amount = new BigDecimal(78.90).setScale(2, RoundingMode.HALF_UP);
		System.out.println("balance="+balance+", amount="+amount);
		
		// Simple arithmetic
		System.out.println("> balance + amount = "+balance.add(amount));
		System.out.println("> balance - amount = "+balance.subtract(amount));
		System.out.println("> balance / amount = "+balance.divide(amount, 2, RoundingMode.HALF_UP));
		System.out.println("> balance * amount = "+balance.multiply(amount));
		System.out.println("> balance % amount = "+balance.divideAndRemainder(amount)[1]);
		
		// Formatted amounts
		NumberFormat dollarFmt = NumberFormat.getCurrencyInstance(Locale.US);
		System.out.println("balance="+dollarFmt.format(balance)+", amount="+dollarFmt.format(amount));
	}
}
