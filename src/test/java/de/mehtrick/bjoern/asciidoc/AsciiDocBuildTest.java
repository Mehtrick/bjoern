package de.mehtrick.bjoern.asciidoc;

import de.mehtrick.bjoern.base.BjoernMissingPropertyException;
import de.mehtrick.bjoern.base.NotSupportedJunitVersionException;
import de.mehtrick.bjoern.doc.BjoernDocApplication;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Test class for AsciiDoc generation
 * Tests the generation of AsciiDoc files from Bjoern files
 * @version 1.0

 */
public class AsciiDocBuildTest {

	@Test
	@DisplayName("Test Doc Generation")
	public void testDocGeneration() throws IOException, BjoernMissingPropertyException, NotSupportedJunitVersionException {
		BjoernDocApplication.main(new String[]{"path=src/test/resources/bjoern.zgr", "docdir=src/gen/resources"});
	}

	@Test
	@DisplayName("Test Generation of Empty Given")
	public void testGenerationOfEmptyGiven() throws BjoernMissingPropertyException, FileNotFoundException, NotSupportedJunitVersionException {
		BjoernDocApplication.main(new String[]{"path=src/test/resources/empty-given.zgr", "docdir=src/gen/resources"});
	}

	@Test
	@DisplayName("Test Generation of Empty When")
	public void testGenerationOfEmptyWhen() throws BjoernMissingPropertyException, FileNotFoundException, NotSupportedJunitVersionException {
		BjoernDocApplication.main(new String[]{"path=src/test/resources/empty-when.zgr", "docdir=src/gen/resources"});
	}

	@Test
	@DisplayName("Test Generation of Empty Then")
	public void testGenerationOfEmptyThen() throws BjoernMissingPropertyException, FileNotFoundException, NotSupportedJunitVersionException {
		BjoernDocApplication.main(new String[]{"path=src/test/resources/empty-then.zgr", "docdir=src/gen/resources"});
	}

}
