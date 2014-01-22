package org.strangeforest.hibernate.entities;

import java.math.*;
import java.util.*;
import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.OrderBy;
import javax.validation.*;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.*;
import org.joda.time.*;
import org.strangeforest.util.*;

import static javax.persistence.CascadeType.*;
import static javax.persistence.FetchType.*;
import static javax.persistence.TemporalType.*;
import static org.hibernate.annotations.CacheConcurrencyStrategy.*;

@Entity
@NamedEntityGraphs(@NamedEntityGraph(name = "WithTitles", attributeNodes = @NamedAttributeNode("titles")))
@DynamicUpdate(true)
public class Player {

	@Id @GeneratedValue private long id;
	@Column(unique = true) private String name;
	@Temporal(DATE) private Date dateOfBirth;
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

	public Player(long id) {
		this.id = id;
	}

	public Player(String name) {
		this.name = name;
	}

	public long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public LocalDate getDateOfBirth() {
		return LocalDate.fromDateFields(dateOfBirth);
	}

	public void setDateOfBirth(LocalDate dateOfBirth) {
		this.dateOfBirth = dateOfBirth.toDate();
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

	public PlayerTitle findTitle(final Tournament tournament) {
		return Algorithms.find(getTitles(), new Predicate<PlayerTitle>() {
			@Override public boolean test(PlayerTitle title) {
				return title.getTournamentId() == tournament.getId();
			}
		});
	}

	public void addTitle(Tournament tournament, int count) {
		getTitles().add(new PlayerTitle(this, tournament, count));
	}

	public void addTitle(long tournamentId, int count) {
		getTitles().add(new PlayerTitle(this, tournamentId, count));
	}

	public int getTitleCount() {
		int count = 0;
		for (PlayerTitle title : getTitles())
			count += title.getTitleCount();
		return count;
	}

	public List<PlayerSponsorship> getSponsorships() {
		return sponsorships;
	}

	public PlayerSponsorship findSponsorship(final Sponsor sponsor) {
		return Algorithms.find(getSponsorships(), new Predicate<PlayerSponsorship>() {
			@Override public boolean test(PlayerSponsorship sponsorship) {
				return sponsorship.getSponsor().getId().equals(sponsor.getId());
			}
		});
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
