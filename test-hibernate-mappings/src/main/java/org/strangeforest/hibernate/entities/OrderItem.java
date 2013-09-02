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

	public void setId(OrderItemId id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}


	// ObjectUtil

	@Override public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		OrderItem orderItem = (OrderItem)o;
		if (id != null ? !id.equals(orderItem.id) : orderItem.id != null) return false;
		if (name != null ? !name.equals(orderItem.name) : orderItem.name != null) return false;
		if (count != orderItem.count) return false;
		return true;
	}

	@Override public int hashCode() {
		int result = id != null ? id.hashCode() : 0;
		result = 31 * result + (name != null ? name.hashCode() : 0);
		result = 31 * result + count;
		return result;
	}
}
