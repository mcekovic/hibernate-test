package org.strangeforest.hibernate;

import javax.persistence.*;

@Embeddable
public class Address {

	@Column private String street;
	@Column private String streetNumber;
	@Column private String city;
	@Column private String postCode;
	@Column private String country;

	public Address() {}

	public Address(String street, String streetNumber, String city, String postCode, String country) {
		this.street = street;
		this.streetNumber = streetNumber;
		this.city = city;
		this.postCode = postCode;
		this.country = country;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getStreetNumber() {
		return streetNumber;
	}

	public void setStreetNumber(String streetNumber) {
		this.streetNumber = streetNumber;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getPostCode() {
		return postCode;
	}

	public void setPostCode(String postCode) {
		this.postCode = postCode;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
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
		if (country != null ? !country.equals(address.country) : address.country != null) return false;
		return true;
	}

	@Override public int hashCode() {
		int result = street != null ? street.hashCode() : 0;
		result = 31 * result + (streetNumber != null ? streetNumber.hashCode() : 0);
		result = 31 * result + (city != null ? city.hashCode() : 0);
		result = 31 * result + (postCode != null ? postCode.hashCode() : 0);
		result = 31 * result + (country != null ? country.hashCode() : 0);
		return result;
	}
}
