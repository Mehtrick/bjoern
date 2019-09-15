package de.mehtrick;

import java.util.Map;
import java.util.Map.Entry;

class StringReplacer {

	protected static String replaceWithValues(String string, Map<String, String> umlaute) {
		for (Entry<String, String> umlaut : umlaute.entrySet()) {
			string = string.replace(umlaut.getKey(), umlaut.getValue());
		}
		return string;
	}
}
