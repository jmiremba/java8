package com.strive.learning.java8.features.locale;

import java.util.ListResourceBundle;

public class Translations_fr extends ListResourceBundle {
	private static final Object[][] contents = {
			{ "HELLO", "Bonjour" }
	};
	@Override
	protected Object[][] getContents() {
		return contents;
	}
}
