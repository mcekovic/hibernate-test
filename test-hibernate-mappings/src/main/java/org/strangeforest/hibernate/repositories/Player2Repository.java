package org.strangeforest.hibernate.repositories;

import org.springframework.stereotype.*;
import org.strangeforest.hibernate.entities.projected.*;

@Repository
public class Player2Repository extends JPARepository2<Player2> {

	public Player2Repository() {
		super(Player2.class);
	}
}
