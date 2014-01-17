package org.strangeforest.hibernate.entities;

import java.io.*;
import javax.persistence.*;

@Embeddable
public class OrderPaymentId implements Serializable {

	@ManyToOne private Order order;
	private int index;

	public OrderPaymentId() {}

	public OrderPaymentId(Order order, int index) {
		this.order = order;
		this.index = index;
	}

	public Order getOrder() {
		return order;
	}

	public int getIndex() {
		return index;
	}


	// Object methods

	@Override public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		OrderPaymentId id = (OrderPaymentId)o;
		if (order != null ? order.equals(id.order) : id.order != null) return false;
		if (index != id.index) return false;
		return true;
	}

	@Override public int hashCode() {
		int result = order != null ? order.hashCode() : 0;
		result = 31 * result + index;
		return result;
	}
}
