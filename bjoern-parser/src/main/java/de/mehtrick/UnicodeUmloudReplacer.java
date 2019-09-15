package de.mehtrick;

import java.util.HashMap;
import java.util.Map;

class UnicodeUmloudReplacer {

	protected static String replace(String string) {
		Map<String, String> umlaute = new HashMap<>();
		umlaute.put("�", "\\u00e4");
		umlaute.put("�", "\\u00f6");
		umlaute.put("�", "\\u00fc");
		umlaute.put("�", "\\u00df");
		umlaute.put("�", "\\u00c4");
		umlaute.put("�", "\\u00d6");
		umlaute.put("�", "\\u00dc");
		return StringReplacer.replaceWithValues(string, umlaute);
	}

}
