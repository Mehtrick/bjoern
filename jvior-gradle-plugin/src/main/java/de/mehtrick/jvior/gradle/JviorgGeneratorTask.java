package de.mehtrick.jvior.gradle;

import org.gradle.api.DefaultTask;
import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.TaskAction;

import de.mehtrick.jvior.generator.JviorGenerator;
import de.mehtrick.jvior.generator.JviorGeneratorConfig;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = false)
@ToString
public class JviorgGeneratorTask extends DefaultTask {

	@Input
	private String path;
	@Input
	private String folder;
	@Input
	private String pckg;
	@Input
	private String gendir;
	@Input
	private String extendedTestclass;

	@TaskAction
	public void yourTask() throws Exception {
		JviorGeneratorConfig.setPath(path);
		JviorGeneratorConfig.setFolder(folder);
		JviorGeneratorConfig.setGendir(gendir);
		JviorGeneratorConfig.setPckg(pckg);
		JviorGeneratorConfig.setExtendedTestclass(extendedTestclass);
		System.out.println("Generiere mit " + this);
		JviorGenerator.generateJviorClasses();

	}
}
