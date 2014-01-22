package org.strangeforest.hibernate.entities;

import javax.persistence.*;

public enum PhoneType {
	HOME("H"),
	MOBILE("M");

	private final String code;

	PhoneType(String code) {
		this.code = code;
	}

	public String code() {
		return code;
	}

	public static PhoneType fromCode(String code) {
		for (PhoneType phoneType : values()) {
			if (phoneType.code.equals(code))
				return phoneType;
		}
		throw new IllegalArgumentException("Invalid PhoneType code: " + code);
	}

	@Converter(autoApply = true)
	public static class PhoneTypeConverter implements AttributeConverter<PhoneType, String> {

		@Override public String convertToDatabaseColumn(PhoneType phoneType) {
			return phoneType.code();
		}

		@Override public PhoneType convertToEntityAttribute(String code) {
			return fromCode(code);
		}
	}
}
