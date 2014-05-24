package org.strangeforest.hibernate.repositories;

import org.springframework.stereotype.*;
import org.strangeforest.hibernate.entities.*;

@Repository
public class VersionedEntityRepository extends JPARepository<VersionedEntity> {

	public VersionedEntityRepository() {
		super(VersionedEntity.class);
	}
}
