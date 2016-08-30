package com.strive.learning.java8.features.locale;

import java.util.ListResourceBundle;

public class Translations extends ListResourceBundle {
	private static final Object[][] contents = {
			{ "HELLO", "Hello" }
	};
	@Override
	protected Object[][] getContents() {
		return contents;
	}
}
