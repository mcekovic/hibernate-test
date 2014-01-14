package org.strangeforest.hibernate.entities;

import java.util.*;
import javax.persistence.Entity;
import javax.persistence.*;
import javax.persistence.OrderBy;

import org.hibernate.annotations.Cache;

import static javax.persistence.CascadeType.*;
import static javax.persistence.FetchType.*;
import static javax.persistence.TemporalType.*;
import static org.hibernate.annotations.CacheConcurrencyStrategy.*;

@Entity
@Table(name = "order_table")
public class Order {

	@Id @GeneratedValue private long id;
	@Temporal(TIMESTAMP) private Date created;
	private String description;

	@OneToMany(mappedBy = "id.order", fetch = EAGER, cascade = ALL) @OrderBy("id.index")
	@Cache(usage = READ_WRITE)
	private List<OrderItem> items = new ArrayList<>();

	public Order() {}

	public Order(String description) {
		this.description = description;
		created = new Date();
	}

	public long getId() {
		return id;
	}

	public Date getCreated() {
		return created;
	}

	public String getDescription() {
		return description;
	}

	public List<OrderItem> getItems() {
		return items;
	}

	public void addItem(int index, String name, int count) {
		getItems().add(new OrderItem(this, index, name, count));
	}

	public int getItemCount() {
		int count = 0;
		for (OrderItem item : getItems())
			count += item.getCount();
		return count;
	}


	// Object methods

	@Override public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Order order = (Order)o;
		if (id != order.id) return false;
		if (created != null ? !created.equals(order.created) : order.created != null) return false;
		if (description != null ? !description.equals(order.description) : order.description != null) return false;
		return true;
	}

	@Override public int hashCode() {
		int result = (int)(id ^ (id >>> 32));
		result = 31 * result + (created != null ? created.hashCode() : 0);
		result = 31 * result + (description != null ? description.hashCode() : 0);
		return result;
	}
}
