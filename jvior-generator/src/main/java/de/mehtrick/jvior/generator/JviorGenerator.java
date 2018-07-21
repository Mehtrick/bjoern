package de.mehtrick.jvior.generator;

import java.io.File;
import java.util.Arrays;

import de.mehtrick.jvior.parser.JviorParser;
import de.mehtrick.jvior.parser.modell.Jvior;

public class JviorGenerator {

	public static void gen(String[] args) {
		JviorGeneratorConfig config = new JviorGeneratorConfig(args);
		if (config.isFoldersSet()) {
			File[] files = getFilesFromFolder(config.getFolder());
			Arrays.asList(files).forEach(f -> generateSingleJvior(config, f.getPath()));
		} else {
			generateSingleJvior(config, config.getPath());
		}
	}

	private static void generateSingleJvior(JviorGeneratorConfig config, String path) {
		Jvior jvior = new JviorParser().parseSpec(path);
		JviorCodeGenerator.generate(config, jvior);
	}

	private static File[] getFilesFromFolder(String folder) {
		File file = new File(folder);
		return file.listFiles();
	}
}
