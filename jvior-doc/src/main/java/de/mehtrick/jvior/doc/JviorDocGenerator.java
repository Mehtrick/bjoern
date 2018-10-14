package de.mehtrick.jvior.doc;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Map;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.databind.ObjectMapper;

import de.mehtrick.jvior.base.JviorGeneratorConfig;
import de.mehtrick.jvior.parser.modell.Jvior;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;
import freemarker.template.Version;

public class JviorDocGenerator {

	public void generate(Jvior jvior) throws IOException, TemplateException {
		Configuration cfg = createDefaultFreemarkerConfig();
		@SuppressWarnings("unchecked")
		Map<String, Object> convertValue = new ObjectMapper().convertValue(jvior, Map.class);
		Template temp = cfg.getTemplate(JviorGeneratorConfig.getTemplate());
		StringWriter stringWriter = new StringWriter();
		temp.process(convertValue, stringWriter);
		writeToFile(stringWriter.toString(), jvior.getFilePath());
		stringWriter.close();
	}

	private void writeToFile(String text, String path) throws IOException {
		cleanDocDir();
		String filename = FilenameUtils.removeExtension(new File(path).getName());
		File asciiDocFile = new File(
				JviorGeneratorConfig.getDocdir() + "/" + filename + "." + JviorGeneratorConfig.getDocExtension());
		try (PrintWriter out = new PrintWriter(asciiDocFile)) {
			out.println(text);
		}

	}

	private void cleanDocDir() {
		File gendir = new File(JviorGeneratorConfig.getDocdir());
		gendir.deleteOnExit();
		gendir.mkdirs();
	}

	private Configuration createDefaultFreemarkerConfig() throws IOException {
		Configuration cfg = new Configuration(new Version(2, 3, 23));
		if (StringUtils.isNotBlank(JviorGeneratorConfig.getTemplateFolder())) {
			cfg.setDirectoryForTemplateLoading(new File(JviorGeneratorConfig.getTemplateFolder()));
		} else {
			cfg.setClassForTemplateLoading(this.getClass(), "/");
		}
		cfg.setDefaultEncoding("UTF-8");
		cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
		return cfg;
	}

}
