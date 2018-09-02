package de.mehtrick.jvior.parser.reader;

import java.util.Arrays;

public enum JviorFileExtensions {

	yaml, yml;

	public static String[] getValuesAsString() {
		return Arrays.asList(JviorFileExtensions.values()).stream().map(v -> v.name()).toArray(String[]::new);
	}
}
