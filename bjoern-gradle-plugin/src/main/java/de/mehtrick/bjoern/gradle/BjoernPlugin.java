package de.mehtrick.bjoern.gradle;

import org.gradle.api.Plugin;
import org.gradle.api.Project;

import java.security.acl.Group;

public class BjoernPlugin implements Plugin<Project> {
	static final String GEN_TASK_CONFIG = "bjoern";
	static final String GEN_TASK_NAME = "bjoernGen";
	static final String DOC_TASK_NAME = "bjoernDoc";
	public static final String GROUP = "BjoernFramework";

	@Override
	public void apply(Project target) {
		target.getExtensions().add(GEN_TASK_CONFIG, BjoernGeneratorExtension.class);
		target.getTasks().create(GEN_TASK_NAME, BjoernGeneratorTask.class).setGroup(GROUP);
		target.getTasks().create(DOC_TASK_NAME, BjoernDocGeneratorTask.class).setGroup(GROUP);
	}
}
