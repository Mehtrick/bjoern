package de.mehtrick.jvior.gradle;

import org.gradle.api.Plugin;
import org.gradle.api.Project;

public class JviorPlugin implements Plugin<Project> {
	static final String TASK_NAME = "jviorgen";

	@Override
	public void apply(Project target) {
		target.getTasks().create(TASK_NAME, JviorgGeneratorTask.class);
	}
}