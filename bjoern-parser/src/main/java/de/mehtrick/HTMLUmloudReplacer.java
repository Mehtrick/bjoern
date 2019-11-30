package de.mehtrick;

import java.util.HashMap;
import java.util.Map;

class HTMLUmloudReplacer {

	protected static String replace(String string) {
		Map<String, String> umlaute = new HashMap<>();
		umlaute.put("Ä", "&Auml;");
		umlaute.put("Ö", "&Ouml;");
		umlaute.put("U", "&Uuml;");
		umlaute.put("ä", "&auml;");
		umlaute.put("ö", "&ouml;");
		umlaute.put("u", "&uuml;");
		umlaute.put("ß", "&szlig;");
		return StringReplacer.replaceWithValues(string, umlaute);
	}

}
