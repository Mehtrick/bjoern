package de.mehtrick;

import java.util.HashMap;
import java.util.Map;

class DefaultUmloudReplacer {

	protected static String replace(String string) {
		Map<String, String> umlaute = new HashMap<>();
		umlaute.put("ä", "ae");
		umlaute.put("ö", "oe");
		umlaute.put("ü", "ue");
		umlaute.put("ß", "ss");
		umlaute.put("Ä", "Ae");
		umlaute.put("Ö", "Oe");
		umlaute.put("Ü", "Ue");
		return StringReplacer.replaceWithValues(string, umlaute);
	}

}
