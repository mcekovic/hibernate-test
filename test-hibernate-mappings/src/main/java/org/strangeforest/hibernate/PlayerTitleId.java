package org.strangeforest.hibernate;

import java.io.*;
import javax.persistence.*;

import static javax.persistence.FetchType.*;

@Embeddable
public class PlayerTitleId implements Serializable {

	@ManyToOne(fetch = LAZY) private Player player;
	@ManyToOne(fetch = LAZY) private Tournament tournament;

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
