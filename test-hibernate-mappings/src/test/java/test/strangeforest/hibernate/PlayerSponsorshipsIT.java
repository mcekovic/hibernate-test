package test.strangeforest.hibernate;

import java.math.*;

import org.springframework.beans.factory.annotation.*;
import org.springframework.test.context.*;
import org.springframework.test.context.testng.*;
import org.springframework.transaction.*;
import org.springframework.transaction.support.*;
import org.strangeforest.hibernate.entities.*;
import org.strangeforest.hibernate.repositories.*;
import org.testng.annotations.*;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

@ContextConfiguration(locations = "classpath:test-hibernate.xml")
public class PlayerSponsorshipsIT extends AbstractTestNGSpringContextTests {

	@Autowired private PlayerRepository players;
	@Autowired private SponsorRepository sponsors;
	@Autowired TransactionTemplate transactionTemplate;
	private long playerId;

	private static final String PLAYER_NAME = "Roger Federer";

	@Test(dependsOnGroups = {"SponsorsFixture"})
	public void createPlayerWithSponsorship() {
		transactionTemplate.execute(new TransactionCallback<Player>() {
			@Override public Player doInTransaction(TransactionStatus status) {
				Player player = new Player(PLAYER_NAME);
				Sponsor nike = sponsors.findById("NIKE");
				player.addSponsorship(nike, 5, new BigDecimal(1e7));
				players.create(player);
				playerId = player.getId();
				return player;
			}
		});
	}

	@Test(dependsOnMethods = "createPlayerWithSponsorship")
	public void playerSponsorshipIsUpdated() {
		Player player = getPlayer();
		Sponsor nike = sponsors.findById("NIKE");
		PlayerSponsorship sponsorship = player.findSponsorship(nike);
		sponsorship.setYears(10);
		sponsorship.setAmount(new BigDecimal(2e7));
		players.save(player);

		assertThat(getPlayer().findSponsorship(nike).getYears(), is(equalTo(10)));
		assertThat(getPlayer().findSponsorship(nike).getAmount(), comparesEqualTo(new BigDecimal(2e7)));
	}


	// Util

	private Player getPlayer() {
		return players.findByIdWithSponsorships(playerId);
	}
}
