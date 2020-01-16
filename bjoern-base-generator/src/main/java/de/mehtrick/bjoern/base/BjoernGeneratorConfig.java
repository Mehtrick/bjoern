package de.mehtrick.bjoern.base;

import org.apache.commons.lang3.StringUtils;

import java.nio.charset.Charset;

import static java.nio.charset.StandardCharsets.UTF_8;

/**
 * Config class for all bjoern generators.
 */
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
	private final String PROPERTY_ENCODING = "encoding";
	private String path;
	private String folder;

	private String pckg;
	private String gendir;
	private String extendedTestclass;
	private String docdir;
	private String template = "/asciidoc.ftlh";
	private String templateFolder;
	private String docExtension = "adoc";

	private Charset encoding = UTF_8;

	public BjoernGeneratorConfig() {
	}

	/**
	 * This methods accepts the cli arguments and parses the values to the correct property
	 *
	 * @param args
	 * @throws BjoernMissingPropertyException
	 */
	public BjoernGeneratorConfig(String[] args) {
		setPath(findPropertyInArgs(PROPERTY_PATH, args));
		setFolder(findPropertyInArgs(PROPERTY_FOLDER, args));
		setPckg(findPropertyInArgs(PROPERTY_PACKAGE, args));
		setGendir(findPropertyInArgs(PROPERTY_GENDIR, args));
		setExtendedTestclass(findPropertyInArgs(PROPERTY_EXTENDED_TESTCLASS, args));
		setDocdir(findPropertyInArgs(PROPERTY_DOCDIR, args));
		setTemplate(findPropertyInArgs(PROPERTY_TEMPLATE, args));
		setTemplateFolder(findPropertyInArgs(PROPERTY_TEMPLATE_FOLDER, args));
		setDocExtension(findPropertyInArgs(PROPERTY_DOC_EXTENSION, args));
		setEncoding(findPropertyInArgs(PROPERTY_ENCODING, args));
	}

	public void validate() throws BjoernMissingPropertyException {
		if (StringUtils.isAllBlank(path, folder)) {
			throw new BjoernMissingPropertyException("Please configure a path or folder");
		}

	}

	protected String findPropertyInArgs(String propertyname, String[] args) {
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
        if (folder != null && !folder.isEmpty()) {
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

	public Charset getEncoding() {
		return encoding;
	}

	public void setEncoding(String encoding) {
		if (StringUtils.isNotBlank(encoding)) {
			this.encoding = Charset.forName(encoding);
		}
	}


}
