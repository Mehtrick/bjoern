package de.mehtrick.jvior.generator;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;

import de.mehtrick.jvior.parser.JviorParser;
import de.mehtrick.jvior.parser.modell.Jvior;

public class JviorGenerator {

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
			Jvior jvior = new JviorParser().parseSpec(path);
			JviorCodeGenerator.generate(jvior);
		} catch (Throwable e) {
			throw new JviorGeneratorException(path,e);
		}
	}

	private static File[] getFilesFromFolder(String folder) throws FileNotFoundException {
		File file = new File(folder);
		File[] ymlFiles = file.listFiles(new JviorFileNameFilter());
		if (ymlFiles.length == 0) {
			throw new FileNotFoundException("No yaml files Found in Folder " + folder
					+ ". The file has to end with .yml or .yaml to be detected");
		}
		return file.listFiles(new JviorFileNameFilter());
	}
}
