package de.mehtrick.jvior.parser.reader;

import java.io.IOException;
import java.io.StringWriter;
import java.nio.charset.Charset;

import org.apache.commons.io.IOUtils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import de.mehtrick.jvior.parser.modell.Jvior;
import de.mehtrick.jvior.parser.modell.yaml.JviorYMLModell;

public class JviorYMLReader {

	public JviorYMLModell readSpec(String path) {
		try {
			ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
			StringWriter writer = new StringWriter();
			IOUtils.copy(this.getClass().getClassLoader().getResourceAsStream("jvior.yaml"), writer,
					Charset.forName("UTF-8"));
			String theString = writer.toString();
			return mapper.readValue(theString, JviorYMLModell.class);
		} catch (IOException e) {
			throw new JviorYMLReaderException(e);
		}
	}

	public Jvior parseSpec(String path) {
		JviorYMLModell jviorYmlModell = readSpec(path);
		return parseToJviorComplexModell(jviorYmlModell);

	}

	private Jvior parseToJviorComplexModell(JviorYMLModell jviorYmlModell) {

		
		return null;
	}
}
