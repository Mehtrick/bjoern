package de.mehtrick.bjoern.gradle;

import de.mehtrick.bjoern.doc.BjoernDocApplication;
import de.mehtrick.bjoern.doc.BjoernDocGeneratorConfig;
import org.gradle.api.DefaultTask;
import org.gradle.api.provider.Property;
import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.Optional;
import org.gradle.api.tasks.TaskAction;

public abstract class BjoernDocGeneratorTask extends DefaultTask {

    @Input
    @Optional
    public abstract Property<String> getSpecPath();

    @Input
    @Optional
    public abstract Property<String> getFolder();

    @Input
    @Optional
    public abstract Property<String> getDocdir();

    @Input
    @Optional
    public abstract Property<String> getTemplate();

    @Input
    @Optional
    public abstract Property<String> getTemplateFolder();

    @Input
    @Optional
    public abstract Property<String> getDocExtension();

    @Input
    public abstract Property<Boolean> getSpecRecursive();

    @Input
    public abstract Property<Boolean> getGitHistory();

    @TaskAction
    public void genDoc() throws Exception {
        BjoernDocGeneratorConfig bjoernGeneratorConfig = new BjoernDocGeneratorConfig();
        bjoernGeneratorConfig.setPath(getSpecPath().getOrNull());
        bjoernGeneratorConfig.setFolder(getFolder().getOrNull());
        bjoernGeneratorConfig.setDocdir(getDocdir().getOrNull());
        bjoernGeneratorConfig.setTemplate(getTemplate().getOrNull());
        bjoernGeneratorConfig.setTemplateFolder(getTemplateFolder().getOrNull());
        bjoernGeneratorConfig.setDocExtension(getDocExtension().getOrNull());
        bjoernGeneratorConfig.setSpecRecursive(getSpecRecursive().getOrElse(false));
        bjoernGeneratorConfig.setGitHistory(getGitHistory().getOrElse(false));
        new BjoernDocApplication(bjoernGeneratorConfig).generateBjoernDocs();
    }
}
