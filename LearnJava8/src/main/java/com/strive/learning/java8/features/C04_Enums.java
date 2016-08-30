package com.strive.learning.java8.features;

public class C04_Enums {
	// Declaring enums
	public enum PrinterType {
		DOT_MATRIX, INKJET, LASER
	}
	
	// You can also add member attributes and methods
	public enum PrinterCapacity {
		DOT_MATRIX(5), INKJECT(100), LASER(1000);
		
		private PrinterCapacity(int capacity) {
			this.capacity = capacity;
		}
		
		private int capacity;
		public int getCapacity() { return this.capacity; }
	}

	public static void main(String[] args) {
		// Using enums
		PrinterType printerType = PrinterType.INKJET;
		System.out.println("Printer type = "+printerType);
		
		// In a switch statement, no need for types
		switch(printerType) {
		case DOT_MATRIX:
			System.out.println("Selected is Dot Matrix");
			break;
		case INKJET:
			System.out.println("Selected is Inkjet");
			break;
		case LASER:
			System.out.println("Selected is Laser");
			break;
		default:
			System.out.println("No printer selected");
			break;
		}
		
		// Call member functions
		PrinterCapacity printerCapacity = PrinterCapacity.LASER;
		System.out.println("Printer capacity = "+printerCapacity+", with "+printerCapacity.getCapacity()+" sheets");
		
		// The static method values() returns a list of enumerations
		System.out.println("Enumerations of PrinterCapacity:");
		for(PrinterCapacity pc: PrinterCapacity.values()) {
			System.out.println("\t"+pc+" = "+pc.getCapacity()+" sheets");
		}
		
		// Enums can be compared using == or equals()
		System.out.println("printerType == INKJET? "+(printerType == PrinterType.INKJET));
		System.out.println("printerType.equals(INKJET)? "+(printerType.equals(PrinterType.INKJET)));
		
		// Can compare objects using == or equals()
		PrinterType inkjetPrinter = PrinterType.INKJET;
		System.out.println("printerType == inkjetPrinter? "+(printerType == inkjetPrinter));
		System.out.println("printerType.equals(inkjetPrinter)? "+(printerType.equals(inkjetPrinter)));
		
		// You can derive an enum by providing its name
		PrinterCapacity dotMatrixCapacity = PrinterCapacity.valueOf("DOT_MATRIX");
		System.out.println("Printer capacity = "+dotMatrixCapacity+", with "+dotMatrixCapacity.getCapacity()+" sheets");
		
		// Other interesting properties
		System.out.println("dotMatrixCapacity.name() = "+dotMatrixCapacity.name());
		System.out.println("dotMatrixCapacity.ordinal() = "+dotMatrixCapacity.ordinal());
		System.out.println("dotMatrixCapacity.toString() = "+dotMatrixCapacity.toString());
		System.out.println("dotMatrixCapacity.getDeclaringClass() = "+dotMatrixCapacity.getDeclaringClass());
	}
}