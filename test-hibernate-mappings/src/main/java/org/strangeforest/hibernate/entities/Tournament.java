package org.strangeforest.hibernate.entities;

import javax.persistence.*;

@Entity
public class Tournament {

	@Id @GeneratedValue private long id;
	@Column(unique = true) private String name;
	private TournamentType type;
	@ManyToOne private Country country;

	public Tournament() {}

	public Tournament(String name, TournamentType type, Country country) {
		this.name = name;
		this.country = country;
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

	public TournamentType getType() {
		return type;
	}

	public void setType(TournamentType type) {
		this.type = type;
	}

	public Country getCountry() {
		return country;
	}

	public void setCountry(Country country) {
		this.country = country;
	}
}
