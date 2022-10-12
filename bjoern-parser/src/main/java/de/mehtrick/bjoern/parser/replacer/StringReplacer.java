package de.mehtrick.bjoern.parser.replacer;

import java.util.Map;
import java.util.Map.Entry;

class StringReplacer {

	protected static String replaceWithValues(String string, Map<String, String> replacements) {
		for (Entry<String, String> replacement : replacements.entrySet()) {
			string = string.replace(replacement.getKey(), replacement.getValue());
		}
		return string;
	}
}
