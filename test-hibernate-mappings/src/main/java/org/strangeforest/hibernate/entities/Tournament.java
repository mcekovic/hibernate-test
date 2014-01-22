package org.strangeforest.hibernate.entities;

import javax.persistence.*;

import static javax.persistence.FetchType.*;

@Entity
public class Tournament {

	@Id @GeneratedValue private long id;
	@Column(unique = true) private String name;
	private TournamentType type;
	@ManyToOne(fetch = LAZY) private Country country;

	public Tournament() {}

	public Tournament(String name, TournamentType type, Country country) {
		this.name = name;
		this.type = type;
		this.country = country;
	}

	public long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public TournamentType getType() {
		return type;
	}

	public Country getCountry() {
		return country;
	}
}
