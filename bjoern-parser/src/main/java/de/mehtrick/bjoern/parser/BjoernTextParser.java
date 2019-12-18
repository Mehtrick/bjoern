package de.mehtrick.bjoern.parser;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.text.WordUtils;

import java.util.Arrays;
import java.util.List;

public class BjoernTextParser {

	public static final String BLANK_REPLACEMENT = "";

	private static String parseTextToCamelCase(String text) {
		return WordUtils.capitalizeFully(text).trim().replaceAll(" ", BLANK_REPLACEMENT);
	}

	/**
	 * Some characters are invalid in java class or method names, so they need to be assimilated
	 *
	 * @param formattedName
	 * @return
	 */
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
