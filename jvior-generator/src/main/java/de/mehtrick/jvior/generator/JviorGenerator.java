package de.mehtrick.jvior.generator;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import de.mehtrick.jvior.parser.JviorParser;
import de.mehtrick.jvior.parser.modell.Jvior;

public class JviorGenerator {

	public static void gen(String[] args) {
		JviorGeneratorConfig.init(args);
		generateJviorClasses();
	}

	public static void generateJviorClasses() {
		if (JviorGeneratorConfig.isFoldersSet()) {
			File[] files = getFilesFromFolder(JviorGeneratorConfig.getFolder());
			Arrays.asList(files).forEach(f -> generateSingleJvior(f.getPath()));
		} else {
			generateSingleJvior(JviorGeneratorConfig.getPath());
		}
	}

	private static void generateSingleJvior(String path) {
		try {
			Jvior jvior = new JviorParser().parseSpec(path);
			JviorCodeGenerator.generate(jvior);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	private static File[] getFilesFromFolder(String folder) {
		File file = new File(folder);
		return file.listFiles();
	}
}
