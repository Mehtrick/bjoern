package de.mehtrick.jvior.gradle;

import org.gradle.api.Plugin;
import org.gradle.api.Project;

public class JviorPlugin implements Plugin<Project> {
	static final String GEN_TASK_NAME = "jviorGen";
	static final String DOC_TASK_NAME = "jviorDoc";

	@Override
	public void apply(Project target) {
		target.getExtensions().add(GEN_TASK_NAME, JviorGeneratorExtension.class);
		target.getTasks().create(GEN_TASK_NAME, JviorGeneratorTask.class);
		target.getTasks().create(DOC_TASK_NAME, JviorDocGeneratorTask.class);
	}
}