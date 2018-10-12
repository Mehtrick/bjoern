package de.mehtrick.jvior.asciidoc;

import java.io.IOException;

import org.junit.Test;

import de.mehtrick.jvior.base.JviorMissingPropertyException;
import de.mehtrick.jvior.doc.JviorDocApplication;
import freemarker.template.TemplateException;

public class AsciiDocBuildTest {

	@Test
	public void testFreemarker() throws IOException, TemplateException, JviorMissingPropertyException {
		JviorDocApplication.main(new String[] { "folder=src/test/resources/", "docdir=src/gen/resources" });
	}

}
