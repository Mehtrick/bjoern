package de.mehtrick.bjoern.parser;

import de.mehtrick.bjoern.parser.modell.Bjoern;
import de.mehtrick.bjoern.parser.modell.BjoernZGRModell;
import de.mehtrick.bjoern.parser.reader.BjoernZGRReader;

import java.nio.charset.Charset;

/**
 * Main Class to read from a certain bjoern spec file and parses it into a bjoern modell
 * java pojo
 *
 * @author mehtrick
 */
public class BjoernParser {

	/**
	 *
	 * @param path - expects the path to the bjoern spec file
	 * @param encodig - The charset which is used to read the spec file
	 * @return parsed bjoern modell
	 */
	public Bjoern parseSpec(String path, Charset encodig) {
		BjoernZGRModell zgrModell = BjoernZGRReader.readSpec(path, encodig);
		return new Bjoern(zgrModell, path);
	}

}
