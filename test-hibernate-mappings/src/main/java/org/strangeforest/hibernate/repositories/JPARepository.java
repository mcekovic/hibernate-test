package org.strangeforest.hibernate.repositories;

import java.util.*;
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
	public E find(Object id) {
		E entity = em.find(entityClass, id);
		if (entity == null)
			throw new EntityNotFoundException();
		return entity;
	}

	@Transactional(readOnly = true)
	public E queryOne(String query, Map<String, Object> params) {
		TypedQuery<E> typedQuery = em.createQuery(query, entityClass);
		for (Map.Entry<String, Object> param : params.entrySet())
			typedQuery.setParameter(param.getKey(), param.getValue());
		return typedQuery.getSingleResult();
	}

	@Transactional(readOnly = true)
	public List<E> query(String query, Map<String, Object> params) {
		TypedQuery<E> typedQuery = em.createQuery(query, entityClass);
		for (Map.Entry<String, Object> param : params.entrySet())
			typedQuery.setParameter(param.getKey(), param.getValue());
		typedQuery.setHint(HINT_CACHEABLE, Boolean.TRUE);
		return typedQuery.getResultList();
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

	public void evict() {
		em.getEntityManagerFactory().getCache().evict(entityClass);
	}
}
