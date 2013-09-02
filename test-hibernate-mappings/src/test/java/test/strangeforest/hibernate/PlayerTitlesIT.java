package test.strangeforest.hibernate;

import org.springframework.beans.factory.annotation.*;
import org.springframework.test.context.*;
import org.springframework.test.context.testng.*;
import org.strangeforest.hibernate.entities.*;
import org.strangeforest.hibernate.repositories.*;
import org.testng.annotations.*;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

@ContextConfiguration(locations = "classpath:test-hibernate.xml")
public class PlayerTitlesIT extends AbstractTestNGSpringContextTests {

	@Autowired private PlayerRepository players;
	@Autowired private TournamentRepository tournaments;
	private long playerId;

	private static final String PLAYER_NAME = "Novak Djokovic 2";

	@BeforeClass
	public void setUp() {
		createPlayer();
	}

	private void createPlayer() {
		Player player = new Player(PLAYER_NAME);
		players.create(player);
		playerId = player.getId();
	}

	@Test(dependsOnGroups = {"TournamentFixture", "CountryFixture"})
	public void playerTitlesAreAdded() {
		Tournament australianOpen = tournaments.findByName("Australian Open");
		Tournament wimbledon = tournaments.findByName("Wimbledon");
		Tournament usOpen = tournaments.findByName("US Open");
		Player player = getPlayerWithTitles();
		player.addTitle(australianOpen, 4);
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
