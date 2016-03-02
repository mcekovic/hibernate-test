package org.strangeforest.hibernate.repositories;

import javax.persistence.*;
import javax.persistence.criteria.*;

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
		Player player = find(id);
		player.getTitles().size();
		return player;
	}


	@Transactional(readOnly = true)
	public Player findByIdWithSponsorships(long id) {
		Player player = find(id);
		player.getSponsorships().size();
		return player;
	}

	@Transactional(readOnly = true)
	public Player findByName(String name) {
		TypedQuery<Player> query = em.createQuery("select p from Player p where p.name = :name", Player.class);
		query.setParameter("name", name);
		query.setHint(HINT_CACHEABLE, Boolean.TRUE);
		return query.getSingleResult();
	}

	@Transactional(readOnly = true)
	public Player findProjected(long id) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Player> query = cb.createQuery(Player.class);
		Root<Player> player = query.from(Player.class);
		query.select(cb.construct(Player.class, player.get("id"), player.get("name"), player.get("eMail")));
		query.where(cb.equal(player.get("id"), cb.parameter(Long.class, "id")));
		TypedQuery<Player> typedQuery = em.createQuery(query);
		typedQuery.setParameter("id", id);
		typedQuery.setHint(HINT_CACHEABLE, Boolean.TRUE);
		return typedQuery.getSingleResult();
	}
}
