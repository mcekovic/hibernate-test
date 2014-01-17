package org.strangeforest.hibernate.entities;

import javax.persistence.*;

@Embeddable @Access(AccessType.FIELD)
public class OrderItem {

	private String name;
	private int count;

	public OrderItem() {}

	public OrderItem(String name, int count) {
		this.name = name;
		this.count = count;
	}

	public String getName() {
		return name;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}
}
