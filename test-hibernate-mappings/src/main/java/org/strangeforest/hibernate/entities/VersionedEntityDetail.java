package org.strangeforest.hibernate.entities;

import javax.persistence.*;
import javax.persistence.Entity;

import org.hibernate.annotations.*;

@Entity
@DynamicUpdate(true)
public class VersionedEntityDetail {

	@Id @GeneratedValue private long id;
	private String name;

	@ManyToOne private VersionedEntity parent;

	public VersionedEntityDetail() {}

	public VersionedEntityDetail(String name, VersionedEntity parent) {
		this.name = name;
		this.parent = parent;
	}

	public long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public VersionedEntity getParent() {
		return parent;
	}
}
