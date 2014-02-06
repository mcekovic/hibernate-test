package test.strangeforest.hibernate;

import org.springframework.beans.factory.annotation.*;
import org.springframework.test.context.*;
import org.springframework.test.context.testng.*;
import org.springframework.transaction.support.*;
import org.strangeforest.hibernate.entities.*;
import org.strangeforest.hibernate.repositories.*;
import org.testng.annotations.*;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

@ContextConfiguration(locations = "classpath:test-hibernate.xml")
public class PlayerTitlesIT extends AbstractTestNGSpringContextTests {

	@Autowired private PlayerRepository players;
	@Autowired private TournamentRepository tournaments;
	@Autowired TransactionTemplate transactionTemplate;
	private long playerId;

	private static final String PLAYER_NAME = "Novak Djokovic 2";

	@Test(dependsOnGroups = "TournamentFixture")
	public void createPlayerWithTitles() {
		transactionTemplate.execute(status -> {
			Player player = new Player(PLAYER_NAME);
			Tournament australianOpen = tournaments.findByName("Australian Open");
			player.addTitle(australianOpen, 1);
			players.create(player);
			playerId = player.getId();
			return player;
		});
	}

	@Test(dependsOnMethods = "createPlayerWithTitles", dependsOnGroups = "TournamentFixture")
	public void playerTitlesAreAdded() {
		Tournament australianOpen = tournaments.findByName("Australian Open");
		Tournament wimbledon = tournaments.findByName("Wimbledon");
		Tournament usOpen = tournaments.findByName("US Open");
		Player player = getPlayerWithTitles();
		player.findTitle(australianOpen).setTitleCount(4);
		player.addTitle(wimbledon, 1);
		player.addTitle(usOpen, 1);
		players.save(player);

		assertThat(getPlayerWithTitles().getTitleCount(), is(equalTo(6)));
		assertThat(getPlayerWithTitles().getTitleCount(), is(equalTo(6)));
	}


	// Util

	private Player getPlayerWithTitles() {
		return players.findByIdWithTitles(playerId);
	}
}
