package de.mehtrick.bjoern.gradle;

import org.gradle.api.DefaultTask;
import org.gradle.api.tasks.TaskAction;

import de.mehtrick.bjoern.base.BjoernGeneratorConfig;
import de.mehtrick.bjoern.doc.BjoernDocApplication;

public class BjoernDocGeneratorTask extends DefaultTask {

	@TaskAction
	public void genDoc() throws Exception {
		BjoernGeneratorExtension extension = getProject().getExtensions().getByType(BjoernGeneratorExtension.class);
		BjoernGeneratorConfig bjoernGeneratorConfig = new BjoernGeneratorConfig();
		bjoernGeneratorConfig.setPath(extension.getPath());
		bjoernGeneratorConfig.setFolder(extension.getFolder());
		bjoernGeneratorConfig.setDocdir(extension.getDocdir());
		bjoernGeneratorConfig.setTemplate(extension.getTemplate());
		bjoernGeneratorConfig.setTemplateFolder(extension.getTemplateFolder());
		bjoernGeneratorConfig.setDocExtension(extension.getDocExtension());
		new BjoernDocApplication(bjoernGeneratorConfig).generateBjoernDocs();

	}
}
