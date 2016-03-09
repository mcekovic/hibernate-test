package org.strangeforest.hibernate.entities.projected;

public class Player2 {

	private long id;
	private String name;

	public Player2() {}

	public Player2(String name) {
		this.name = name;
	}

	public long getId() {
		return id;
	}

	public String getName() {
		return name;
	}
}
