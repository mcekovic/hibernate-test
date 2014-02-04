package org.strangeforest.hibernate.entities;

import java.util.*;
import javax.persistence.*;

import static javax.persistence.FetchType.*;

@Embeddable
public class Address {

	private String street;
	private String streetNumber;
	private String city;
	private String postCode;
	@ManyToOne(fetch = LAZY) private Country country;

	public Address() {}

	public Address(String street, String streetNumber, String city, String postCode, Country country) {
		this.street = street;
		this.streetNumber = streetNumber;
		this.city = city;
		this.postCode = postCode;
		this.country = country;
	}

	public String getStreet() {
		return street;
	}

	public String getStreetNumber() {
		return streetNumber;
	}

	public String getCity() {
		return city;
	}

	public String getPostCode() {
		return postCode;
	}

	public Country getCountry() {
		return country;
	}


	// Object Utils

	@Override public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Address address = (Address)o;
		if (!Objects.equals(street, address.street)) return false;
		if (!Objects.equals(streetNumber, address.streetNumber)) return false;
		if (!Objects.equals(city, address.city)) return false;
		if (!Objects.equals(postCode, address.postCode)) return false;
		if (!Objects.equals(country, address.country.getId())) return false;
		return true;
	}

	@Override public int hashCode() {
		int result = Objects.hashCode(street);
		result = 31 * result + Objects.hashCode(streetNumber);
		result = 31 * result + Objects.hashCode(city);
		result = 31 * result + Objects.hashCode(postCode);
		result = 31 * result + Objects.hashCode(country);
		return result;
	}
}
