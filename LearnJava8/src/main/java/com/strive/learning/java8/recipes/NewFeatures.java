package com.strive.learning.java8.recipes;

import java.lang.annotation.Repeatable;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

public class NewFeatures {
	public static void main(String[] args) {
		embedJavascript();
	}
	
	private static void embedJavascript() {
		ScriptEngineManager sem = new ScriptEngineManager();
		ScriptEngine nashorn = sem.getEngineByName("nashorn");
		try {
			nashorn.eval("print('This is Javascript!');");
		} catch (ScriptException e) {
			e.printStackTrace();
		}
	}

	/**
	 * The @Repeatable annoatation is used to declare annotations that can be repeated, rather than grouped.
	 * - When used more than once, it uses the Roles annotation when used more than once.
	 */
	@Repeatable(value=Roles.class)
	private @interface Role {
		public String name() default "AUTHOR";
	}
	
	private @interface Roles {
		public Role[] value();
	}
	
	@Role(name="WRITER")
	@Role(name="PRODUCER")
	private static void sameAnnotationMultipleTimes() {
		
	}
	
	@Roles({
		@Role(name="WRITER"),
		@Role(name="PRODUCER")
	})
	private static void sameAnnotationMultipleTimes2() {
		
	}
}
