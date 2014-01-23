package org.strangeforest.hibernate.entities;

import javax.persistence.*;

import org.strangeforest.hibernate.util.*;

public enum TournamentType implements Coded {
	GRAND_SLAM("GS"),
	ATP_MASTERS_1000("ATP1000"),
	ATP_500("ATP500"),
	ATP_250("ATP250");

	private final String code;

	TournamentType(String code) {
		this.code = code;
	}

	@Override public String code() {
		return code;
	}

	@Converter(autoApply = true)
	public static class TournamentTypeConverter extends CodedEnumConverter<TournamentType> implements AttributeConverter<TournamentType, String> {}
}
