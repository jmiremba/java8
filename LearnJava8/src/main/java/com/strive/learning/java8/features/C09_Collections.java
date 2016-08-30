package com.strive.learning.java8.features;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.NavigableMap;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;

@SuppressWarnings("unused")
public class C09_Collections {

	public static void main(String[] args) {
		comparisons();
	}
	
	private static void comparisons() {
		// List of people
		List<Person> people = Arrays.asList(
				new Person(1L, "Miremba"),
				new Person(1L, "Jubilee"),
				new Person(2L, "Elizabeth"),
				new Person(3L, "Andrew")
		);
		System.out.println("ORIGINAL LIST = "+people);
		
		// Natoral sorting using Comparable (on Person)
		SortedSet<Person> sortedPeople = new TreeSet<>(people);
		System.out.println("NATURALLY SORTED = "+sortedPeople);
		
		// Using a comparator
		Collections.sort(people, new PeopleByIdComparator());
		System.out.println("SORTED WITH COMPARATOR = "+people);
	}

	private static void usingNavigableMap() {
        NavigableMap<Integer, String> examScores = new TreeMap<Integer, String>();
        examScores.put(90, "Sophia");
        examScores.put(20, "Isabella");
        examScores.put(10, "Emma");
        examScores.put(50, "Olivea");
        System.out.println("The data in the map is: " + examScores); 
        System.out.println("The data descending order is: " + examScores.descendingMap()); 
        System.out.println("Details of those who passed the exam: " + examScores.tailMap(40)); 
        System.out.println("The lowest mark is: " + examScores.firstEntry());
	}

	private static void usingIterators() {
		List<Integer> list = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5));
		System.out.println("Original: "+list);
		for(Iterator<Integer> it = list.iterator(); it.hasNext(); ) {
			Integer i = it.next();
			if(i % 2 == 0) { it.remove(); }
		}
		System.out.println("Final: "+list);
	}
	
	private static void usingAsList() {
		Integer[] numbers = {1, 2, 3, 4};
		List<Integer> list = Arrays.asList(numbers);
		System.out.println(list);
	}
}

class PeopleByIdComparator implements Comparator<Person> {
	@Override
	public int compare(Person o1, Person o2) {
		int comp = o1.getId().compareTo(o2.getId());
		if(comp == 0) { comp = o1.getName().compareTo(o2.getName()); }
		return comp;
	}
}

class Person implements Comparable<Person> {
	private Long id;
	private String name;
	
	public Person(Long id, String name) {
		this.id = id;
		this.name = name;
	}

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	@Override
	public int compareTo(Person o) {
		int comp = this.name.compareTo(o.name);
		if(comp == 0) { comp = this.id.compareTo(o.id); }
		return comp;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof Person)) {
			return false;
		}
		Person other = (Person) obj;
		if (id == null) {
			if (other.id != null) {
				return false;
			}
		} else if (!id.equals(other.id)) {
			return false;
		}
		if (name == null) {
			if (other.name != null) {
				return false;
			}
		} else if (!name.equals(other.name)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "Person [id=" + id + ", name=" + name + "]";
	}
}
