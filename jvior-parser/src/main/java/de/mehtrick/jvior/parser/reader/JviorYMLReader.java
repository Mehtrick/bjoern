package de.mehtrick.jvior.parser.reader;

import java.io.File;
import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import de.mehtrick.jvior.parser.modell.JviorYMLModell;

public class JviorYMLReader {

	public JviorYMLModell readSpec(String path) {
		try {
			File yaml = new File(path);
			ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
			return mapper.readValue(yaml, JviorYMLModell.class);
		} catch (IOException e) {
			throw new JviorYMLReaderException(e);
		}
	}

}
