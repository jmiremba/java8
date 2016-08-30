package com.strive.learning.java8.features;

public class C03_InnerClasses {
	
	public static void main(String[] args) {
		// Access inner classes
		ContainingClass cc = new ContainingClass();
		ContainingClass.InnerClassA classInnerClass = cc.new InnerClassA();
		System.out.println("classInnerClass = "+classInnerClass);
		
		// Static variables in inner static classes
		String classInnerStaticClassVariable = ContainingClass.InnerClassB.s;
		System.out.println("classInnerStaticClassVariable = "+classInnerStaticClassVariable);
		
		// Accessing inner interfaces
		ContainingClass.InnerInterfaceC classInnerInterface = new ContainingClass.InnerInterfaceC() {};
		System.out.println("classInnerInterface = "+classInnerInterface);
		
		// Class inner static interface
		String classInnerStaticInterfaceVariable = ContainingClass.InnerInterfaceD.s;
		System.out.println("classInnerStaticInterfaceVariable = "+classInnerStaticInterfaceVariable);
	}
}

class ContainingClass {
	class InnerClassA {}
	static class InnerClassB { public static final String s = "class"; }
	interface InnerInterfaceC {}
	static interface InnerInterfaceD { public static final String s = "interface"; }
}

interface ContainingInterface {
	class InnerClassA {}
	static class InnerClassB { public static final String s = "class"; }
	interface InnerInterfaceC {}
	static interface InnerInterfaceD { public static final String s = "interface"; }
}