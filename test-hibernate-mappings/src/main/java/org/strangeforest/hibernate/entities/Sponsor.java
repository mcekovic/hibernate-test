package org.strangeforest.hibernate.entities;

import javax.persistence.*;

@Entity
public class Sponsor {

	@Id private String id;
	private String name;

	public Sponsor() {}

	public Sponsor(String id, String name) {
		this.id = id;
		this.name = name;
	}

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}
}
