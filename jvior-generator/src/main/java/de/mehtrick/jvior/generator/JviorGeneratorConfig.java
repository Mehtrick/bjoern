package de.mehtrick.jvior.generator;

import org.apache.commons.lang3.StringUtils;

import lombok.Data;

@Data
public class JviorGeneratorConfig {

	private static final String PROPERTY_PACKAGE = "package";
	private static final String PROPERTY_FOLDER = "folder";
	private static final String PROPERTY_PATH = "path";
	private static final String PROPERTY_GENDIR = "gendir";
	private static final String PROPERTY_EXTENDED_TESTCLASS = "extendedTestclass";
	private String path;
	private String folder;
	private String pckg;
	private String gendir;
	private String extendedTestclass;

	public JviorGeneratorConfig(String[] args) {
		checkForUnknownProperties(args);
		path = findPropertyInArgs(PROPERTY_PATH, args);
		folder = findPropertyInArgs(PROPERTY_FOLDER, args);
		pckg = findPropertyInArgs(PROPERTY_PACKAGE, args);
		gendir = findPropertyInArgs(PROPERTY_GENDIR, args);
		extendedTestclass = findPropertyInArgs(PROPERTY_EXTENDED_TESTCLASS, args);
		
	}

	private void checkForUnknownProperties(String[] args) {

	}

	private String findPropertyInArgs(String propertyname, String[] args) {
		for (String arg : args) {
			if (arg.startsWith(propertyname + "=")) {
				return StringUtils.substringAfter(arg, "=");
			}
		}
		return null;
	}

	public boolean isFoldersSet() {
		return folder != null;
	}

}
