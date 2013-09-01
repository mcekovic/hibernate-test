package org.strangeforest.hibernate.entities;

import java.io.*;
import javax.persistence.*;

@Embeddable
public class PlayerTitleId implements Serializable {

	@ManyToOne private Player player;
	@ManyToOne private Tournament tournament;

	public PlayerTitleId() {}

	public PlayerTitleId(Player player, Tournament tournament) {
		this.player = player;
		this.tournament = tournament;
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public Tournament getTournament() {
		return tournament;
	}

	public void setTournament(Tournament tournament) {
		this.tournament = tournament;
	}

	@Override public String toString() {
		return "PlayerTitleId{" +
				"player=" + player.getId() +
				", tournament=" + tournament.getId() +
				'}';
	}
}
