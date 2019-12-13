package de.mehtrick.bjoern.base;

import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

public class BjoernGeneratorConfig {

	private final String PROPERTY_PACKAGE = "package";
	private final String PROPERTY_FOLDER = "folder";
	private final String PROPERTY_PATH = "path";
	private final String PROPERTY_GENDIR = "gendir";
	private final String PROPERTY_EXTENDED_TESTCLASS = "extendedTestclass";
	private final String PROPERTY_DOCDIR = "docdir";
	private final String PROPERTY_TEMPLATE = "template";
	private final String PROPERTY_TEMPLATE_FOLDER = "templateFolder";
	private final String PROPERTY_DOC_EXTENSION = "docExtension";
	private final String PROPERTY_JUNIT_VERSION = "junitVersion";
	private String path;
	private String folder;

	private String pckg;
	private String gendir;
	private String extendedTestclass;
	private String docdir;
	private String template = "/asciidoc.ftlh";
	private String templateFolder;
	private String docExtension = "adoc";
	private SupportedJunitVersion junitVersion = SupportedJunitVersion.junit4;

	public BjoernGeneratorConfig() {

	}

	public BjoernGeneratorConfig(String[] args) throws NotSupportedJunitVersionException {
		setPath(findPropertyInArgs(PROPERTY_PATH, args));
		setFolder(findPropertyInArgs(PROPERTY_FOLDER, args));
		setPckg(findPropertyInArgs(PROPERTY_PACKAGE, args));
		setGendir(findPropertyInArgs(PROPERTY_GENDIR, args));
		setExtendedTestclass(findPropertyInArgs(PROPERTY_EXTENDED_TESTCLASS, args));
		setDocdir(findPropertyInArgs(PROPERTY_DOCDIR, args));
		setTemplate(findPropertyInArgs(PROPERTY_TEMPLATE, args));
		setTemplateFolder(findPropertyInArgs(PROPERTY_TEMPLATE_FOLDER, args));
		setDocExtension(findPropertyInArgs(PROPERTY_DOC_EXTENSION, args));
		setJunitVersion(findPropertyInArgs(PROPERTY_JUNIT_VERSION, args));
	}

	public void validate() throws BjoernMissingPropertyException {
		if (StringUtils.isAllBlank(path, folder)) {
			throw new BjoernMissingPropertyException("Please configure a path or folder");
		}

	}

	private String findPropertyInArgs(String propertyname, String[] args) {
		for (String arg : args) {
			if (arg.startsWith(propertyname + "=")) {
				return StringUtils.substringAfter(arg, "=");
			}
		}
		return null;
	}

	public SupportedJunitVersion getJunitVersion() {
		return junitVersion;
	}

	public void setJunitVersion(String junitVersion) throws NotSupportedJunitVersionException {
		this.junitVersion = SupportedJunitVersion.getByVersionnumber(junitVersion);
	}

	public boolean isFoldersSet() {
		return folder != null;
	}

	public String getPath() {
		return path;
	}

	public String getPckg() {
		return pckg;
	}

	public void setPckg(String pckg) {
		this.pckg = pckg;
	}

	public  String getGendir() {
		return gendir;
	}

	public  void setGendir(String gendir) {
		this.gendir = gendir;
	}

	public  String getExtendedTestclass() {
		return extendedTestclass;
	}

	public  void setExtendedTestclass(String extendedTestclass) {
		this.extendedTestclass = extendedTestclass;
	}

	public  String getFolder() {
		return folder;
	}

	public  void setFolder(String folder) {
		if (!folder.isEmpty()) {
			this.folder = folder;
		}
	}

	public  String getDocdir() {
		return docdir;
	}

	public  void setDocdir(String docdir) {
		this.docdir = docdir;
	}

	public  String getTemplate() {
		return template;
	}

	public  void setTemplate(String template) {
		if (StringUtils.isNotBlank(template)) {
			this.template = template;
		}
	}

	public String getDocExtension() {
		return docExtension;
	}

	public void setDocExtension(String docExtension) {
		if (StringUtils.isNotBlank(docExtension)) {
			this.docExtension = docExtension;
		}
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getTemplateFolder() {
		return templateFolder;
	}

	public void setTemplateFolder(String templateFolder) {
		this.templateFolder = templateFolder;
	}

	/**
	 * Currently Junit 4 and 5 in the latest Versions are supported
	 */
	public enum SupportedJunitVersion {
		junit4(4, org.junit.Test.class, Before.class), junit5(5, org.junit.jupiter.api.Test.class, BeforeEach.class);

		private final int junitVersion;
		private final Class<?> testAnnotationClass;


		private final Class<?> beforeAnnotationClass;

		SupportedJunitVersion(int versionnumber, Class<?> testAnnotationClass, Class<?> beforeAnnotationClass) {
			this.junitVersion = versionnumber;
			this.testAnnotationClass = testAnnotationClass;
			this.beforeAnnotationClass = beforeAnnotationClass;
		}

		public static SupportedJunitVersion getByVersionnumber(String versionNumber) throws NotSupportedJunitVersionException {
			return Arrays.stream(SupportedJunitVersion.values()).filter(js -> Integer.parseInt(versionNumber) == js.junitVersion).findFirst().orElseThrow(NotSupportedJunitVersionException::new);
		}

		public static Set<String> getSupportedVersionNumbers() {
			return Arrays.stream(SupportedJunitVersion.values()).map(js -> String.valueOf(js.junitVersion)).collect(Collectors.toSet());
		}

		public Class<?> getTestAnnotationClass() {
			return testAnnotationClass;
		}

		public Class<?> getBeforeAnnotationClass() {
			return beforeAnnotationClass;
		}
	}

}
