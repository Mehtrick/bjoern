package jviorplugin.de.mehtrick.jvior.gradle;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import java.io.File;

import org.gradle.internal.impldep.org.junit.Rule;
import org.gradle.internal.impldep.org.junit.rules.TemporaryFolder;
import org.gradle.testkit.runner.BuildResult;
import org.gradle.testkit.runner.GradleRunner;
import org.junit.Test;

public class JviorGradleTest {
	@Rule
	public final TemporaryFolder testProjectDir = new TemporaryFolder();

	@Test
	public void test() throws Exception {
	}

}
