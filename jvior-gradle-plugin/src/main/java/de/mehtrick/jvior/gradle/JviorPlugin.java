package de.mehtrick.jvior.gradle;

import org.gradle.api.Plugin;
import org.gradle.api.Project;

public class JviorPlugin implements Plugin<Project> {
	static final String TASK_NAME = "jvior";

	@Override
	public void apply(Project target) {
		target.getExtensions().add("jvior", JviorGeneratorExtension.class);
		target.getTasks().create(TASK_NAME, JviorGeneratorTask.class);
	}
}