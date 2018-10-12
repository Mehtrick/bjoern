package de.mehtrick.jvior.doc;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;

import org.apache.commons.lang3.StringUtils;

import de.mehtrick.jvior.base.AbstractJviorGenerator;
import de.mehtrick.jvior.base.JviorGeneratorConfig;
import de.mehtrick.jvior.base.JviorGeneratorException;
import de.mehtrick.jvior.base.JviorMissingPropertyException;
import de.mehtrick.jvior.parser.JviorParser;
import de.mehtrick.jvior.parser.modell.Jvior;

public class JviorDocApplication extends AbstractJviorGenerator {

	public static void main(String[] args) throws JviorMissingPropertyException, FileNotFoundException {
		JviorGeneratorConfig.init(args);
		generateJviorDocs();
	}

	public static void generateJviorDocs() throws JviorMissingPropertyException, FileNotFoundException {
		JviorGeneratorConfig.validate();
		if (StringUtils.isAllBlank(JviorGeneratorConfig.getDocdir())) {
			throw new JviorMissingPropertyException("Please configure the docDir where the documentation will be generated");
		}
		if (JviorGeneratorConfig.isFoldersSet()) {
			File[] files = getFilesFromFolder(JviorGeneratorConfig.getFolder());
			Arrays.asList(files).forEach(f -> generateSingleJviorDocs(f.getPath()));
		} else {
			generateSingleJviorDocs(JviorGeneratorConfig.getPath());
		}
	}

	private static void generateSingleJviorDocs(String path) {
		try {
			Jvior jvior = JviorParser.parseSpec(path);
			JviorDocGenerator.generate(jvior);
		} catch (Throwable e) {
			throw new JviorGeneratorException(path, e);
		}
	}

}
