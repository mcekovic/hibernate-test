package org.strangeforest.hibernate.entities;

import java.math.*;
import javax.persistence.*;

import static javax.persistence.FetchType.*;

@Embeddable
public class PlayerSponsorship {

	@ManyToOne(fetch = EAGER) private Sponsor sponsor;
	private int years;
	private BigDecimal amount;

	public PlayerSponsorship() {}

	public PlayerSponsorship(Sponsor sponsor, int years, BigDecimal amount) {
		this.sponsor = sponsor;
		this.years = years;
		this.amount = amount;
	}

	public Sponsor getSponsor() {
		return sponsor;
	}

	public int getYears() {
		return years;
	}

	public void setYears(int years) {
		this.years = years;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}


	// Object methods

	@Override public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof PlayerSponsorship)) return false;
		PlayerSponsorship sponsorship = (PlayerSponsorship)o;
		return getSponsor().getId().equals(sponsorship.getSponsor().getId());
	}

	@Override public int hashCode() {
		return getSponsor().getId().hashCode();
	}
}
