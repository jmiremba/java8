package com.strive.learning.java8.features;

import java.io.File;
import java.io.IOException;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Map.Entry;
import java.util.Set;
import java.util.function.BiPredicate;
import java.util.stream.Stream;

@SuppressWarnings("unused")
public class C16_JavaNIO2 {

	public static void main(String[] args) throws IOException {
		usingStreamLines();
	}

	private static void usingStreamLines() {
		Path sqlPath = Paths.get("file_to_read.sql");
		System.out.println("Path = " + sqlPath);

		// Read
		try (Stream<String> lines = Files.lines(sqlPath)) {
			lines.forEach(System.out::println);
		} catch (IOException ioe) {
			System.err.println("IOException occurred when reading the file ... exiting");
		}
	}

	private static void usingStreamList() throws IOException {
		Path currDir = Paths.get(".");

		// Listing files
		System.out.println("Files under " + currDir);
		try (Stream<Path> entries = Files.list(currDir)) {
			entries.map(p -> p.toAbsolutePath().normalize()).forEach(p -> System.out.println("> " + p));
		}

		// Recursively visit
		System.out.println("Recursive files under " + currDir);
		try (Stream<Path> entries = Files.walk(currDir)) {
			entries.map(p -> p.toAbsolutePath().normalize()).forEach(p -> System.out.println("> " + p));
		}

		// Count files
		System.out.println("Count files under " + currDir);
		try (Stream<Path> entries = Files.walk(currDir)) {
			System.out.println("> Number of files = " + entries.count());
		}

		// Search
		System.out.println("Search for *.prefs under " + currDir);
		BiPredicate<Path, BasicFileAttributes> bpSearch = (path, attrs) -> {
			return (attrs.isRegularFile() && path.toString().endsWith(".prefs"));
		};
		try (Stream<Path> entries = Files.find(currDir, 4, bpSearch)) {
			entries.map(p -> p.toAbsolutePath().normalize()).forEach(p -> System.out.println("> " + p));
		}
	}

	private static void usingFiles() throws IOException {
		// Paths
		Path relpath = Paths.get("file_to_read.sql");
		System.out.println("Path (rel) = " + relpath);
		Path abspath = Paths.get("/Development/workspaces/java8/LearnJava8/file_to_read.sql");
		System.out.println("Path (abs) = " + abspath);

		// Metadata
		System.out.println("> Files.isSameFile() = " + Files.isSameFile(relpath, abspath));
		System.out.println("> Files.exists() = " + Files.exists(relpath));
		System.out.println("> Files.isDirectory() = " + Files.isDirectory(abspath, LinkOption.NOFOLLOW_LINKS));

		// Attributes
		System.out.printf("> Readable: %b, Writable: %b, Executable: %b%n", Files.isReadable(relpath),
				Files.isWritable(relpath), Files.isExecutable(relpath));
		System.out.println("> Files.getAttribute(creationTime) = "
				+ Files.getAttribute(abspath, "creationTime", LinkOption.NOFOLLOW_LINKS));
		Set<Entry<String, Object>> attributesSet = Files.readAttributes(relpath, "*", LinkOption.NOFOLLOW_LINKS)
				.entrySet();
		for (Entry<String, Object> attribute : attributesSet) {
			System.out.println("> Attribute: " + attribute);
		}

		// Copying
		Path srcPath = Paths.get("file_to_read.sql");
		Path copyPath = Paths.get("file_to_read.sql.copy");
		try {
			Files.copy(srcPath, copyPath, StandardCopyOption.REPLACE_EXISTING);
			System.out.println("Copied " + srcPath + " to " + copyPath);
		} catch (IOException ex) {
			ex.printStackTrace();
		}

		// Moving
		Path moveDir = srcPath.toAbsolutePath().getParent().getParent();
		Path movedFile = null;
		try {
			movedFile = Files.move(copyPath, Paths.get(moveDir.toString(), copyPath.getFileName().toString()),
					StandardCopyOption.REPLACE_EXISTING);
			System.out.println("Moved " + copyPath + " to " + moveDir);
			System.out.println("> Moved file = " + movedFile);
		} catch (IOException ex) {
			ex.printStackTrace();
		}

		// Deleting
		try {
			Files.delete(movedFile);
			System.out.println(
					"Deleted " + movedFile + " {exists=" + Files.exists(movedFile, LinkOption.NOFOLLOW_LINKS) + "}");
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	private static void usingPaths() throws IOException {
		// File
		Path filePath = Paths.get("/Development/workspaces/java8/LearnJava8/file_to_read.sql");
		System.out.println("File = " + filePath);
		System.out.println("> getRoot() = " + filePath.getRoot());
		System.out.println("> getFileName() = " + filePath.getFileName());
		System.out.println("> getNameCount() = " + filePath.getNameCount());
		System.out.println("> getName(2) = " + filePath.getName(2));
		System.out.println("> subpath(1,3) = " + filePath.subpath(1, 3));
		System.out.println("> isAbsolute() = " + filePath.isAbsolute());
		System.out.println("> startsWith(/var) = " + filePath.startsWith("/var"));
		System.out.println("> toUri() = " + filePath.toUri());

		// Relative
		Path relPath = Paths.get("../../");
		System.out.println("Relative(../../) = " + relPath);
		System.out.println("> isAbsolute() = " + relPath.isAbsolute());
		System.out.println("> toAbsolutePath() = " + relPath.toAbsolutePath());
		System.out.println("> toRealPath() = " + relPath.toRealPath(LinkOption.NOFOLLOW_LINKS));
		Path relPathNormalized = relPath.toAbsolutePath().normalize();
		System.out.println("Relative(normalized) = " + relPathNormalized);

		// Directory
		Path dirPath = filePath.getParent();
		System.out.println("Directory = " + dirPath);

		// Path elements
		for (Path element : dirPath) {
			System.out.println("> Element = " + element);
		}

		// From file to path
		File file = filePath.toFile();
		Path filePath2 = file.toPath();
		System.out.println(file + " == " + filePath2);

		// Comparison
		Path relpath = Paths.get("file_to_read.sql");
		Path abspath = Paths.get("/Development/workspaces/java8/LearnJava8/file_to_read.sql");
		System.out.println("relpath.compareTo(abspath) = " + relpath.compareTo(abspath));
		System.out.println("relpath.equals(abspath) = " + relpath.equals(abspath));
		System.out.println("abspath.equals(relpath.toAbsolutePath()) = " + abspath.equals(relpath.toAbsolutePath()));
	}
}
