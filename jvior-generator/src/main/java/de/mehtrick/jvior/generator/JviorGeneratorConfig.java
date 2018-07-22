package de.mehtrick.jvior.generator;

import org.apache.commons.lang3.StringUtils;

import lombok.Data;

@Data
 class JviorGeneratorConfig {

	private static final String PROPERTY_PACKAGE = "package";
	private static final String PROPERTY_FOLDER = "folder";
	private static final String PROPERTY_PATH = "path";
	private String path;
	private String folder;
	private String pckg;

	public JviorGeneratorConfig(String[] args) {
		checkForUnknownProperties(args);
		path = findPropertyInArgs(PROPERTY_PATH, args);
		folder = findPropertyInArgs(PROPERTY_FOLDER, args);
		pckg = findPropertyInArgs(PROPERTY_PACKAGE, args);
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
