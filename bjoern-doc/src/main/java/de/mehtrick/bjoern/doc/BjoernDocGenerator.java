package de.mehtrick.bjoern.doc;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Map;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.databind.ObjectMapper;

import de.mehtrick.bjoern.base.BjoernGeneratorConfig;
import de.mehtrick.bjoern.parser.modell.Bjoern;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;
import freemarker.template.Version;

public class BjoernDocGenerator {

	public void generate(Bjoern bjoern) throws IOException, TemplateException {
		Configuration cfg = createDefaultFreemarkerConfig();
		@SuppressWarnings("unchecked")
		Map<String, Object> convertValue = new ObjectMapper().convertValue(bjoern, Map.class);
		Template temp = cfg.getTemplate(BjoernGeneratorConfig.getTemplate());
		StringWriter stringWriter = new StringWriter();
		temp.process(convertValue, stringWriter);
		writeToFile(stringWriter.toString(), bjoern.getFilePath());
		stringWriter.close();
	}

	private void writeToFile(String text, String path) throws IOException {
		cleanDocDir();
		String filename = FilenameUtils.removeExtension(new File(path).getName());
		File asciiDocFile = new File(
				BjoernGeneratorConfig.getDocdir() + "/" + filename + "." + BjoernGeneratorConfig.getDocExtension());
		try (PrintWriter out = new PrintWriter(asciiDocFile)) {
			out.println(text);
		}

	}

	private void cleanDocDir() {
		File gendir = new File(BjoernGeneratorConfig.getDocdir());
		gendir.deleteOnExit();
		gendir.mkdirs();
	}

	private Configuration createDefaultFreemarkerConfig() throws IOException {
		Configuration cfg = new Configuration(new Version(2, 3, 23));
		if (StringUtils.isNotBlank(BjoernGeneratorConfig.getTemplateFolder())) {
			cfg.setDirectoryForTemplateLoading(new File(BjoernGeneratorConfig.getTemplateFolder()));
		} else {
			cfg.setClassForTemplateLoading(this.getClass(), "/");
		}
		cfg.setDefaultEncoding("UTF-8");
		cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
		return cfg;
	}

}
