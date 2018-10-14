package de.mehtrick.jvior.base;

import org.apache.commons.lang3.StringUtils;

public class JviorGeneratorConfig {

	private static final String PROPERTY_PACKAGE = "package";
	private static final String PROPERTY_FOLDER = "folder";
	private static final String PROPERTY_PATH = "path";
	private static final String PROPERTY_GENDIR = "gendir";
	private static final String PROPERTY_EXTENDED_TESTCLASS = "extendedTestclass";
	private static final String PROPERTY_DOCDIR = "docdir";
	private static final String PROPERTY_TEMPLATE = "template";
	private static final String PROPERTY_TEMPLATE_FOLDER = "templateFolder";
	private static final String PROPERTY_DOC_EXTENSION = "docExtension";
	private static String path;
	private static String folder;

	private static String pckg;
	private static String gendir;
	private static String extendedTestclass;
	private static String docdir;
	private static String template = "/asciidoc.ftlh";
	private static String templateFolder;
	private static String docExtension = "adoc";

	public static void init(String[] args) {
		setPath(findPropertyInArgs(PROPERTY_PATH, args));
		setFolder(findPropertyInArgs(PROPERTY_FOLDER, args));
		setPckg(findPropertyInArgs(PROPERTY_PACKAGE, args));
		setGendir(findPropertyInArgs(PROPERTY_GENDIR, args));
		setExtendedTestclass(findPropertyInArgs(PROPERTY_EXTENDED_TESTCLASS, args));
		setDocdir(findPropertyInArgs(PROPERTY_DOCDIR, args));
		setTemplate(findPropertyInArgs(PROPERTY_TEMPLATE, args));
		setTemplateFolder(findPropertyInArgs(PROPERTY_TEMPLATE_FOLDER, args));
		setDocExtension(findPropertyInArgs(PROPERTY_DOC_EXTENSION, args));
	}

	public static void validate() throws JviorMissingPropertyException {
		if (StringUtils.isAllBlank(path, folder)) {
			throw new JviorMissingPropertyException("Please configure a path or folder");
		}

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
		if (!folder.isEmpty()) {
			JviorGeneratorConfig.folder = folder;
		}
	}

	public static String getDocdir() {
		return docdir;
	}

	public static void setDocdir(String docdir) {
		JviorGeneratorConfig.docdir = docdir;
	}

	public static String getTemplate() {
		return template;
	}

	public static void setTemplate(String template) {
		if (StringUtils.isNotBlank(template)) {
			JviorGeneratorConfig.template = template;
		}
	}

	public static String getDocExtension() {
		return docExtension;
	}

	public static void setDocExtension(String docExtension) {
		if (StringUtils.isNotBlank(docExtension)) {
			JviorGeneratorConfig.docExtension = docExtension;
		}
	}

	public static String getTemplateFolder() {
		return templateFolder;
	}

	public static void setTemplateFolder(String templateFolder) {
			JviorGeneratorConfig.templateFolder = templateFolder;
	}

}
