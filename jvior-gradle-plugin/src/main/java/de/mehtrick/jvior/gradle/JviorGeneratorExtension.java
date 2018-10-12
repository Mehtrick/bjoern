package de.mehtrick.jvior.gradle;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class JviorGeneratorExtension {

	private String path;
	private String folder;
	private String pckg;
	private String gendir;
	private String extendedTestClass;
	private String docdir;
	private String template;
	private String templateFolder;
	private String docExtension;

}
