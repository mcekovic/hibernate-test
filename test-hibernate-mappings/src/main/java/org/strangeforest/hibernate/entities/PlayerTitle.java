package org.strangeforest.hibernate.entities;

import javax.persistence.*;

import static javax.persistence.FetchType.*;

@Entity @IdClass(PlayerTitleId.class)
public class PlayerTitle {

	@Id @Column(name = "player_id") private long playerId;
	@Id @Column(name = "tournament_id") private long tournamentId;

	@ManyToOne @MapsId("playerId") private Player player;
	@ManyToOne(fetch = LAZY) @MapsId("tournamentId") private Tournament tournament;
	private int titleCount;

	public PlayerTitle() {}

	public PlayerTitle(Player player, Tournament tournament, int titleCount) {
		this(player, tournament.getId(), titleCount);
		this.tournament = tournament;
	}

	public PlayerTitle(Player player, long tournamentId, int titleCount) {
		this.playerId = player.getId();
		this.tournamentId = tournamentId;
		this.player = player;
		this.titleCount = titleCount;
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

	public Player getPlayer() {
		return player;
	}

	public Tournament getTournament() {
		return tournament;
	}

	public int getTitleCount() {
		return titleCount;
	}

	public void setTitleCount(int titleCount) {
		this.titleCount = titleCount;
	}


	// Object methods

	@Override public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		PlayerTitle playerTitle = (PlayerTitle)o;
		if (playerId != playerTitle.playerId) return false;
		if (tournamentId != playerTitle.tournamentId) return false;
		return true;
	}

	@Override public int hashCode() {
		int result = (int)(playerId ^ (playerId >>> 32));
		result = 31 * result + (int)(tournamentId ^ (tournamentId >>> 32));
		return result;
	}
}
