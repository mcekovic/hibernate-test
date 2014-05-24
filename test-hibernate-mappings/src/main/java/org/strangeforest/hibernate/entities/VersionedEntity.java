package org.strangeforest.hibernate.entities;

import java.util.*;
import javax.persistence.Entity;
import javax.persistence.*;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.*;

import static javax.persistence.CascadeType.*;
import static javax.persistence.FetchType.*;
import static org.hibernate.annotations.CacheConcurrencyStrategy.*;

@Entity
@DynamicUpdate(true)
public class VersionedEntity {

	@Id @GeneratedValue private long id;
	private String name;
	@Version int version;

	@OneToMany(mappedBy = "parent", fetch = EAGER, cascade = ALL)
	@OptimisticLock(excluded = false)
	@Cache(usage = READ_WRITE)
	private List<VersionedEntityDetail> details = new ArrayList<>();

	public VersionedEntity() {}

	public VersionedEntity(String name) {
		this.name = name;
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

	public int getVersion() {
		return version;
	}

	public List<VersionedEntityDetail> getDetails() {
		return details;
	}

	public void addDetail(String name) {
		getDetails().add(new VersionedEntityDetail(name, this));
	}

	public VersionedEntityDetail getDetail(String name) {
		return getDetails().stream().filter(detail -> name.equals(detail.getName())).findAny().get();
	}
}
