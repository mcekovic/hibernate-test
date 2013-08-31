package org.strangeforest.hibernate.repositories;

import javax.persistence.*;

import org.springframework.stereotype.*;
import org.springframework.transaction.annotation.*;
import org.strangeforest.hibernate.entities.*;

@Repository
public class TournamentRepository extends JPARepository<Tournament> {

	public TournamentRepository() {
		super(Tournament.class);
	}

	@Transactional(readOnly = true)
	public Tournament findByName(String name) {
		TypedQuery<Tournament> query = em.createQuery("from Tournament t where t.name = :name", Tournament.class);
		query.setParameter("name", name);
		query.setHint(HINT_CACHEABLE, Boolean.TRUE);
		return query.getSingleResult();
	}
}
