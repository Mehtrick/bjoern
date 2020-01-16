package de.mehtrick.bjoern.asciidoc;

import de.mehtrick.bjoern.base.BjoernMissingPropertyException;
import de.mehtrick.bjoern.doc.BjoernDocApplication;
import org.junit.Test;

import java.io.IOException;

public class AsciiDocBuildTest {

	@Test
	public void testFreemarker() throws IOException, BjoernMissingPropertyException {
		BjoernDocApplication.main(new String[]{"folder=src/test/resources/", "docdir=src/gen/resources"});
	}

}
