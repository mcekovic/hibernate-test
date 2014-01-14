package org.strangeforest.hibernate.entities;

import javax.persistence.*;

@Entity
public class OrderItem {

	@EmbeddedId private OrderItemId id;
	private String name;
	private int count;

	public OrderItem() {}

	public OrderItem(Order order, int index, String name, int count) {
		id = new OrderItemId(order, index);
		this.name = name;
		this.count = count;
	}

	public OrderItemId getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public int getCount() {
		return count;
	}


	// Object Util

	@Override public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		OrderItem item = (OrderItem)o;
		if (id != null ? !id.equals(item.id) : item.id != null) return false;
		if (name != null ? !name.equals(item.name) : item.name != null) return false;
		return true;
	}

	@Override public int hashCode() {
		int result = id != null ? id.hashCode() : 0;
		result = 31 * result + (name != null ? name.hashCode() : 0);
		return result;
	}
}
