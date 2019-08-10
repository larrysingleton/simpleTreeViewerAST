package model.person;

import java.util.ArrayList;
import java.util.List;

public class Person {
	List<Person>	list		= new ArrayList<Person>();
	Person			parent	= null;
	String			name		= null;

	public Person(String name) {
		this.name = name;
		parent = this;
	}

	public Person(String name, Person parent) {
		this.name = name;
		this.parent = parent;
	}

	public Person[] list() {
		Person[] l = new Person[list.size()];
		return list.toArray(l);
	}

	public void add(Person p) {
		list.add(p);
	}

	public Person getParent() {
		return this.parent;
	}

	public boolean hasChildren() {
		return !this.list.isEmpty();
	}

	public String getName() {
		return this.name;
	}
}
