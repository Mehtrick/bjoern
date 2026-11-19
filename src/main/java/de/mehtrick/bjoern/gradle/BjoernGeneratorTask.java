package de.mehtrick.bjoern.gradle;

import de.mehtrick.bjoern.generator.BjoernCodeGeneratorApplication;
import de.mehtrick.bjoern.generator.BjoernCodeGeneratorConfig;
import org.gradle.api.DefaultTask;
import org.gradle.api.provider.Property;
import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.Optional;
import org.gradle.api.tasks.TaskAction;

public abstract class BjoernGeneratorTask extends DefaultTask {

    @Input
    @Optional
    public abstract Property<String> getSpecPath();

    @Input
    @Optional
    public abstract Property<String> getFolder();

    @Input
    public abstract Property<String> getGendir();

    @Input
    public abstract Property<String> getPckg();

    @Input
    @Optional
    public abstract Property<String> getExtendedTestClass();

    @Input
    @Optional
    public abstract Property<String> getJunitVersion();

    @Input
    @Optional
    public abstract Property<String> getEncoding();

    @Input
    public abstract Property<Boolean> getSpecRecursive();

    @TaskAction
    public void genBjoernCode() throws Exception {
        BjoernCodeGeneratorConfig bjoernGeneratorConfig = new BjoernCodeGeneratorConfig();
        bjoernGeneratorConfig.setPath(getSpecPath().getOrNull());
        bjoernGeneratorConfig.setFolder(getFolder().getOrNull());
        bjoernGeneratorConfig.setGendir(getGendir().getOrNull());
        bjoernGeneratorConfig.setPckg(getPckg().getOrNull());
        bjoernGeneratorConfig.setExtendedTestclass(getExtendedTestClass().getOrNull());
        bjoernGeneratorConfig.setJunitVersion(getJunitVersion().getOrNull());
        bjoernGeneratorConfig.setEncoding(getEncoding().getOrNull());
        bjoernGeneratorConfig.setSpecRecursive(getSpecRecursive().getOrElse(false));
        new BjoernCodeGeneratorApplication(bjoernGeneratorConfig).generateBjoernClasses();
    }
}
