package org.strangeforest.hibernate.entities;

import javax.persistence.*;

public enum TournamentType {
	GRAND_SLAM("GS"),
	ATP_MASTERS_1000("ATP1000"),
	ATP_500("ATP500"),
	ATP_250("ATP250");

	private final String code;

	TournamentType(String code) {
		this.code = code;
	}

	public String code() {
		return code;
	}

	public static TournamentType fromCode(String code) {
		for (TournamentType tournamentType : values()) {
			if (tournamentType.code.equals(code))
				return tournamentType;
		}
		throw new IllegalArgumentException("Invalid PhoneType code: " + code);
	}

	@Converter(autoApply = true)
	public static class TournamentTypeConverter implements AttributeConverter<TournamentType, String> {

		@Override public String convertToDatabaseColumn(TournamentType tournamentType) {
			return tournamentType.code();
		}

		@Override public TournamentType convertToEntityAttribute(String code) {
			return fromCode(code);
		}
	}
}
