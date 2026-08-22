package de.mehtrick.bjoern.gradle;

import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.Task;

public class BjoernPlugin implements Plugin<Project> {
    public static final String GROUP = "BjoernFramework";
    static final String GEN_TASK_CONFIG = "bjoern";
    static final String GEN_TASK_NAME = "bjoernGen";
    static final String DOC_TASK_NAME = "bjoernDoc";

    @Override
    public void apply(Project target) {
        BjoernGeneratorExtension extension = createBjoernExtension(target);
        createBjoernCodeGeneratorTask(target, extension);
        createBjoernDocGeneratorTask(target, extension);
    }

    private BjoernGeneratorExtension createBjoernExtension(Project target) {
        return target.getExtensions().create(GEN_TASK_CONFIG, BjoernGeneratorExtension.class);
    }

    private void createBjoernDocGeneratorTask(Project target, BjoernGeneratorExtension extension) {
        BjoernDocGeneratorTask bjoernDocGeneratorTask = target.getTasks().create(DOC_TASK_NAME, BjoernDocGeneratorTask.class);
        bjoernDocGeneratorTask.setDescription("Generates documentation of the given bjoern files");
        bjoernDocGeneratorTask.setGroup(GROUP);
        target.afterEvaluate(p -> {
            bjoernDocGeneratorTask.getSpecPath().set(extension.getPath());
            bjoernDocGeneratorTask.getFolder().set(extension.getFolder());
            bjoernDocGeneratorTask.getDocdir().set(extension.getDocdir());
            bjoernDocGeneratorTask.getTemplate().set(extension.getTemplate());
            bjoernDocGeneratorTask.getTemplateFolder().set(extension.getTemplateFolder());
            bjoernDocGeneratorTask.getDocExtension().set(extension.getDocExtension());
            bjoernDocGeneratorTask.getSpecRecursive().set(extension.isSpecRecursive());
            bjoernDocGeneratorTask.getGitHistory().set(extension.isGitHistory());
        });
    }

    private void createBjoernCodeGeneratorTask(Project target, BjoernGeneratorExtension extension) {
        BjoernGeneratorTask bjoernGeneratorTask = target.getTasks().create(GEN_TASK_NAME, BjoernGeneratorTask.class);
        bjoernGeneratorTask.setGroup(GROUP);
        bjoernGeneratorTask.setDescription("Generates junit files based on the bjoern spec. Automatically runs after compileTestJava");
        target.afterEvaluate(p -> {
            bjoernGeneratorTask.getSpecPath().set(extension.getPath());
            bjoernGeneratorTask.getFolder().set(extension.getFolder());
            bjoernGeneratorTask.getGendir().set(extension.getGendir());
            bjoernGeneratorTask.getPckg().set(extension.getPckg());
            bjoernGeneratorTask.getExtendedTestClass().set(extension.getExtendedTestClass());
            bjoernGeneratorTask.getJunitVersion().set(extension.getJunitVersion());
            bjoernGeneratorTask.getEncoding().set(extension.getEncoding());
            bjoernGeneratorTask.getSpecRecursive().set(extension.isSpecRecursive());
        });
        Task compileTestJavaTask = target.getTasks().findByName("compileTestJava");
        if (compileTestJavaTask != null) {
            compileTestJavaTask.dependsOn(bjoernGeneratorTask);
        }
    }
}
