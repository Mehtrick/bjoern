package de.mehtrick.jvior.parser;

import org.apache.commons.text.WordUtils;

public class JviorTextParser {

	public static final String BLANK_REPLACEMENT = "";

	public static String parseTextToCamelCase(String text) {
		return WordUtils.capitalizeFully(text).trim().replaceAll(" ", BLANK_REPLACEMENT);
	}

}
