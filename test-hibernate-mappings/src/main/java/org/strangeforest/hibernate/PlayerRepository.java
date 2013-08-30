package org.strangeforest.hibernate;

import javax.persistence.*;

import org.springframework.stereotype.*;
import org.springframework.transaction.annotation.*;

@Repository
public class PlayerRepository {

	@PersistenceContext private EntityManager em;

	private static final String HINT_CACHEABLE = "org.hibernate.cacheable";

	@Transactional(readOnly = true)
	public Player findById(long id) {
		Player player = em.find(Player.class, id);
		if (player == null)
			throw new EntityNotFoundException();
		return player;
	}

	@Transactional(readOnly = true)
	public Player findByName(String name) {
		TypedQuery<Player> query = em.createQuery("from Player p where p.name = :name", Player.class);
		query.setParameter("name", name);
		query.setHint(HINT_CACHEABLE, Boolean.TRUE);
		return query.getSingleResult();
	}

	@Transactional
	public void create(Player player) {
		em.persist(player);
	}

	@Transactional
	public void save(Player player) {
		em.merge(player);
	}

	@Transactional
	public void delete(Player player) {
		em.remove(player);
	}
}
