package de.mehtrick.bjoern.asciidoc;

import java.io.IOException;

import org.junit.Test;

import de.mehtrick.bjoern.base.BjoernMissingPropertyException;
import de.mehtrick.bjoern.doc.BjoernDocApplication;
import freemarker.template.TemplateException;

public class AsciiDocBuildTest {

	@Test
	public void testFreemarker() throws IOException, TemplateException, BjoernMissingPropertyException {
		BjoernDocApplication.main(new String[] { "folder=src/test/resources/", "docdir=src/gen/resources" });
	}

}
