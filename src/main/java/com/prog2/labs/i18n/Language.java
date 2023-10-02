package com.prog2.labs.i18n;

/**
 * The Language Enum
 *
 * @author <a href="mailto:Chailikeg@yahoo.com">Olena Chailik</a>
 */
public enum Language {

	ENGLISH("en", "EN", "USD", 1), 
	FRENCH("fr", "FR", "CAD", 1.25d);

	private String language;
	private String country;
	private String currency;
	private double rate;

	private Language(String language, String country, 
			String currency, double rate) {
		this.language = language;
		this.country = country;
		this.currency = currency;
		this.rate = rate;
	}

	public String getLanguage() {
		return language;
	}

	public String getCountry() {
		return country;
	}

	public String getCurrency() {
		return currency;
	}

	public double getRate() {
		return rate;
	}
}
