package de.mehtrick.bjoern.generator;

import de.mehtrick.bjoern.base.BjoernMissingPropertyException;
import de.mehtrick.bjoern.base.NotSupportedJunitVersionException;
import org.junit.Test;

import java.io.IOException;

/*
 * This Java source file was generated by the Gradle 'init' task.
 */

public class BjoernGeneratorTest {
	@Test
	public void testSomeLibraryMethod() throws BjoernMissingPropertyException, IOException, NotSupportedJunitVersionException {
		BjoernCodeGeneratorApplication.main(new String[]{"path=src/test/resources/specification/bjoern.zgr",
				"folder=src/test/resources/", "package=de.mehtrick.bjoern", "gendir=src/gen/java",
				"extendedTestclass=de.mehtrick.bjoern.AbstractTestclass", "junitVersion=4"});
	}

	@Test
	public void testMissingParameter() throws BjoernMissingPropertyException, IOException, NotSupportedJunitVersionException {
		BjoernCodeGeneratorApplication.main(new String[]{"path=src/test/resources/bjoern.yaml",
				"folder=src/test/resources/", "package=de.mehtrick.bjoern", "gendir=src/gen/java",
				"extendedTestclass=de.mehtrick.bjoern.AbstractTestclass", "junitVersion=5"});
	}

}
