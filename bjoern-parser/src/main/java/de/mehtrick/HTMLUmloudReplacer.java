package de.mehtrick;

import java.util.HashMap;
import java.util.Map;

class HTMLUmloudReplacer {

	protected static String replace(String string) {
		Map<String, String> umlaute = new HashMap<>();
		umlaute.put("�", "&Auml;");
		umlaute.put("�", "&Ouml;");
		umlaute.put("�", "&Uuml;");
		umlaute.put("�", "&auml;");
		umlaute.put("�", "&ouml;");
		umlaute.put("�", "&uuml;");
		umlaute.put("�", "&szlig;");
		return StringReplacer.replaceWithValues(string, umlaute);
	}

}
