package de.mehtrick.bjoern.parser.reader;

import java.util.Arrays;

public enum BjoernFileExtensions {

	zgr;

	public static String[] getValuesAsString() {
		return Arrays.asList(BjoernFileExtensions.values()).stream().map(v -> v.name()).toArray(String[]::new);
	}
}
