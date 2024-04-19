package de.mehtrick.bjoern.gradle;

import de.mehtrick.bjoern.generator.BjoernCodeGeneratorApplication;
import de.mehtrick.bjoern.generator.BjoernCodeGeneratorConfig;
import org.gradle.api.DefaultTask;
import org.gradle.api.tasks.TaskAction;

public class BjoernGeneratorTask extends DefaultTask {

    @TaskAction
    public void genBjoernCode() throws Exception {
        BjoernGeneratorExtension extension = getProject().getExtensions().getByType(BjoernGeneratorExtension.class);
        BjoernCodeGeneratorConfig bjoernGeneratorConfig = new BjoernCodeGeneratorConfig();
        bjoernGeneratorConfig.setPath(extension.getPath());
        bjoernGeneratorConfig.setFolder(extension.getFolder());
        bjoernGeneratorConfig.setGendir(extension.getGendir());
        bjoernGeneratorConfig.setPckg(extension.getPckg());
        bjoernGeneratorConfig.setExtendedTestclass(extension.getExtendedTestClass());
        bjoernGeneratorConfig.setJunitVersion(extension.getJunitVersion());
        bjoernGeneratorConfig.setEncoding(extension.getEncoding());
        bjoernGeneratorConfig.setSpecRecursive(extension.isSpecRecursive());
        new BjoernCodeGeneratorApplication(bjoernGeneratorConfig).generateBjoernClasses();

    }
}
