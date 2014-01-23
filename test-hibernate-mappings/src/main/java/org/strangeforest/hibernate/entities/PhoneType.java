package org.strangeforest.hibernate.entities;

import javax.persistence.*;

import org.strangeforest.hibernate.util.*;

public enum PhoneType implements Coded {
	HOME("H"),
	MOBILE("M");

	private final String code;

	PhoneType(String code) {
		this.code = code;
	}

	@Override public String code() {
		return code;
	}

	@Converter(autoApply = true)
	public static class PhoneTypeConverter extends CodedEnumConverter<PhoneType> implements AttributeConverter<PhoneType, String> {}
}
