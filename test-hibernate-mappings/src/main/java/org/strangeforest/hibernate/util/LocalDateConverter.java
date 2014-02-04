package org.strangeforest.hibernate.util;

import java.sql.*;
import java.time.*;
import javax.persistence.*;

@Converter(autoApply = true)
public class LocalDateConverter implements AttributeConverter<LocalDate, Date> {

	@Override public Date convertToDatabaseColumn(LocalDate localDate) {
		if (localDate != null) {
			Instant instant = localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant();
			return new Date(instant.toEpochMilli());
		}
		else
			return null;
	}

	@Override public LocalDate convertToEntityAttribute(Date date) {
		if (date != null) {
			Instant instant = Instant.ofEpochMilli(date.getTime());
			return LocalDateTime.ofInstant(instant, ZoneId.systemDefault()).toLocalDate();
		}
		else
			return null;
	}
}
