package model.person;

import java.util.ArrayList;
import java.util.List;

public enum PersonModelProvider {
	INSTANCE;

	private List<Person> persons;

	private PersonModelProvider() {
		persons = new ArrayList<Person>();
		// group 1
		Person p1 = new Person("Customer Group 1");
		Person p1a = new Person("Customer 1-a", p1);
		Person p1b = new Person("Customer 1-b", p1);
		p1.add(p1a);
		p1.add(p1b);
		// group 2
		Person p2 = new Person("Customer Group 2");
		Person p2a = new Person("Customer 2-a", p2);
		Person p2b = new Person("Customer 2-b", p2);
		p2.add(p2a);
		p2.add(p2b);

		// sub group 3
		Person p3 = new Person("Customer Group 3", p2);
		Person p3a = new Person("Customer 3-a", p2);
		Person p3b = new Person("Customer 3-b", p2);
		p3.add(p3a);
		p3.add(p3b);
		p2.add(p3); // p2 links to p3

		persons.add(p1);
		persons.add(p2);
	}

	public List<Person> getPersons() {
		return persons;
	}
}
