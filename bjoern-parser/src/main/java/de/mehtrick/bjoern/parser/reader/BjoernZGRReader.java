package de.mehtrick.bjoern.parser.reader;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.Charset;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import de.mehtrick.bjoern.parser.BjoernValidator;
import de.mehtrick.bjoern.parser.modell.BjoernZGRModell;
import de.mehtrick.umloud.UmloudReplacer;

/**
 * Reads the bjoern spec file and parses it into a simple modell which represents
 * the structure of the spec.
 * 
 * @author mehtrick
 *
 */
public class BjoernZGRReader {

	public static BjoernZGRModell readSpec(String path) {
		try {
			File zgr = getFileFromPath(path);
			String zgrAsString = FileUtils.readFileToString(zgr, Charset.defaultCharset());
			zgrAsString = UmloudReplacer.replaceUmlaute(zgrAsString);
			new BjoernValidator().validate(zgrAsString);
			ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
			return mapper.readValue(zgrAsString, BjoernZGRModell.class);
		} catch (IOException e) {
			throw new BjoernZGRReaderException(path, e);
		}
	}

	private static File getFileFromPath(String path) throws IOException {
		checkFileExtension(path);
		File yaml = new File(path);
		checkFileExists(path, yaml);
		return yaml;
	}

	private static void checkFileExists(String path, File yaml) throws FileNotFoundException {
		if (!yaml.exists()) {
			throw new FileNotFoundException("No file found under the path " + path);
		}
	}

	private static void checkFileExtension(String path) throws IOException {
		if (!StringUtils.endsWithAny(path, BjoernFileExtensions.getValuesAsString())) {
			throw new BjoernFileExtensionInvalidException();
		}
	}

}
