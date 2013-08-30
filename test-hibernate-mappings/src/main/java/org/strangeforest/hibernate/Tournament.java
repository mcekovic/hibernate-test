package org.strangeforest.hibernate;

import javax.persistence.*;

@Entity
public class Tournament {

	@Id @GeneratedValue private long id;
	@Column private String name;

	public Tournament() {}

	public Tournament(String name) {
		this.name = name;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
