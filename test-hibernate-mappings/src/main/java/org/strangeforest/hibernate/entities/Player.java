package org.strangeforest.hibernate.entities;

import java.math.*;
import java.time.*;
import java.util.*;
import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.OrderBy;
import javax.validation.*;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.*;

import static java.util.stream.Collectors.*;
import static javax.persistence.CascadeType.*;
import static javax.persistence.FetchType.*;
import static org.hibernate.annotations.CacheConcurrencyStrategy.*;

@Entity
@NamedEntityGraphs(@NamedEntityGraph(name = "WithTitles", attributeNodes = @NamedAttributeNode("titles")))
@DynamicUpdate(true)
public class Player {

	@Id @GeneratedValue private long id;
	@Column(unique = true) private String name;
	private LocalDate dateOfBirth;
	@Embedded private Address residence;
	@Embedded @Valid private EMailAddress eMail;

	@ElementCollection(fetch = EAGER) @OrderBy("country,city,postCode,street,streetNumber")
	@Cache(usage = READ_WRITE)
	private List<Address> addresses = new ArrayList<>();

	@ElementCollection(fetch = EAGER) @OrderBy("phone_type")
	@MapKeyColumn(name="phone_type") @Column(name="phone") @Cache(usage = READ_WRITE)
	private Map<PhoneType, String> phones = new HashMap<>();

	@OneToMany(mappedBy = "player", fetch = LAZY, cascade = ALL) @OrderBy("titleCount desc")
	@Cache(usage = READ_WRITE)
	private List<PlayerTitle> titles = new ArrayList<>();

	@ElementCollection(fetch = LAZY) @OrderBy("sponsor.id")
	@Cache(usage = READ_WRITE)
	private List<PlayerSponsorship> sponsorships = new ArrayList<>();

	@OneToOne(fetch = LAZY)
	private Tournament favouriteTournament;

	public Player() {}

	public Player(String name) {
		this.name = name;
	}

	public Player(long id, String name, EMailAddress eMail) {
		this.id = id;
		this.name = name;
		this.eMail = eMail;
	}

	public long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public LocalDate getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(LocalDate dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public Address getResidence() {
		return residence;
	}

	public void setResidence(Address residence) {
		this.residence = residence;
	}

	public String getEMail() {
		return eMail != null ? eMail.get() : null;
	}

	public void setEMail(String eMail) {
		this.eMail = eMail != null ? new EMailAddress(eMail) : null;
	}

	public List<Address> getAddresses() {
		return addresses;
	}

	public void addAddress(Address address) {
		getAddresses().add(address);
	}

	public Map<PhoneType, String> getPhones() {
		return phones;
	}

	public void addPhone(PhoneType type, String phone) {
		getPhones().put(type, phone);
	}

	public List<PlayerTitle> getTitles() {
		return titles;
	}

	public PlayerTitle getTitle(Tournament tournament) {
		return getTitles().stream().filter(title -> title.getTournamentId() == tournament.getId()).findAny().get();
	}

	public void addTitle(Tournament tournament, int count) {
		getTitles().add(new PlayerTitle(this, tournament, count));
	}

	public void addTitle(long tournamentId, int count) {
		getTitles().add(new PlayerTitle(this, tournamentId, count));
	}

	public int getTitleCount() {
		return (int)getTitles().stream().collect(summarizingInt(PlayerTitle::getTitleCount)).getSum();
	}

	public List<PlayerSponsorship> getSponsorships() {
		return sponsorships;
	}

	public PlayerSponsorship getSponsorship(Sponsor sponsor) {
		return getSponsorships().stream().filter(sponsorship -> sponsorship.getSponsor().getId().equals(sponsor.getId())).findFirst().get();
	}

	public void addSponsorship(Sponsor sponsor, int years, BigDecimal amount) {
		getSponsorships().add(new PlayerSponsorship(sponsor, years, amount));
	}

	public Tournament getFavouriteTournament() {
		return favouriteTournament;
	}

	public void setFavouriteTournament(Tournament favouriteTournament) {
		this.favouriteTournament = favouriteTournament;
	}

	@PostPersist
	private void postPersist() {
		for (PlayerTitle title : titles)
			title.setPlayerId(id);
	}
}
