package test.strangeforest.hibernate;

import java.time.*;
import java.util.*;
import javax.persistence.*;
import javax.validation.*;

import org.springframework.beans.factory.annotation.*;
import org.springframework.test.context.*;
import org.springframework.test.context.testng.*;
import org.strangeforest.hibernate.entities.*;
import org.strangeforest.hibernate.repositories.*;
import org.testng.annotations.*;

import static org.assertj.core.api.Assertions.*;

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

		assertThat(getPlayer().getName()).isEqualTo(PLAYER_NAME);
	}

	@Test(dependsOnMethods = "playerIsCreated")
	public void playerDoBIsUpdated() {
		Player player = getPlayer();
		LocalDate dob = LocalDate.of(1987, 5, 2);
		player.setDateOfBirth(dob);
		players.save(player);

		assertThat(getPlayer().getDateOfBirth()).isEqualTo(dob);
	}

	@Test(dependsOnMethods = "playerDoBIsUpdated", dependsOnGroups = "CountryFixture")
	public void playerResidenceIsUpdated() {
		Player player = getPlayer();
		player.setResidence(new Address("Drordza Levijea", "12a", "Monte Karlo", "12345", countries.find("mn")));
		player.setEMail("nole@djoker.net");
		players.save(player);

		assertThat(getPlayer().getResidence().getCity()).isEqualTo("Monte Karlo");
	}

	@Test(dependsOnMethods = "playerResidenceIsUpdated")
	public void playerEMailIsInvalid() throws Throwable {
		Player player = getPlayer();
		player.setEMail("nole@djoker!net");
		assertThatThrownBy(() -> players.save(player)).hasRootCauseInstanceOf(ConstraintViolationException.class);
	}

	@Test(dependsOnMethods = "playerEMailIsInvalid", dependsOnGroups = "CountryFixture")
	public void playerAddressIsAdded() {
		Player player = getPlayer();
		player.addAddress(new Address("Pere Perica", "21b", "Zagubica", "12345", countries.find("sr")));
		players.save(player);

		List<Address> addresses = getPlayer().getAddresses();
		assertThat(addresses).hasSize(1);
		assertThat(addresses).extracting(Address::getCity).contains("Zagubica");
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
		assertThat(phones.entrySet()).hasSize(2);
		assertThat(phones).containsEntry(PhoneType.MOBILE, phoneNumber1);
		assertThat(phones).containsEntry(PhoneType.HOME, phoneNumber2);
	}

	@Test(dependsOnMethods = "playerPhonesAreAdded")
	public void playerPhoneIsUpdated() {
		String phoneNumber1 = "+381 64 2134-031";
		Player player = getPlayer();
		player.addPhone(PhoneType.MOBILE, phoneNumber1);
		players.save(player);

		Map<PhoneType, String> phones = getPlayer().getPhones();
		assertThat(phones.entrySet()).hasSize(2);
		assertThat(phones).containsEntry(PhoneType.MOBILE, phoneNumber1);
	}

	@Test(dependsOnMethods = "playerPhoneIsUpdated", dependsOnGroups = "TournamentFixture")
	public void playerFavouriteTournamentIsSet() {
		Player player = getPlayer();
		player.setFavouriteTournament(tournaments.findByName("Australian Open"));
		players.save(player);

		assertThat(player.getFavouriteTournament().getName()).isEqualTo("Australian Open");
	}

	@Test(dependsOnMethods = "playerFavouriteTournamentIsSet")
	public void playerIsFoundByName() {
		Player player = players.findByName(PLAYER_NAME);
		Player player2 = players.findByName(PLAYER_NAME);

		assertThat(player.getName()).isEqualTo(PLAYER_NAME);
		assertThat(player2.getName()).isEqualTo(PLAYER_NAME);
	}

	@Test(dependsOnMethods = "playerIsFoundByName")
	public void playerIsQueriedByName() {
		Player player = players.queryOne("select p from Player p where p.name = :name", params("name", PLAYER_NAME), "WithTitles");
		assertThat(player.getPhones().entrySet()).hasSize(2);
		assertThat(player.getAddresses()).hasSize(1);
		assertThat(player.getTitles()).isEmpty();
	}

	@Test(dependsOnMethods = "playerIsQueriedByName", expectedExceptions = EntityNotFoundException.class)
	public void playerIdDeleted() {
		players.deleteById(playerId);
		getPlayer();
		fail("Player is not deleted.");
	}


	// Util

	private Player getPlayer() {
		return players.find(playerId);
	}

	private Map<String, Object> params(String name, Object value) {
		return Collections.singletonMap(name, value);
	}
}
