package com.prog2.labs.i18n;

import java.util.ResourceBundle;

/**
 * The Library Locale class.
 *   Set Language and manage Locale
 *   LibraryLocale is Singleton
 *
 * @author <a href="mailto:Chailikeg@yahoo.com">Olena Chailik</a>
 */
public class LibraryLocale {

	private static final String PROPERTIES_PATTERN = "ApplicationMessages";
    private static LibraryLocale instance;
    private static ResourceBundle localization;
    private static Language lang;

    /**
     * Constructor LibraryLocale
     *  access is closed
     * 
     * @param lang
     */
    private LibraryLocale(Language lang) {
        this.setLanguage(lang);
    }

    /**
     * Method getLocale
     *   get Singleton LinraryLocale Instance
     *   
     * @param lang
     * @return LibraryLocale Singleton Instance
     */
    public static LibraryLocale createLocale(Language lang) {
        if (instance == null) {
            instance = new LibraryLocale(lang);
        }

        return instance;
    }

    /**
     * Method set Language
     * 
     * @param lang
     */
    public void setLanguage(Language lang) {
    	this.lang = lang;
        localization = ResourceBundle.getBundle(PROPERTIES_PATTERN, this.getLocaleByLanguage(lang));
    }

    /**
     * Method getValueByName
     *   get Value from localized properties file
     *   
     * @param name
     * @return value String
     */
    public String get(String name) {
        return localization.getString(name);
    }

    public static Language getActiveLanguage() {
    	return lang;
    }
    /**
     * Method getLocaleByLanguage
     * 
     * @param lang
     * @return java.util.Locale
     */
    private java.util.Locale getLocaleByLanguage(Language lang) {
        return new java.util.Locale(lang.getCountry(), lang.getLanguage());
    }
}