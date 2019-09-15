package de.mehtrick.bjoern.parser;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.text.WordUtils;

public class BjoernTextParser {

	public static final String BLANK_REPLACEMENT = "";

	private static String parseTextToCamelCase(String text) {
		return WordUtils.capitalizeFully(text).trim().replaceAll(" ", BLANK_REPLACEMENT);
	}

	private static String removeInvalidChars(String formattedName) {
		List<String> removeChars = Arrays.asList("(", ")", ".", ",", "-", "_", ":", "=", "+");
		for (String removeChar : removeChars) {
			formattedName = StringUtils.remove(formattedName, removeChar);
		}
		return formattedName;

	}

	public static String parseText(String name) {
		String formattedText = removeInvalidChars(name);
		formattedText = parseTextToCamelCase(formattedText);
		return formattedText;
	}

}
