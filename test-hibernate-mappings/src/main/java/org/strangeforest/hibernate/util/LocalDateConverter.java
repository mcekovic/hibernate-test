package org.strangeforest.hibernate.util;

import java.sql.*;
import java.time.*;
import javax.persistence.*;

@Converter(autoApply = true)
public class LocalDateConverter implements AttributeConverter<LocalDate, Date> {

	@Override public Date convertToDatabaseColumn(LocalDate localDate) {
		return localDate != null ? Date.valueOf(localDate) : null;
	}

	@Override public LocalDate convertToEntityAttribute(Date date) {
		return date != null ? date.toLocalDate() : null;
	}
}
