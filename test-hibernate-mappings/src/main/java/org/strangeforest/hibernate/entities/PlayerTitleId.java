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


	// Object Methods

	@Override public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		PlayerTitleId id = (PlayerTitleId)o;
		if (player != null ? player.getId() != id.player.getId() : id.player != null) return false;
		if (tournament != null ? tournament.getId() != id.tournament.getId() : id.tournament != null) return false;
		return true;
	}

	@Override public int hashCode() {
		int result = player != null ? player.hashCode() : 0;
		result = 31 * result + (tournament != null ? tournament.hashCode() : 0);
		return result;
	}

	@Override public String toString() {
		return "PlayerTitleId{" +
				"player=" + player.getId() +
				", tournament=" + tournament.getId() +
				'}';
	}
}
