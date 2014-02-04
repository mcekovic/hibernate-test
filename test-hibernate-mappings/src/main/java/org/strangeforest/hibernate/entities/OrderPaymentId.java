package org.strangeforest.hibernate.entities;

import java.io.*;
import java.util.*;
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
		return Objects.equals(order, id.order) && index == id.index;
	}

	@Override public int hashCode() {
		return 31 * Objects.hashCode(order) + index;
	}
}
