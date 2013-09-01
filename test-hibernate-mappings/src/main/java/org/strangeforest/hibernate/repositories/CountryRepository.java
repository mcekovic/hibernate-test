package org.strangeforest.hibernate.repositories;

import org.springframework.stereotype.*;
import org.strangeforest.hibernate.entities.*;

@Repository
public class CountryRepository extends JPARepository<Country> {

	public CountryRepository() {
		super(Country.class);
	}
}
