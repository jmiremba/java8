package com.strive.learning.java8.features;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/*
 * In Java, thrown objects should be instances of Throwable.
 * Throwable has 3 important subclasses: Error, Exception, and RuntimeException.
 * (1) Exceptions = checked, must be handled in code.
 * (2) RuntimeException = unchecked, optional to handle.
 * (3) Error = when JVM detects abnormal conditions, should not be handled.
 * If using a class that implements Closable, the garbage collector will call close() on those system resources.
 * - Otherwise you must always close() them yourself.
 */
@SuppressWarnings("unused")
public class C13_ExceptionsAssertions {

	public static void main(String[] args) throws Throwable {
		tryWithResources();
	}

	private static void tryWithResources() {
		try (
			ZipFile zf = new ZipFile("test.zip");
	        BufferedWriter writer = Files.newBufferedWriter(new File("text.txt").toPath(), Charset.defaultCharset())
	    ) {
	        for (Enumeration<? extends ZipEntry> entries = zf.entries(); entries.hasMoreElements();) {
	            String newLine = System.getProperty("line.separator");
	            String zipEntryName = entries.nextElement().getName() + newLine;
	            writer.write(zipEntryName, 0, zipEntryName.length());
	        }
	    } catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void tryMultiCatch() throws Exception {
		// If you want to handle each exception separately
		try {
			Integer.parseInt("4.56");
		} catch(NumberFormatException ex) {
			System.err.println("Error: Invalid input: "+ex.getLocalizedMessage());
		} catch(Exception ex) {
			System.err.println("Error: "+ex.getLocalizedMessage());
		}

		// If you want to handle all exceptions together
		try {
			Integer.parseInt("4.56");
		} catch(NumberFormatException | IllegalStateException ex) {
			// Exception chaining = rethrow an exception
			throw new Exception("Number format or illegal state error: "+ex.getLocalizedMessage());
		}
	}

	private static void tryCatch() {
		try {
			Integer.parseInt("2.3");
		} catch(NumberFormatException ex) {
			System.err.println("Error: Invalid input for integer");
		} finally {
			System.out.println("finally() is always called");
		}
	}

	/**
	 * Documenting atThrows.
	 * @throws Throwable An intended error.
	 */
	private static void throwingAnException() throws Throwable {
		throw new Throwable("Meant to do that!");
	}
}
