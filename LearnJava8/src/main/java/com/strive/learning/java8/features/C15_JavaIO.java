package com.strive.learning.java8.features;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

@SuppressWarnings("unused")
public class C15_JavaIO {

	public static void main(String[] args) {
		usingObjectStreams();
	}

	private static void usingObjectStreams() {
		// Object
		Map<String, String> presidentsOfUS = new HashMap<>();
		presidentsOfUS.put("Barack Obama", "2009 to --, Democratic Party, 56th term");
		presidentsOfUS.put("George W. Bush", "2001 to 2009, Republican Party, 54th and 55th terms");
		presidentsOfUS.put("Bill Clinton", "1993 to 2001, Democratic Party, 52nd and 53rd terms");
		
		// Write
		try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("object.data"))) {
			oos.writeObject(presidentsOfUS);
		} catch (FileNotFoundException fnfe) {
			System.err.println("cannot create a file with the given file name ");
		} catch (IOException ioe) {
			System.err.println("an I/O error occurred while processing the file");
		}
		
		// Read
		try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("object.data"))) {
			Object obj = ois.readObject();
			if (obj != null && obj instanceof Map) {
				Map<?, ?> presidents = (Map<?, ?>) obj;
				System.out.println("President name \t Description");
				for (Map.Entry<?, ?> president : presidents.entrySet()) {
					System.out.printf("%s \t %s %n", president.getKey(), president.getValue());
				}
			}
		} catch (FileNotFoundException fnfe) {
			System.err.println("cannot create a file with the given file name ");
		} catch (IOException ioe) {
			System.err.println("an I/O error occurred while processing the file");
		} catch (ClassNotFoundException cnfe) {
			System.err.println("cannot recognize the class of the object - is the file corrupted?");
		}
	}

	private static void usingDataStreams() {
		// Binary file
		File file = new File("tmp_log.txt");

		// Write
		try (DataOutputStream dos = new DataOutputStream(new FileOutputStream(file))) {
			// Write values 1 to 10 as byte, short, int, long, float and double
			for (int i = 1; i <= 10; i++) {
				dos.writeByte(i);
				dos.writeShort(i);
				dos.writeInt(i);
				dos.writeLong(i);
				dos.writeFloat(i);
				dos.writeDouble(i);
			}
		} catch (FileNotFoundException fnfe) {
			System.err.println("cannot create a file with the given file name ");
			System.exit(-1);
		} catch (IOException ioe) {
			System.err.println("an I/O error occurred while processing the file");
			System.exit(-1);
		}

		// Read
		try (DataInputStream dis = new DataInputStream(new FileInputStream(file))) {
			// Order written: byte, short, int, long, float, double
			for (int i = 0; i < 10; i++) {
				// %d is for printing byte, short, int or long
				// %f, %g, or %e is for printing float or double
				// %n is for printing newline
				System.out.printf("%d %d %d %d %g %g %n", dis.readByte(), dis.readShort(), dis.readInt(),
						dis.readLong(), dis.readFloat(), dis.readDouble());
			}
		} catch (FileNotFoundException fnfe) {
			System.err.println("cannot create a file with the given file name ");
		} catch (IOException ioe) {
			System.err.println("an I/O error occurred while processing the file");
		}
	}

	private static void readByteStream() {
		// Binary file
		File file = new File("CoreConstants.class");

		// Magic number for .class files
		byte[] magicNumber = { (byte) 0xCA, (byte) 0xFE, (byte) 0xBA, (byte) 0xBE };

		// Read the file
		try (FileInputStream fis = new FileInputStream(file)) {
			// Magic number if first 4 bytes
			byte[] u4buffer = new byte[4];

			// Read 4 bytes from the file
			String fmt = "The file %s %s a valid .class file%n";
			if (fis.read(u4buffer) != -1) {
				if (Arrays.equals(magicNumber, u4buffer)) {
					System.out.printf(fmt, file.getAbsolutePath(), "is");
				} else {
					System.out.printf(fmt, file.getAbsolutePath(), "is not");
				}
			}
		} catch (FileNotFoundException fnfe) {
			System.err.println("file does not exist with the given file name ");
		} catch (IOException ioe) {
			System.err.println("an I/O error occurred while processing the file");
		}
	}

	private static void tokenizingText() {
		// File
		File file = new File("file_to_read.sql");

		SortedSet<String> words = new TreeSet<>();
		try (Scanner tokenizingScanner = new Scanner(new FileReader(file))) {
			// Delimiter is non-words
			tokenizingScanner.useDelimiter("\\W");

			// Scan tokens
			while (tokenizingScanner.hasNext()) {
				String word = tokenizingScanner.next();
				// Only non-empty words
				if (!word.equals("")) {
					words.add(word.toLowerCase());
				}
			}

			for (String word : words) {
				System.out.print(word + '\t');
			}
		} catch (FileNotFoundException fnfe) {
			System.err.println("Cannot read the input file - pass a valid file name");
		}
	}

	private static void readingTextFiles() {
		// File
		File file = new File("file_to_read.sql");

		// Open as resource
		try (FileReader inputFile = new FileReader(file)) {
			// Read character by character
			int ch = 0;
			while ((ch = inputFile.read()) != -1) {
				System.out.print((char) ch);
			}
		} catch (FileNotFoundException fnfe) {
			System.err.printf("Cannot open the given file %s%n", file);
		} catch (IOException ioe) {
			System.err.printf("Error when processing file %s... skipping it%n", file);
		}

		// File copy
		try (BufferedReader inputFile = new BufferedReader(new FileReader("file_to_read.sql"));
				BufferedWriter outputFile = new BufferedWriter(new FileWriter("tmp_log.txt"))) {
			int ch = 0;
			while ((ch = inputFile.read()) != -1) {
				outputFile.write((char) ch);
			}
		} catch (FileNotFoundException fnfe) {
			System.err.println("Cannot open the file " + fnfe.getMessage());
		} catch (IOException ioe) {
			System.err.printf("Error when processing file; exiting ... ");
		}
	}

	private static void reassignStandardStreams() {
		try {
			PrintStream ps = new PrintStream("tmp_log.txt");
			System.setOut(ps);
			System.out.println("Test output to System.out");
		} catch (Exception ee) {
			ee.printStackTrace();
		}
	}

	private static void readFromConsole() {
		System.out.print("Type a character: ");
		int val = 0;
		try {
			// Returns an int, the byte value of the ASCII characters!
			val = System.in.read();
		} catch (IOException ioe) {
			System.err.println("Cannot read input " + ioe);
			System.exit(-1);
		}
		System.out.println("You typed: " + val);
	}
}
