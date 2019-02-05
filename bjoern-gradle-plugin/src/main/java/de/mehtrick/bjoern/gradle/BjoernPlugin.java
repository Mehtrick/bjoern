package de.mehtrick.bjoern.gradle;

import org.gradle.api.Plugin;
import org.gradle.api.Project;

public class BjoernPlugin implements Plugin<Project> {
	static final String GEN_TASK_CONFIG = "bjoern";
	static final String GEN_TASK_NAME = "bjoernGen";
	static final String DOC_TASK_NAME = "bjoernDoc";

	@Override
	public void apply(Project target) {
		target.getExtensions().add(GEN_TASK_CONFIG, BjoernGeneratorExtension.class);
		target.getTasks().create(GEN_TASK_NAME, BjoernGeneratorTask.class);
		target.getTasks().create(DOC_TASK_NAME, BjoernDocGeneratorTask.class);
	}
}