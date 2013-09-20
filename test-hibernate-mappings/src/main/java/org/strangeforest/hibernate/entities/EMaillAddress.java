package org.strangeforest.hibernate.entities;

import javax.persistence.*;
import javax.validation.constraints.*;

@Embeddable
public class EMaillAddress {

	@Pattern(regexp = "[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}")
	private String eMailAddress;

	public EMaillAddress() {}

	public EMaillAddress(String eMailAddress) {
		this.eMailAddress = eMailAddress;
	}

	public String get() {
		return eMailAddress;
	}

	@Override public String toString() {
		return eMailAddress;
	}
}
