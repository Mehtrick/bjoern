package de.mehtrick.bjoern.asciidoc;

import de.mehtrick.bjoern.base.BjoernMissingPropertyException;
import de.mehtrick.bjoern.base.NotSupportedJunitVersionException;
import de.mehtrick.bjoern.doc.BjoernDocApplication;
import org.apache.commons.io.FileUtils;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.lib.PersonIdent;
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
	@DisplayName("Test Doc Generation with Version")
	public void testDocGenerationWithVersion() throws IOException, BjoernMissingPropertyException, NotSupportedJunitVersionException {
		BjoernDocApplication.main(new String[]{"path=src/test/resources/version.zgr", "docdir=src/gen/resources"});
		File generatedFile = new File("src/gen/resources/version.adoc");
		assertThat(generatedFile).exists();
		String content = new String(Files.readAllBytes(generatedFile.toPath()), StandardCharsets.UTF_8);
		assertThat(content).contains("= Test mit Version");
		assertThat(content).contains("Version: 1.0.0");
	}

	@Test
	@DisplayName("Test Doc Generation with Reference")
	public void testDocGenerationWithReference() throws IOException, BjoernMissingPropertyException, NotSupportedJunitVersionException {
		BjoernDocApplication.main(new String[]{"path=src/test/resources/reference.zgr", "docdir=src/gen/resources"});
		File generatedFile = new File("src/gen/resources/reference.adoc");
		assertThat(generatedFile).exists();
		String content = new String(Files.readAllBytes(generatedFile.toPath()), StandardCharsets.UTF_8);
		assertThat(content).contains("= Test mit Reference");
		assertThat(content).contains("Reference: link:https://example.com/TICKET-123[TICKET-123]");
	}

	@Test
	@DisplayName("Test Doc Generation with Changelog")
	public void testDocGenerationWithChangelog() throws IOException, BjoernMissingPropertyException, NotSupportedJunitVersionException {
		BjoernDocApplication.main(new String[]{"path=src/test/resources/changelog.zgr", "docdir=src/gen/resources"});
		File generatedFile = new File("src/gen/resources/changelog.adoc");
		assertThat(generatedFile).exists();
		String content = new String(Files.readAllBytes(generatedFile.toPath()), StandardCharsets.UTF_8);
		assertThat(content).contains("= Test mit Changelog");
		assertThat(content).contains("Changelog: This is a changelog entry describing what changed in this spec.");
	}

	@Test
	@DisplayName("Test Doc Generation without Changelog does not include changelog section")
	public void testDocGenerationWithoutChangelog() throws IOException, BjoernMissingPropertyException, NotSupportedJunitVersionException {
		BjoernDocApplication.main(new String[]{"path=src/test/resources/version.zgr", "docdir=src/gen/resources"});
		File generatedFile = new File("src/gen/resources/version.adoc");
		assertThat(generatedFile).exists();
		String content = new String(Files.readAllBytes(generatedFile.toPath()), StandardCharsets.UTF_8);
		assertThat(content).contains("= Test mit Version");
		assertThat(content).doesNotContain("Changelog:");
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
	public void testDocGenerationWithGitHistory() throws Exception {
		File tmpDir = Files.createTempDirectory("bjoern-git-test").toFile();
		try {
			File specFile = new File(tmpDir, "test.zgr");
			FileUtils.copyFile(new File("src/test/resources/bjoern.zgr"), specFile);

			// Init a controlled git repo and commit the spec file so the test is deterministic
			try (Git git = Git.init().setDirectory(tmpDir).call()) {
				git.add().addFilepattern("test.zgr").call();
				PersonIdent person = new PersonIdent("Testuser", "test@test.com");
				git.commit().setAuthor(person).setCommitter(person).setMessage("Test commit").call();
			}

			File docDir = new File(tmpDir, "docs");
			BjoernDocApplication.main(new String[]{
					"path=" + specFile.getAbsolutePath(),
					"docdir=" + docDir.getAbsolutePath(),
					"gitHistory=true"
			});

			File generatedFile = new File(docDir, "test.adoc");
			assertThat(generatedFile).exists();
			String content = new String(Files.readAllBytes(generatedFile.toPath()), StandardCharsets.UTF_8);
			assertThat(content).contains("== Git History");
			assertThat(content).contains("*Date*");
			assertThat(content).contains("*Author*");
			assertThat(content).contains("*Message*");
			assertThat(content).contains("Testuser");
			assertThat(content).contains("Test commit");
		} finally {
			FileUtils.deleteDirectory(tmpDir);
		}
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
