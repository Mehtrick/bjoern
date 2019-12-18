package de.mehtrick.bjoern.doc;

import de.mehtrick.bjoern.base.*;
import de.mehtrick.bjoern.parser.BjoernParser;
import de.mehtrick.bjoern.parser.modell.Bjoern;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;

/**
 * Main class to start the bjoern documentation generation
 */
public class BjoernDocApplication extends AbstractBjoernGenerator {

    public BjoernDocApplication(BjoernDocGeneratorConfig bjoernGeneratorConfig) throws BjoernMissingPropertyException {
        super(bjoernGeneratorConfig);
    }

    public static void main(String[] args) throws BjoernMissingPropertyException, FileNotFoundException, NotSupportedJunitVersionException {
        BjoernDocGeneratorConfig bjoernGeneratorConfig = new BjoernDocGeneratorConfig(args);
        new BjoernDocApplication(bjoernGeneratorConfig).generateBjoernDocs();
    }

    public void generateBjoernDocs() throws FileNotFoundException {
        if (bjoernGeneratorConfig.isFoldersSet()) {
            File[] files = getFilesFromFolder(bjoernGeneratorConfig.getFolder());
            Arrays.asList(files).parallelStream().forEach(f -> generateSingleBjoernDocs(f.getPath(), bjoernGeneratorConfig));
        } else {
            generateSingleBjoernDocs(bjoernGeneratorConfig.getPath(), bjoernGeneratorConfig);
        }
    }

    private void generateSingleBjoernDocs(String path, BjoernGeneratorConfig bjoernGeneratorConfig) {
        try {
            Bjoern bjoern = new BjoernParser().parseSpec(path, bjoernGeneratorConfig.getEncoding());
            new BjoernDocGenerator(bjoernGeneratorConfig).generate(bjoern);
        } catch (Throwable e) {
            throw new BjoernGeneratorException(path, e);
        }
    }

}
