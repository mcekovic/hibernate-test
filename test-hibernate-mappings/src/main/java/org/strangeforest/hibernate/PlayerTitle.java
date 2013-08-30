package org.strangeforest.hibernate;

import javax.persistence.*;

@Entity
public class PlayerTitle {

	@EmbeddedId PlayerTitleId playerTitleId;
	@Column int titleCount;

	public PlayerTitle() {}

	public PlayerTitle(Player player, Tournament tournament, int titleCount) {
		this.playerTitleId = new PlayerTitleId(player, tournament);
		this.titleCount = titleCount;
	}

	public PlayerTitleId getPlayerTitleId() {
		return playerTitleId;
	}

	public void setPlayerTitleId(PlayerTitleId playerTitleId) {
		this.playerTitleId = playerTitleId;
	}

	public int getTitleCount() {
		return titleCount;
	}

	public void setTitleCount(int titleCount) {
		this.titleCount = titleCount;
	}
}
