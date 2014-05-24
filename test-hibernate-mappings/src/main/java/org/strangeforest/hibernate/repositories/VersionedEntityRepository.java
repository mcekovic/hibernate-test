package org.strangeforest.hibernate.repositories;

import javax.persistence.*;

import org.springframework.stereotype.*;
import org.springframework.transaction.annotation.*;
import org.strangeforest.hibernate.entities.*;

@Repository
public class VersionedEntityRepository extends JPARepository<VersionedEntity> {

	public VersionedEntityRepository() {
		super(VersionedEntity.class);
	}

	@Transactional
	public void changeName(long id, String name) {
		em.find(entityClass, id, LockModeType.NONE).setName(name);
	}

	@Transactional
	public void changeNameOptimistic(long id, String name) {
		em.find(entityClass, id, LockModeType.OPTIMISTIC).setName(name);
	}

	@Transactional
	public void changeNamePessimistic(long id, String name) {
		em.find(entityClass, id, LockModeType.PESSIMISTIC_WRITE).setName(name);
	}
}
