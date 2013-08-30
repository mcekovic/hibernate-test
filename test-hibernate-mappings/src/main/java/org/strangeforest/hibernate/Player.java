package org.strangeforest.hibernate;

import java.util.*;
import javax.persistence.*;
import javax.persistence.Entity;

import org.hibernate.annotations.Cache;
import org.joda.time.*;

import static javax.persistence.FetchType.*;
import static javax.persistence.TemporalType.*;
import static org.hibernate.annotations.CacheConcurrencyStrategy.*;

@Entity
public class Player {

	@Id @GeneratedValue private long id;
	@Column private String name;
	@Column @Temporal(DATE) private Date dateOfBirth;
	@Embedded private Address residence;

	@ElementCollection(fetch = EAGER) @Cache(usage = READ_WRITE)
	@OrderBy("country,city,postCode,street,streetNumber")
	private List<Address> addresses = new ArrayList<>();

	@ElementCollection(fetch = EAGER) @Cache(usage = READ_WRITE)
	@MapKeyEnumerated @MapKeyColumn(name="PHONE_TYPE")	@Column(name="PHONE")
	@OrderBy("PHONE_TYPE")
	private Map<PhoneType, String> phones = new TreeMap<>();

	public Player() {}

	public Player(String name) {
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

	public LocalDate getDateOfBirth() {
		return LocalDate.fromDateFields(dateOfBirth);
	}

	public void setDateOfBirth(LocalDate dateOfBirth) {
		this.dateOfBirth = dateOfBirth.toDate();
	}

	public Address getResidence() {
		return residence;
	}

	public void setResidence(Address residence) {
		this.residence = residence;
	}

	public List<Address> getAddresses() {
		return addresses;
	}

	public void setAddresses(List<Address> address) {
		this.addresses = address;
	}

	public Map<PhoneType, String> getPhones() {
		return phones;
	}

	public void setPhones(Map<PhoneType, String> phones) {
		this.phones = phones;
	}
}
