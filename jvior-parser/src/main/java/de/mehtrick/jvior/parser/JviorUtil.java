package de.mehtrick.jvior.parser;

import org.apache.commons.text.WordUtils;

public class JviorUtil {

	public static String parseTextToCamelCase(String text) {
		return WordUtils.capitalizeFully(text).trim().replaceAll(" ", "");
	}

}
