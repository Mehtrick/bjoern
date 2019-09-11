package de.mehtrick;

import java.util.HashMap;
import java.util.Map;

class DefaultUmloudReplacer {

	protected static String replace(String string) {
		Map<String, String> umlaute = new HashMap<>();
		umlaute.put("�", "ae");
		umlaute.put("�", "oe");
		umlaute.put("�", "ue");
		umlaute.put("�", "ss");
		umlaute.put("�", "Ae");
		umlaute.put("�", "Oe");
		umlaute.put("�", "Ue");
		return StringReplacer.replaceWithValues(string, umlaute);
	}

}
