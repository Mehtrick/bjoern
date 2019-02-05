package de.mehtrick.bjoern.gradle;

import org.gradle.api.DefaultTask;
import org.gradle.api.tasks.TaskAction;

import de.mehtrick.bjoern.base.BjoernGeneratorConfig;
import de.mehtrick.bjoern.doc.BjoernDocApplication;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = false)
@ToString
public class BjoernDocGeneratorTask extends DefaultTask {

	@TaskAction
	public void genDoc() throws Exception {
		BjoernGeneratorExtension extension = getProject().getExtensions().getByType(BjoernGeneratorExtension.class);
		BjoernGeneratorConfig.setPath(extension.getPath());
		BjoernGeneratorConfig.setFolder(extension.getFolder());
		BjoernGeneratorConfig.setDocdir(extension.getDocdir());
		BjoernGeneratorConfig.setTemplate(extension.getTemplate());
		BjoernGeneratorConfig.setTemplateFolder(extension.getTemplateFolder());
		BjoernGeneratorConfig.setDocExtension(extension.getDocExtension());
		BjoernDocApplication.generateBjoernDocs();

	}
}
