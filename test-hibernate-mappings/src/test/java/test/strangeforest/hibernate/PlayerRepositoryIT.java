package test.strangeforest.hibernate;

import java.io.*;
import java.util.*;

import org.joda.time.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.test.context.*;
import org.springframework.test.context.testng.*;
import org.strangeforest.hibernate.*;
import org.testng.annotations.*;

import com.fasterxml.jackson.databind.*;

import com.finsoft.db.*;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

@Test @ContextConfiguration(locations = "classpath:test-hibernate.xml")
public class PlayerRepositoryIT extends AbstractTestNGSpringContextTests {

	@Autowired private PlayerRepository players;
	@Autowired private TournamentRepository tournaments;
	@Autowired private ConnectionPoolDataSource dataSource;
	private long playerId;

	private static final String PLAYER_NAME = "Novak Djokovic";

	@BeforeSuite
	public void setUpSuite() {
//		ITUtil.deleteFiles(System.getProperty("user.home") + "/.hibernate-test/h2", ".*");
	}

	@BeforeClass
	public void setUp() {
		createTournaments();
	}

	private void createTournaments() {
		tournaments.create(new Tournament("Australian Open"));
		tournaments.create(new Tournament("Roland Garros"));
		tournaments.create(new Tournament("Wimbledon"));
		tournaments.create(new Tournament("US Open"));
	}

	@AfterSuite
	public void cleanUp() throws IOException {
		dataSource.dropConnections();
		new ObjectMapper().writerWithDefaultPrettyPrinter().writeValue(System.out, dataSource.getStatistics());
	}

	@Test
	public void playerIsCreated() {
		Player player = new Player(PLAYER_NAME);
		players.create(player);
		playerId = player.getId();

		assertThat(getPlayer().getName(), is(equalTo(PLAYER_NAME)));
	}

	@Test(dependsOnMethods = "playerIsCreated")
	public void playerDoBIsUpdated() {
		Player player = getPlayer();
		LocalDate dob = new LocalDate(1987, 5, 2);
		player.setDateOfBirth(dob);
		players.save(player);

		assertThat(getPlayer().getDateOfBirth(), is(equalTo(dob)));
	}

	@Test(dependsOnMethods = "playerDoBIsUpdated")
	public void playerResidenceIsUpdated() {
		Player player = getPlayer();
		player.setResidence(new Address("Drordza Levijea", "12a", "Monte Karlo", "12345", "Monako"));
		players.save(player);

		assertThat(getPlayer().getResidence().getCity(), is(equalTo("Monte Karlo")));
	}

	@Test(dependsOnMethods = "playerResidenceIsUpdated")
	public void playerAddressIsAdded() {
		Player player = getPlayer();
		player.addAddress(new Address("Pere Perica", "21b", "Zagubica", "12345", "Srbistan"));
		players.save(player);

		List<Address> addresses = getPlayer().getAddresses();
		assertThat(addresses, hasSize(1));
		assertThat(addresses.get(0).getCity(), is(equalTo("Zagubica")));
	}

	@Test(dependsOnMethods = "playerAddressIsAdded")
	public void playerPhonesAreAdded() {
		String phoneNumber1 = "+381 64 2134-032";
		String phoneNumber2 = "+654 12 3465-432";
		Player player = getPlayer();
		player.addPhone(PhoneType.MOBILE, phoneNumber1);
		player.addPhone(PhoneType.HOME, phoneNumber2);
		players.save(player);

		Map<PhoneType, String> phones = getPlayer().getPhones();
		assertThat(phones.entrySet(), hasSize(2));
		assertThat(phones.get(PhoneType.MOBILE), is(equalTo(phoneNumber1)));
		assertThat(phones.get(PhoneType.HOME), is(equalTo(phoneNumber2)));
	}

	@Test(dependsOnMethods = "playerPhonesAreAdded")
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
	}

	@Test(dependsOnMethods = "playerTitlesAreAdded")
	public void playerIsFoundByName() {
		Player player = players.findByName(PLAYER_NAME);
		Player player2 = players.findByName(PLAYER_NAME);

		assertThat(player.getName(), is(equalTo(PLAYER_NAME)));
		assertThat(player2.getName(), is(equalTo(PLAYER_NAME)));
	}


	// Util

	private Player getPlayer() {
		return players.findById(playerId);
	}

	private Player getPlayerWithTitles() {
		return players.findByIdWithTitles(playerId);
	}
}
