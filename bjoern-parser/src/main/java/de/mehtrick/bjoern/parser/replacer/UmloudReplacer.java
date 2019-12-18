package de.mehtrick.bjoern.parser.replacer;

public class UmloudReplacer {

	public static String replaceWithHTMLEscapes(String html) {
		return HTMLUmloudReplacer.replace(html);
	}

	public static String replaceUmlaute(String string) {
		return DefaultUmloudReplacer.replace(string);
	}

}
