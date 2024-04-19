package de.mehtrick.bjoern.gradle;

public class BjoernGeneratorExtension {

    private String path;
    private String folder;
    private String pckg;
    private String gendir;
    private String extendedTestClass;
    private String docdir;
    private String template;
    private String templateFolder;
    private String docExtension;
    private String junitVersion;
    private String encoding;
    private Boolean specRecursive = false;

    public BjoernGeneratorExtension() {
    }

    public String getPath() {
        return this.path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getFolder() {
        return this.folder;
    }

    public void setFolder(String folder) {
        this.folder = folder;
    }

    public String getPckg() {
        return this.pckg;
    }

    public void setPckg(String pckg) {
        this.pckg = pckg;
    }

    public String getGendir() {
        return this.gendir;
    }

    public void setGendir(String gendir) {
        this.gendir = gendir;
    }

    public String getExtendedTestClass() {
        return this.extendedTestClass;
    }

    public void setExtendedTestClass(String extendedTestClass) {
        this.extendedTestClass = extendedTestClass;
    }

    public String getDocdir() {
        return this.docdir;
    }

    public void setDocdir(String docdir) {
        this.docdir = docdir;
    }

    public String getTemplate() {
        return this.template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }

    public String getTemplateFolder() {
        return this.templateFolder;
    }

    public void setTemplateFolder(String templateFolder) {
        this.templateFolder = templateFolder;
    }

    public String getDocExtension() {
        return this.docExtension;
    }

    public void setDocExtension(String docExtension) {
        this.docExtension = docExtension;
    }

    public String getJunitVersion() {
        return junitVersion;
    }

    public void setJunitVersion(String junitVersion) {
        this.junitVersion = junitVersion;
    }

    @Override
    public String toString() {
        return "BjoernGeneratorExtension{" +
                "path='" + path + '\'' +
                ", folder='" + folder + '\'' +
                ", pckg='" + pckg + '\'' +
                ", gendir='" + gendir + '\'' +
                ", extendedTestClass='" + extendedTestClass + '\'' +
                ", docdir='" + docdir + '\'' +
                ", template='" + template + '\'' +
                ", templateFolder='" + templateFolder + '\'' +
                ", docExtension='" + docExtension + '\'' +
                ", junitVersion='" + junitVersion + '\'' +
                ", encoding='" + encoding + '\'' +
                ", specRecursive=" + specRecursive +
                '}';
    }

    public String getEncoding() {
        return encoding;
    }

    public void setEncoding(String encoding) {
        this.encoding = encoding;
    }

    public void setSpecRecursive(Boolean specRecursive) {
        this.specRecursive = specRecursive;
    }

    public Boolean isSpecRecursive() {
        return specRecursive;
    }

}

