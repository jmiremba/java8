package com.strive.learning.java8.features;

import java.util.Arrays;
import java.util.ListResourceBundle;
import java.util.Locale;
import java.util.ResourceBundle;

import com.strive.learning.java8.features.locale.Translations;

@SuppressWarnings("unused")
public class C19_Localization {

	public static void main(String[] args) {
		listResourceBundles();
	}
	
	private static void listResourceBundles() {
		Locale currentLocale = Locale.getDefault();
		ResourceBundle resourceBundle = ResourceBundle.getBundle(Translations.class.getName(), currentLocale);
		System.out.println("English = "+resourceBundle.getString("HELLO"));
		resourceBundle = ResourceBundle.getBundle(Translations.class.getName(), Locale.FRANCE);
		System.out.println("French = "+resourceBundle.getString("HELLO"));
	}

	private static void propertyResourceBundles() {
		Locale currentLocale = Locale.getDefault();
		ResourceBundle resourceBundle = ResourceBundle.getBundle("locale/ResourceBundle", currentLocale);
		System.out.println("English = "+resourceBundle.getString("HELLO"));
		resourceBundle = ResourceBundle.getBundle("locale/ResourceBundle", Locale.FRANCE);
		System.out.println("French = "+resourceBundle.getString("HELLO"));
	}

	private static void generalLocales() {
		System.out.println("Default locale = "+Locale.getDefault());
		
		// All available locales
		String fmt = "%10s %-25s %5s %-12s %5s %5s %s";
		System.out.println(String.format(fmt, "COUNTRY", "D.COUNTRY", "LANG", "D.LANGUAGE", "VAR", "D.VAR", "STRING"));
		for(Locale locale : Locale.getAvailableLocales()) {
			System.out.println(String.format(fmt, 
					locale.getCountry(), locale.getDisplayCountry(), 
					locale.getLanguage(), locale.getDisplayLanguage(), 
					locale.getVariant(), locale.getDisplayVariant(), 
					locale.toString()));
		}
		
		// French locales
		System.out.println("\nFRENCH LOCALES:");
		Arrays.stream(Locale.getAvailableLocales())
			.filter(locale -> locale.getLanguage().equals("fr"))
			.forEach(l -> System.out.printf("Locale code: %s and it stands for %s%n", l, l.getDisplayName()));
		
		// Details
		System.out.println("\nLocale.CANADA_FRENCH:");
		Locale locale = Locale.CANADA_FRENCH;
		System.out.printf("> Locale is %s %n", locale);
		System.out.printf("> Language code is %s and the name is %s %n", locale.getLanguage(),
				locale.getDisplayLanguage());
		System.out.printf("> Country code is %s and the name is %s %n", locale.getCountry(),
				locale.getDisplayCountry());
		System.out.printf("> Variant code is %s and the name is %s %n", locale.getVariant(), 
				locale.getDisplayVariant());
		
		// More ways of creating a locale
		System.out.println("\nCREATING ITALIAN LOCALE:");
		Locale localeIT = new Locale("it");
		System.out.println("> New: "+localeIT);
		localeIT = Locale.forLanguageTag("it");
		System.out.println("> forLanguageTag: "+localeIT);
		localeIT = new Locale.Builder().setLanguageTag("it").build();
		System.out.println("> Builder: "+localeIT);
	}

}
