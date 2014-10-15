package org.strangeforest.hibernate.entities;

import java.math.*;
import java.util.*;
import javax.persistence.*;

import static javax.persistence.TemporalType.TIMESTAMP;

@Entity
public class OrderPayment {

	@EmbeddedId private OrderPaymentId id;
	private BigDecimal amount;
	@Temporal(TIMESTAMP) private Calendar timestamp;

	public OrderPayment() {}

	public OrderPayment(Order order, int index, BigDecimal amount) {
		id = new OrderPaymentId(order, index);
		this.amount = amount;
		this.timestamp = Calendar.getInstance();
	}

	public OrderPaymentId getId() {
		return id;
	}

	public int getIndex() {
		return id.getIndex();
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public Calendar getTimestamp() {
		return timestamp;
	}


	// Object Util

	@Override public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		OrderPayment item = (OrderPayment)o;
		return Objects.equals(id, item.id);
	}

	@Override public int hashCode() {
		return Objects.hashCode(id);
	}
}
