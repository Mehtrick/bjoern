package de.mehtrick.bjoern.asciidoc;

import de.mehtrick.bjoern.base.BjoernMissingPropertyException;
import de.mehtrick.bjoern.base.NotSupportedJunitVersionException;
import de.mehtrick.bjoern.doc.BjoernDocApplication;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

import static org.assertj.core.api.Assertions.assertThat;

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

	@Test
	@DisplayName("Test Doc Generation with Git History enabled generates Git History table")
	public void testDocGenerationWithGitHistory() throws IOException, BjoernMissingPropertyException, NotSupportedJunitVersionException {
		BjoernDocApplication.main(new String[]{"path=src/test/resources/bjoern.zgr", "docdir=src/gen/resources", "gitHistory=true"});
		File generatedFile = new File("src/gen/resources/bjoern.adoc");
		assertThat(generatedFile).exists();
		String content = new String(Files.readAllBytes(generatedFile.toPath()), StandardCharsets.UTF_8);
		assertThat(content).contains("== Git History");
		assertThat(content).contains("*Commit-Datum*");
		assertThat(content).contains("*Committer*");
		assertThat(content).contains("*Commit-Kommentar*");
	}

	@Test
	@DisplayName("Test Doc Generation without Git History does not generate Git History table")
	public void testDocGenerationWithoutGitHistory() throws IOException, BjoernMissingPropertyException, NotSupportedJunitVersionException {
		BjoernDocApplication.main(new String[]{"path=src/test/resources/bjoern.zgr", "docdir=src/gen/resources"});
		File generatedFile = new File("src/gen/resources/bjoern.adoc");
		assertThat(generatedFile).exists();
		String content = new String(Files.readAllBytes(generatedFile.toPath()), StandardCharsets.UTF_8);
		assertThat(content).doesNotContain("== Git History");
	}

}
