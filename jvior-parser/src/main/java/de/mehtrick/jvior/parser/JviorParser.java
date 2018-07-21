package de.mehtrick.jvior.parser;

import de.mehtrick.jvior.parser.modell.Jvior;
import de.mehtrick.jvior.parser.modell.JviorYMLModell;
import de.mehtrick.jvior.parser.reader.JviorYMLReader;

public class JviorParser {

	public Jvior parseSpec(String path) {
		JviorYMLModell yamlModell = new JviorYMLReader().readSpec(path);
		return new Jvior(yamlModell);
	}

}
