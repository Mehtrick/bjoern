package de.mehtrick.jvior.gradle;

import org.gradle.api.DefaultTask;
import org.gradle.api.tasks.TaskAction;

import de.mehtrick.jvior.base.JviorGeneratorConfig;
import de.mehtrick.jvior.doc.JviorDocApplication;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = false)
@ToString
public class JviorDocGeneratorTask extends DefaultTask {

	@TaskAction
	public void genDoc() throws Exception {
		JviorGeneratorExtension extension = getProject().getExtensions().getByType(JviorGeneratorExtension.class);
		JviorGeneratorConfig.setPath(extension.getPath());
		JviorGeneratorConfig.setFolder(extension.getFolder());
		JviorGeneratorConfig.setDocdir(extension.getDocdir());
		JviorGeneratorConfig.setTemplate(extension.getTemplate());
		JviorGeneratorConfig.setTemplateFolder(extension.getTemplateFolder());
		JviorGeneratorConfig.setDocExtension(extension.getDocExtension());
		JviorDocApplication.generateJviorDocs();

	}
}
