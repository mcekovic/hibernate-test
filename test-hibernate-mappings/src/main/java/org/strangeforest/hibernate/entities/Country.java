package org.strangeforest.hibernate.entities;

import javax.persistence.*;

@Entity
public class Country {

	@Id private String id;
	private String name;

	public Country() {}

	public Country(String id, String name) {
		this.id = id;
		this.name = name;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
