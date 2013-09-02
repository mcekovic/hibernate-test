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
}
