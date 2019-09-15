package de.mehtrick;

public class UmloudReplacer {

	public static String replaceWithUnicode(String string) {
		return UnicodeUmloudReplacer.replace(string);
	}

	public static String replaceWithHTMLEscapes(String html) {
		return HTMLUmloudReplacer.replace(html);
	}

	public static String replaceUmlaute(String string) {
		return DefaultUmloudReplacer.replace(string);
	}

}
