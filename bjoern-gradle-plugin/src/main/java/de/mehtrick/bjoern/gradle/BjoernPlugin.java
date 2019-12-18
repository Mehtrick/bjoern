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
        createBjoernExtension(target);
        createBjoernCodeGeneratorTask(target);
        createBjoernDocGeneratorTask(target);
    }

    private void createBjoernExtension(Project target) {
        target.getExtensions().add(GEN_TASK_CONFIG, BjoernGeneratorExtension.class);
    }

    private void createBjoernDocGeneratorTask(Project target) {
        BjoernDocGeneratorTask bjoernDocGeneratorTask = target.getTasks().create(DOC_TASK_NAME, BjoernDocGeneratorTask.class);
        bjoernDocGeneratorTask.setDescription("Generates documentation of the given bjoern files");
        bjoernDocGeneratorTask.setGroup(GROUP);
    }

    private void createBjoernCodeGeneratorTask(Project target) {
        BjoernGeneratorTask bjoernGeneratorTask = target.getTasks().create(GEN_TASK_NAME, BjoernGeneratorTask.class);
        bjoernGeneratorTask.setGroup(GROUP);
        bjoernGeneratorTask.setDescription("Generates junit files based on the bjoern spec. Automatically runs after compileTestJava");
        Task compileTestJavaTask = target.getTasks().findByName("compileTestJava");
        if (compileTestJavaTask != null) {
            compileTestJavaTask.dependsOn(bjoernGeneratorTask);
        }
    }
}
