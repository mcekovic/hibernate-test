package org.strangeforest.hibernate.entities;

import java.io.*;
import javax.persistence.*;

@Embeddable
public class PlayerTitleId implements Serializable {

	private long playerId;
	private long tournamentId;

	public PlayerTitleId() {}

	public PlayerTitleId(long playerId, long tournamentId) {
		this.playerId = playerId;
		this.tournamentId = tournamentId;
	}

	public long getPlayerId() {
		return playerId;
	}

	public void setPlayerId(long playerId) {
		this.playerId = playerId;
	}

	public long getTournamentId() {
		return tournamentId;
	}

	public void setTournamentId(long tournamentId) {
		this.tournamentId = tournamentId;
	}


	// Object Methods

	@Override public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		PlayerTitleId id = (PlayerTitleId)o;
		if (playerId != id.playerId) return false;
		if (tournamentId != id.tournamentId) return false;
		return true;
	}

	@Override public int hashCode() {
		int result = (int)(playerId ^ (playerId >>> 32));
		result = 31 * result + (int)(tournamentId ^ (tournamentId >>> 32));
		return result;
	}

	@Override public String toString() {
		return "PlayerTitleId{" +
				"player=" + playerId +
				", tournament=" + tournamentId +
				'}';
	}
}
