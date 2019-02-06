package de.mehtrick.bjoern.gradle;

import org.gradle.api.DefaultTask;
import org.gradle.api.tasks.TaskAction;

import de.mehtrick.bjoern.base.BjoernGeneratorConfig;
import de.mehtrick.bjoern.generator.BjoernCodeGeneratorApplication;

public class BjoernGeneratorTask extends DefaultTask {

	@TaskAction
	public void genBjoernCode() throws Exception {
		BjoernGeneratorExtension extension = getProject().getExtensions().getByType(BjoernGeneratorExtension.class);
		BjoernGeneratorConfig bjoernGeneratorConfig = new BjoernGeneratorConfig();
		
		bjoernGeneratorConfig.setPath(extension.getPath());
		bjoernGeneratorConfig.setFolder(extension.getFolder());
		bjoernGeneratorConfig.setGendir(extension.getGendir());
		bjoernGeneratorConfig.setPckg(extension.getPckg());
		bjoernGeneratorConfig.setExtendedTestclass(extension.getExtendedTestClass());
		new BjoernCodeGeneratorApplication(bjoernGeneratorConfig).generateBjoernClasses();

	}
}
