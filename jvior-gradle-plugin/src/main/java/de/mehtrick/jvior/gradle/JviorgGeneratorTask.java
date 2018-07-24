package de.mehtrick.jvior.gradle;

import org.gradle.api.DefaultTask;
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

	@TaskAction
	public void yourTask() throws Exception {
		JviorGeneratorExtension extension = getProject().getExtensions().getByType(JviorGeneratorExtension.class);
		JviorGeneratorConfig.setPath(extension.getPath());
		JviorGeneratorConfig.setFolder(extension.getFolder());
		JviorGeneratorConfig.setGendir(extension.getGendir());
		JviorGeneratorConfig.setPckg(extension.getPckg());
		JviorGeneratorConfig.setExtendedTestclass(extension.getExtendedTestClass());
		System.out.println("Generiere mit " + this);
		JviorGenerator.generateJviorClasses();

	}
}
