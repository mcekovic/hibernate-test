package org.strangeforest.hibernate.repositories;

import javax.persistence.*;

import org.springframework.transaction.annotation.*;

public abstract class JPARepository<E> {

	protected final Class<E> entityClass;
	@PersistenceContext protected EntityManager em;

	protected static final String HINT_CACHEABLE = "org.hibernate.cacheable";

	protected JPARepository(Class<E> entityClass) {
		this.entityClass = entityClass;
	}

	@Transactional(readOnly = true)
	public E findById(Object id) {
		E entity = em.find(entityClass, id);
		if (entity == null)
			throw new EntityNotFoundException();
		return entity;
	}

	@Transactional
	public void create(E entity) {
		em.persist(entity);
	}

	@Transactional
	public void save(E entity) {
		em.merge(entity);
	}

	@Transactional
	public void deleteById(Object id) {
		E entity = em.find(entityClass, id);
		if (entity != null)
			em.remove(entity);
	}
}
