package de.mehtrick.jvior.asciidoc;

import de.mehtrick.jvior.parser.modell.Jvior;

public class JviorAsciiDocGenerator {

	public static void generate(Jvior jvior) {
		System.out.println("Generate Documentation for: " + jvior.getFeatureNameFormatted());

	}

}
