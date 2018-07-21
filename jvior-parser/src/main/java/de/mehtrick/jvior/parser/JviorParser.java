package de.mehtrick.jvior.parser;

import de.mehtrick.jvior.parser.modell.Jvior;
import de.mehtrick.jvior.parser.modell.JviorYMLModell;
import de.mehtrick.jvior.parser.reader.JviorYMLReader;

/**
 * Main Class to read from a certain jvior spec file and parses it into a jvior
 * java pojo
 * 
 * @author mehtrick
 * 
 *
 */
public class JviorParser {

	/**
	 * 
	 * @param path - expects the path to the jvior spec file
	 * @return parsed jvior modell
	 */
	public Jvior parseSpec(String path) {
		JviorYMLModell yamlModell = new JviorYMLReader().readSpec(path);
		return new Jvior(yamlModell);
	}

}
