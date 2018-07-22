package de.mehtrick.jvior.parser;

import org.apache.commons.text.WordUtils;

import de.mehtrick.jvior.parser.modell.JviorStatement;

public class JviorTextParser {

	public static final String BLANK_REPLACEMENT = "_";

	public static String parseTextToCamelCase(String text) {
		return WordUtils.capitalizeFully(text).trim().replaceAll(" ", BLANK_REPLACEMENT);
	}

}
