package org.strangeforest.hibernate.repositories;

import java.util.*;
import javax.persistence.*;

import org.springframework.transaction.annotation.*;

public abstract class JPARepository2<E> {

	protected final Class<E> entityClass;
	@PersistenceContext(unitName = "TestPersistenceUnit2") protected EntityManager em;

	protected static final String HINT_GRAPH     = "javax.persistence.fetchgraph";

	protected JPARepository2(Class<E> entityClass) {
		this.entityClass = entityClass;
	}

	@Transactional(readOnly = true, transactionManager = "transactionManager2")
	public E find(Object id) {
		E entity = em.find(entityClass, id);
		if (entity == null)
			throw new EntityNotFoundException();
		return entity;
	}

	public E queryOne(String query, Map<String, Object> params) {
		return queryOne(query, params, null);
	}

	@Transactional(readOnly = true, transactionManager = "transactionManager2")
	public E queryOne(String query, Map<String, Object> params, String entityGraph) {
		return typedQuery(query, params, entityGraph).getSingleResult();
	}

	public List<E> query(String query, Map<String, Object> params) {
		return query(query, params, null);
	}

	@Transactional(readOnly = true, transactionManager = "transactionManager2")
	public List<E> query(String query, Map<String, Object> params, String entityGraph) {
		return typedQuery(query, params, entityGraph).getResultList();
	}

	private TypedQuery<E> typedQuery(String query, Map<String, Object> params, String entityGraph) {
		TypedQuery<E> typedQuery = em.createQuery(query, entityClass);
		for (Map.Entry<String, Object> param : params.entrySet())
			typedQuery.setParameter(param.getKey(), param.getValue());
		if (entityGraph != null)
			typedQuery.setHint(HINT_GRAPH, em.getEntityGraph(entityGraph));
		return typedQuery;
	}

	@Transactional(transactionManager = "transactionManager2")
	public void create(E entity) {
		em.persist(entity);
	}

	@Transactional(transactionManager = "transactionManager2")
	public void save(E entity) {
		em.merge(entity);
	}

	@Transactional(transactionManager = "transactionManager2")
	public void deleteById(Object id) {
		E entity = em.find(entityClass, id);
		if (entity != null)
			em.remove(entity);
	}

	public void evict() {
		em.getEntityManagerFactory().getCache().evict(entityClass);
	}
}
