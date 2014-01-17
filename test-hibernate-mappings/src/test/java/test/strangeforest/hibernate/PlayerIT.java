package test.strangeforest.hibernate;

import java.util.*;
import javax.persistence.*;
import javax.validation.*;

import org.joda.time.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.test.context.*;
import org.springframework.test.context.testng.*;
import org.strangeforest.hibernate.entities.*;
import org.strangeforest.hibernate.repositories.*;
import org.strangeforest.util.*;
import org.testng.annotations.*;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

@ContextConfiguration(locations = "classpath:test-hibernate.xml")
public class PlayerIT extends AbstractTestNGSpringContextTests {

	@Autowired private PlayerRepository players;
	@Autowired private CountryRepository countries;
	@Autowired private TournamentRepository tournaments;
	private long playerId;

	private static final String PLAYER_NAME = "Novak Djokovic";

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

	@Test(dependsOnMethods = "playerDoBIsUpdated", dependsOnGroups = "CountryFixture")
	public void playerResidenceIsUpdated() {
		Player player = getPlayer();
		player.setResidence(new Address("Drordza Levijea", "12a", "Monte Karlo", "12345", countries.find("mn")));
		player.setEMail("nole@djoker.net");
		players.save(player);

		assertThat(getPlayer().getResidence().getCity(), is(equalTo("Monte Karlo")));
	}

	@Test(dependsOnMethods = "playerResidenceIsUpdated")
	public void playerEMailIsInvalid() {
		Player player = getPlayer();
		player.setEMail("nole@djoker!net");
		try {
			players.save(player);
			fail("E-Mail should be invalid.");
		}
		catch (Exception ex) {
			assertThat(ExceptionUtil.getRootCause(ex), instanceOf(ConstraintViolationException.class));
		}
	}

	@Test(dependsOnMethods = "playerEMailIsInvalid", dependsOnGroups = "CountryFixture")
	public void playerAddressIsAdded() {
		Player player = getPlayer();
		player.addAddress(new Address("Pere Perica", "21b", "Zagubica", "12345", countries.find("sr")));
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
	public void playerPhoneIsUpdated() {
		String phoneNumber1 = "+381 64 2134-031";
		Player player = getPlayer();
		player.addPhone(PhoneType.MOBILE, phoneNumber1);
		players.save(player);

		Map<PhoneType, String> phones = getPlayer().getPhones();
		assertThat(phones.entrySet(), hasSize(2));
		assertThat(phones.get(PhoneType.MOBILE), is(equalTo(phoneNumber1)));
	}

	@Test(dependsOnMethods = "playerPhoneIsUpdated", dependsOnGroups = "TournamentFixture")
	public void playerFavouriteTournamentIsSet() {
		Player player = getPlayer();
		player.setFavouriteTournament(tournaments.findByName("Australian Open"));
		players.save(player);

		assertThat(player.getFavouriteTournament().getName(), is(equalTo("Australian Open")));
	}

	@Test(dependsOnMethods = "playerFavouriteTournamentIsSet")
	public void playerIsFoundByName() {
		Player player = players.findByName(PLAYER_NAME);
		Player player2 = players.findByName(PLAYER_NAME);

		assertThat(player.getName(), is(equalTo(PLAYER_NAME)));
		assertThat(player2.getName(), is(equalTo(PLAYER_NAME)));
	}

	@Test(dependsOnMethods = "playerIsFoundByName")
	public void playerIsQueriedByName() {
		Player player = players.queryOne("select p from Player p left join fetch p.titles where p.name = :name", params("name", PLAYER_NAME));
		assertThat(player.getPhones().entrySet(), hasSize(2));
		assertThat(player.getAddresses(), hasSize(1));
		assertThat(player.getTitles(), is(empty()));
	}

	@Test(dependsOnMethods = "playerIsQueriedByName")
	public void playerIdDeleted() {
		players.deleteById(playerId);
		try {
			getPlayer();
			fail("Player is not deleted.");
		}
		catch (EntityNotFoundException ignored) {}
	}


	// Util

	private Player getPlayer() {
		return players.find(playerId);
	}

	private Map<String, Object> params(String name, Object value) {
		return Collections.singletonMap(name, value);
	}
}
