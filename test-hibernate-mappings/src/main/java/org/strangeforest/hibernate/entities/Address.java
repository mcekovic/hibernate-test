package org.strangeforest.hibernate.entities;

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
		if (street != null ? !street.equals(address.street) : address.street != null) return false;
		if (streetNumber != null ? !streetNumber.equals(address.streetNumber) : address.streetNumber != null) return false;
		if (city != null ? !city.equals(address.city) : address.city != null) return false;
		if (postCode != null ? !postCode.equals(address.postCode) : address.postCode != null) return false;
		if (country != null ? !country.getId().equals(address.country.getId()) : address.country != null) return false;
		return true;
	}

	@Override public int hashCode() {
		int result = street != null ? street.hashCode() : 0;
		result = 31 * result + (streetNumber != null ? streetNumber.hashCode() : 0);
		result = 31 * result + (city != null ? city.hashCode() : 0);
		result = 31 * result + (postCode != null ? postCode.hashCode() : 0);
		result = 31 * result + (country != null ? country.getId().hashCode() : 0);
		return result;
	}
}
