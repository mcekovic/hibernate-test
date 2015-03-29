package test.strangeforest.hibernate;

import org.springframework.beans.factory.annotation.*;
import org.springframework.test.context.*;
import org.springframework.test.context.testng.*;
import org.springframework.transaction.support.*;
import org.strangeforest.hibernate.entities.*;
import org.strangeforest.hibernate.repositories.*;
import org.strangeforest.util.*;
import org.testng.annotations.*;

import static org.assertj.core.api.Assertions.*;

@ContextConfiguration(locations = "classpath:test-hibernate.xml")
public class PlayerTitlesIT extends AbstractTestNGSpringContextTests {

	@Autowired private PlayerRepository players;
	@Autowired private TournamentRepository tournaments;
	@Autowired TransactionTemplate transactionTemplate;
	private long playerId;

	private static final String PLAYER_NAME = "Novak Djokovic 2";

	@Test(dependsOnGroups = "TournamentFixture")
	public void createPlayerWithTitles() {
		final Reference<Player> playerRef = new Reference<>();
		transactionTemplate.execute(status -> {
			Player player = new Player(PLAYER_NAME);
			Tournament australianOpen = tournaments.findByName("Australian Open");
			player.addTitle(australianOpen, 1);
			players.create(player);
			playerId = player.getId();
			playerRef.set(player);
			return player;
		});

		Player player = playerRef.get();
		assertThat(player.getTitleCount(), is(equalTo(1)));
		assertThat(player.getTitles().get(0).getPlayerId(), is(not(0L)));
	}

	@Test(dependsOnMethods = "createPlayerWithTitles", dependsOnGroups = "TournamentFixture")
	public void playerTitlesAreAdded() {
		Tournament australianOpen = tournaments.findByName("Australian Open");
		Tournament wimbledon = tournaments.findByName("Wimbledon");
		Tournament usOpen = tournaments.findByName("US Open");
		Player player = getPlayerWithTitles();
		player.getTitle(australianOpen).setTitleCount(4);
		player.addTitle(wimbledon, 1);
		player.addTitle(usOpen, 1);
		players.save(player);

		assertThat(getPlayerWithTitles().getTitleCount()).isEqualTo(6);
		assertThat(getPlayerWithTitles().getTitleCount()).isEqualTo(6);
	}


	// Util

	private Player getPlayerWithTitles() {
		return players.findByIdWithTitles(playerId);
	}
}
