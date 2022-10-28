package de.mehtrick.bjoern.doc;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.mehtrick.bjoern.base.BjoernGeneratorConfig;
import de.mehtrick.bjoern.base.BjoernGeneratorConfigProvided;
import de.mehtrick.bjoern.parser.modell.Bjoern;
import freemarker.template.*;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.util.Map;

/**
 * Generates documentation from bjoern files. The default ist ascii doc
 */
public class BjoernDocGenerator extends BjoernGeneratorConfigProvided {

    public BjoernDocGenerator(BjoernGeneratorConfig bjoernGeneratorConfig) {
        super(bjoernGeneratorConfig);
    }

    public void generate(Bjoern bjoern) throws IOException, TemplateException {
        Configuration cfg = createDefaultFreemarkerConfig();
        @SuppressWarnings("unchecked")
        //Converts the bjoern object into a map, so that freemarker can use the values of the file
        Map<String, Object> convertValue = new ObjectMapper().convertValue(bjoern, Map.class);
        Template template = cfg.getTemplate(bjoernGeneratorConfig.getTemplate(),bjoernGeneratorConfig.getEncoding().toString());
        try (StringWriter stringWriter = new StringWriter()) {
            template.process(convertValue, stringWriter);
            writeToFile(stringWriter.toString(), bjoern.getFilePath());
        }
    }

    private void writeToFile(String text, String path) throws IOException {
        cleanDocDir();
        String filename = FilenameUtils.removeExtension(new File(path).getName());
        File asciiDocFile = new File(
                bjoernGeneratorConfig.getDocdir() + File.separator + filename + "." + bjoernGeneratorConfig.getDocExtension());
        FileUtils.write(asciiDocFile, text, bjoernGeneratorConfig.getEncoding());
    }

    private void cleanDocDir() {
        File gendir = new File(bjoernGeneratorConfig.getDocdir());
        gendir.deleteOnExit();
        gendir.mkdirs();
    }

    /**
     * The freemarker config determines the folder, in which all of the template files can be found
     *
     * @return
     * @throws IOException
     */
    private Configuration createDefaultFreemarkerConfig() throws IOException {
        Configuration cfg = new Configuration(new Version(2, 3, 23));
        if (StringUtils.isNotBlank(bjoernGeneratorConfig.getTemplateFolder())) {
            cfg.setDirectoryForTemplateLoading(new File(bjoernGeneratorConfig.getTemplateFolder()));
        } else {
            cfg.setClassForTemplateLoading(this.getClass(), "/");
        }
        cfg.setDefaultEncoding(bjoernGeneratorConfig.getEncoding().displayName());
        cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
        return cfg;
    }

}
