package org.strangeforest.hibernate.repositories;

import org.springframework.stereotype.*;
import org.strangeforest.hibernate.entities.*;

@Repository
public class SponsorRepository extends JPARepository<Sponsor> {

	public SponsorRepository() {
		super(Sponsor.class);
	}
}
