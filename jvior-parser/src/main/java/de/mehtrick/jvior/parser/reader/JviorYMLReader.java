package de.mehtrick.jvior.parser.reader;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.Charset;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import de.mehtrick.jvior.parser.modell.JviorYMLModell;
import de.mehtrick.umloud.UmloudReplacer;

/**
 * Reads the jvior spec file and parses it into a simple modell which represents
 * the structure of the spec.
 * 
 * @author mehtrick
 *
 */
public class JviorYMLReader {

	public static JviorYMLModell readSpec(String path) {
		try {
			File yaml = getFileFromPath(path);
			String yamlAsString = FileUtils.readFileToString(yaml, Charset.defaultCharset());
			yamlAsString = UmloudReplacer.replaceUmlaute(yamlAsString);
			ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
			return mapper.readValue(yamlAsString, JviorYMLModell.class);
		} catch (IOException e) {
			throw new JviorYMLReaderException(path, e);
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
		if (!StringUtils.endsWithAny(path, JviorFileExtensions.getValuesAsString())) {
			throw new JviorFileExtensionInvalidException();
		}
	}

}
