package org.strangeforest.hibernate;

import javax.persistence.*;

import org.springframework.stereotype.*;
import org.springframework.transaction.annotation.*;

@Repository
public class TournamentRepository {

	@PersistenceContext private EntityManager em;

	@Transactional
	public void create(Tournament tournament) {
		em.persist(tournament);
	}
}
