package org.strangeforest.hibernate.repositories;

import org.springframework.stereotype.*;
import org.strangeforest.hibernate.entities.*;

@Repository
public class OrderRepository extends JPARepository<Order> {

	public OrderRepository() {
		super(Order.class);
	}
}
