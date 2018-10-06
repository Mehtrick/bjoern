package de.mehtrick.jvior.generator;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;

import de.mehtrick.jvior.base.AbstractJviorGenerator;
import de.mehtrick.jvior.base.JviorGeneratorConfig;
import de.mehtrick.jvior.base.JviorGeneratorException;
import de.mehtrick.jvior.base.JviorMissingPropertyException;
import de.mehtrick.jvior.parser.JviorParser;
import de.mehtrick.jvior.parser.modell.Jvior;

public class JviorGenerator extends AbstractJviorGenerator {

	public static void gen(String[] args) throws JviorMissingPropertyException, FileNotFoundException {
		JviorGeneratorConfig.init(args);
		generateJviorClasses();
	}

	public static void generateJviorClasses() throws JviorMissingPropertyException, FileNotFoundException {
		JviorGeneratorConfig.validate();
		if (JviorGeneratorConfig.isFoldersSet()) {
			File[] files = getFilesFromFolder(JviorGeneratorConfig.getFolder());
			Arrays.asList(files).forEach(f -> generateSingleJvior(f.getPath()));
		} else {
			generateSingleJvior(JviorGeneratorConfig.getPath());
		}
	}

	private static void generateSingleJvior(String path) {
		try {
			Jvior jvior = JviorParser.parseSpec(path);
			JviorCodeGenerator.generate(jvior);
		} catch (Throwable e) {
			throw new JviorGeneratorException(path, e);
		}
	}

}
