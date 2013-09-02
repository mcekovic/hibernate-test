package org.strangeforest.hibernate.entities;

import java.io.*;
import javax.persistence.*;

@Embeddable
public class OrderItemId implements Serializable {

	@ManyToOne private Order order;
	private int index;

	public OrderItemId() {}

	public OrderItemId(Order order, int index) {
		this.order = order;
		this.index = index;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}


	// Object methods

	@Override public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		OrderItemId that = (OrderItemId)o;
		if (index != that.index) return false;
		if (order != null ? order.getId() != that.order.getId() : that.order != null) return false;
		return true;
	}

	@Override public int hashCode() {
		int result = order != null ? order.hashCode() : 0;
		result = 31 * result + index;
		return result;
	}
}
