package de.mehtrick.jvior.generator;

import org.apache.commons.lang3.StringUtils;

public class JviorGeneratorConfig {

	private static final String PROPERTY_PACKAGE = "package";
	private static final String PROPERTY_FOLDER = "folder";
	private static final String PROPERTY_PATH = "path";
	private static final String PROPERTY_GENDIR = "gendir";
	private static final String PROPERTY_EXTENDED_TESTCLASS = "extendedTestclass";
	private static String path;
	private static String folder;

	private static String pckg;
	private static String gendir;
	private static String extendedTestclass;

	public static void init(String[] args) {
		checkForUnknownProperties(args);
		setPath(findPropertyInArgs(PROPERTY_PATH, args));
		folder = findPropertyInArgs(PROPERTY_FOLDER, args);
		setPckg(findPropertyInArgs(PROPERTY_PACKAGE, args));
		setGendir(findPropertyInArgs(PROPERTY_GENDIR, args));
		setExtendedTestclass(findPropertyInArgs(PROPERTY_EXTENDED_TESTCLASS, args));

	}

	private static void checkForUnknownProperties(String[] args) {

	}

	private static String findPropertyInArgs(String propertyname, String[] args) {
		for (String arg : args) {
			if (arg.startsWith(propertyname + "=")) {
				return StringUtils.substringAfter(arg, "=");
			}
		}
		return null;
	}

	public static boolean isFoldersSet() {
		return folder != null;
	}

	public static String getPath() {
		return path;
	}

	public static void setPath(String path) {
		JviorGeneratorConfig.path = path;
	}

	public static String getPckg() {
		return pckg;
	}

	public static void setPckg(String pckg) {
		JviorGeneratorConfig.pckg = pckg;
	}

	public static String getGendir() {
		return gendir;
	}

	public static void setGendir(String gendir) {
		JviorGeneratorConfig.gendir = gendir;
	}

	public static String getExtendedTestclass() {
		return extendedTestclass;
	}

	public static void setExtendedTestclass(String extendedTestclass) {
		JviorGeneratorConfig.extendedTestclass = extendedTestclass;
	}

	public static String getFolder() {
		return folder;
	}

	public static void setFolder(String folder) {
		JviorGeneratorConfig.folder = folder;
	}

}
