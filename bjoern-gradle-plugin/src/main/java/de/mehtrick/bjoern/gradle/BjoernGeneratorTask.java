package de.mehtrick.bjoern.gradle;

import org.gradle.api.DefaultTask;
import org.gradle.api.tasks.TaskAction;

import de.mehtrick.bjoern.base.BjoernGeneratorConfig;
import de.mehtrick.bjoern.generator.BjoernCodeGeneratorApplication;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = false)
@ToString
public class BjoernGeneratorTask extends DefaultTask {

	@TaskAction
	public void genBjoernCode() throws Exception {
		BjoernGeneratorExtension extension = getProject().getExtensions().getByType(BjoernGeneratorExtension.class);
		BjoernGeneratorConfig.setPath(extension.getPath());
		BjoernGeneratorConfig.setFolder(extension.getFolder());
		BjoernGeneratorConfig.setGendir(extension.getGendir());
		BjoernGeneratorConfig.setPckg(extension.getPckg());
		BjoernGeneratorConfig.setExtendedTestclass(extension.getExtendedTestClass());
		BjoernCodeGeneratorApplication.generateBjoernClasses();

	}
}
