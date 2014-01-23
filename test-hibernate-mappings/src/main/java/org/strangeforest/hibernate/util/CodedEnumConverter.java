package org.strangeforest.hibernate.util;

import java.lang.reflect.*;
import javax.persistence.*;

public abstract class CodedEnumConverter<E extends Enum<E> & Coded> implements AttributeConverter<E, String> {

	private volatile Class<E> enumClass;

	@Override public String convertToDatabaseColumn(E e) {
		return e.code();
	}

	@Override public E convertToEntityAttribute(String s) {
		if (enumClass == null)
			enumClass = findEnumClass();
		return decode(enumClass, s);
	}

	private static <E extends Enum<E> & Coded> E decode(Class<E> enumClass, String code) {
		for (E e : enumClass.getEnumConstants()) {
			if (e.code().equals(code))
				return e;
		}
		throw new IllegalArgumentException(String.format("Invalid %1$s code: %2$s", enumClass.getSimpleName(), code));
	}

	private Class<E> findEnumClass() {
		ParameterizedType converterType = (ParameterizedType)getClass().getGenericSuperclass();
		return (Class<E>)converterType.getActualTypeArguments()[0];
	}
}
