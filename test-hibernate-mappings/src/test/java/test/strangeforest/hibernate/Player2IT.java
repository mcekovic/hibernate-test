package test.strangeforest.hibernate;

import org.springframework.beans.factory.annotation.*;
import org.springframework.test.context.*;
import org.springframework.test.context.testng.*;
import org.strangeforest.hibernate.entities.*;
import org.strangeforest.hibernate.entities.projected.*;
import org.strangeforest.hibernate.repositories.*;
import org.testng.annotations.*;

import static org.assertj.core.api.Assertions.*;

@ContextConfiguration(locations = "classpath:test-hibernate.xml")
public class Player2IT extends AbstractTestNGSpringContextTests {

	@Autowired private Player2Repository players;
	private long playerId;

	private static final String PLAYER_NAME = "Novak Djokovic 22";

	@Test
	public void playerIsCreated() {
		Player2 player = new Player2(PLAYER_NAME);
		players.create(player);
		playerId = player.getId();

		assertThat(getPlayer().getName()).isEqualTo(PLAYER_NAME);
	}


	@Test(dependsOnMethods = "playerIsCreated")
	public void playerIdDeleted() {
		players.deleteById(playerId);
	}


	// Util

	private Player2 getPlayer() {
		return players.find(playerId);
	}
}
