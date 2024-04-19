package de.mehtrick.bjoern.gradle;

import de.mehtrick.bjoern.doc.BjoernDocApplication;
import de.mehtrick.bjoern.doc.BjoernDocGeneratorConfig;
import org.gradle.api.DefaultTask;
import org.gradle.api.tasks.TaskAction;

public class BjoernDocGeneratorTask extends DefaultTask {

	@TaskAction
	public void genDoc() throws Exception {
            BjoernGeneratorExtension extension = getProject().getExtensions().getByType(BjoernGeneratorExtension.class);
            BjoernDocGeneratorConfig bjoernGeneratorConfig = new BjoernDocGeneratorConfig();
            bjoernGeneratorConfig.setPath(extension.getPath());
            bjoernGeneratorConfig.setFolder(extension.getFolder());
            bjoernGeneratorConfig.setDocdir(extension.getDocdir());
            bjoernGeneratorConfig.setTemplate(extension.getTemplate());
            bjoernGeneratorConfig.setTemplateFolder(extension.getTemplateFolder());
            bjoernGeneratorConfig.setDocExtension(extension.getDocExtension());
            bjoernGeneratorConfig.setSpecRecursive(extension.isSpecRecursive());
            new BjoernDocApplication(bjoernGeneratorConfig).generateBjoernDocs();

    }
}
