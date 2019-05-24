package de.mehtrick.bjoern.parser;

import de.mehtrick.bjoern.parser.modell.Bjoern;
import de.mehtrick.bjoern.parser.modell.BjoernZGRModell;
import de.mehtrick.bjoern.parser.reader.BjoernZGRReader;

/**
 * Main Class to read from a certain bjoern spec file and parses it into a bjoern modell
 * java pojo
 *
 * @author mehtrick
 *
 *
 */
public class BjoernParser {

	/**
	 *
	 * @param path - expects the path to the bjoern spec file
	 * @return parsed bjoern modell
	 */
	public static Bjoern parseSpec(String path) {
		BjoernZGRModell zgrModell = BjoernZGRReader.readSpec(path);
		return new Bjoern(zgrModell,path);
	}

}
