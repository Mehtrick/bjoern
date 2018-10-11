package de.mehtrick.jvior.asciidoc;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Map;

import org.apache.commons.io.FilenameUtils;

import com.fasterxml.jackson.databind.ObjectMapper;

import de.mehtrick.jvior.base.JviorGeneratorConfig;
import de.mehtrick.jvior.parser.modell.Jvior;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;
import freemarker.template.Version;

public class JviorAsciiDocGenerator {

	public static void generate(Jvior jvior) throws IOException, TemplateException {
		Configuration cfg = createDefaultFreemarkerConfig();
		Map<String, Object> convertValue = new ObjectMapper().convertValue(jvior, Map.class);
		Template temp = cfg.getTemplate("asciidoc.ftlh");
		StringWriter stringWriter = new StringWriter();
		temp.process(convertValue, stringWriter);
		writeToFile(stringWriter.toString(), jvior.getFilePath());
		stringWriter.close();
	}

	private static void writeToFile(String text, String path) throws IOException {
		cleanDocDir();
		String filename = FilenameUtils.removeExtension(new File(path).getName());
		File asciiDocFile = new File(JviorGeneratorConfig.getDocdir() + "/" + filename + ".adoc");
		try (PrintWriter out = new PrintWriter(asciiDocFile)) {
			out.println(text);
		}

	}

	private static void cleanDocDir() {
		File gendir = new File(JviorGeneratorConfig.getDocdir());
		gendir.deleteOnExit();
		gendir.mkdirs();
	}

	private static Configuration createDefaultFreemarkerConfig() throws IOException {
		Configuration cfg = new Configuration(new Version(2, 3, 23));
		cfg.setDirectoryForTemplateLoading(new File("src/main/resources"));
		cfg.setDefaultEncoding("UTF-8");
		cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
		return cfg;
	}

}
