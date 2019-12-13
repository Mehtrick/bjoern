package de.mehtrick.bjoern.asciidoc;

import de.mehtrick.bjoern.base.BjoernMissingPropertyException;
import de.mehtrick.bjoern.base.NotSupportedJunitVersionException;
import de.mehtrick.bjoern.doc.BjoernDocApplication;
import freemarker.template.TemplateException;
import org.junit.Test;

import java.io.IOException;

public class AsciiDocBuildTest {

	@Test
	public void testFreemarker() throws IOException, TemplateException, BjoernMissingPropertyException, NotSupportedJunitVersionException {
		BjoernDocApplication.main(new String[]{"folder=src/test/resources/", "docdir=src/gen/resources"});
	}

}
