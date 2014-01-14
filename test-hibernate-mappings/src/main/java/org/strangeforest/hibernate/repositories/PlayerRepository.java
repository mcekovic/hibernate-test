package org.strangeforest.hibernate.repositories;

import javax.persistence.*;

import org.springframework.stereotype.*;
import org.springframework.transaction.annotation.*;
import org.strangeforest.hibernate.entities.*;

@Repository
public class PlayerRepository extends JPARepository<Player> {

	public PlayerRepository() {
		super(Player.class);
	}

	@Transactional(readOnly = true)
	public Player findByIdWithTitles(long id) {
		Player player = findById(id);
		player.getTitles().size();
		return player;
	}


	@Transactional(readOnly = true)
	public Player findByIdWithSponsorships(long id) {
		Player player = findById(id);
		player.getSponsorships().size();
		return player;
	}

	@Transactional(readOnly = true)
	public Player findByName(String name) {
		TypedQuery<Player> query = em.createQuery("from Player p where p.name = :name", Player.class);
		query.setParameter("name", name);
		query.setHint(HINT_CACHEABLE, Boolean.TRUE);
		return query.getSingleResult();
	}
}
