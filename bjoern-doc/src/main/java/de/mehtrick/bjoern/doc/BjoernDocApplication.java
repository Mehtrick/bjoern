package de.mehtrick.bjoern.doc;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;

import org.apache.commons.lang3.StringUtils;

import de.mehtrick.bjoern.base.AbstractBjoernGenerator;
import de.mehtrick.bjoern.base.BjoernGeneratorConfig;
import de.mehtrick.bjoern.base.BjoernGeneratorException;
import de.mehtrick.bjoern.base.BjoernMissingPropertyException;
import de.mehtrick.bjoern.parser.BjoernParser;
import de.mehtrick.bjoern.parser.modell.Bjoern;

public class BjoernDocApplication extends AbstractBjoernGenerator {

	public BjoernDocApplication(BjoernGeneratorConfig bjoernGeneratorConfig) {
		super(bjoernGeneratorConfig);
	}

	public static void main(String[] args) throws BjoernMissingPropertyException, FileNotFoundException {
		BjoernGeneratorConfig bjoernGeneratorConfig = new BjoernGeneratorConfig(args);
		new BjoernDocApplication(bjoernGeneratorConfig).generateBjoernDocs();
	}

	public void generateBjoernDocs() throws BjoernMissingPropertyException, FileNotFoundException {
		bjoernGeneratorConfig.validate();
		if (StringUtils.isAllBlank(bjoernGeneratorConfig.getDocdir())) {
			throw new BjoernMissingPropertyException("Please configure the docDir where the documentation will be generated");
		}
		if (bjoernGeneratorConfig.isFoldersSet()) {
			File[] files = getFilesFromFolder(bjoernGeneratorConfig.getFolder());
			Arrays.asList(files).forEach(f -> generateSingleBjoernDocs(f.getPath(),bjoernGeneratorConfig));
		} else {
			generateSingleBjoernDocs(bjoernGeneratorConfig.getPath(),bjoernGeneratorConfig);
		}
	}

	private void generateSingleBjoernDocs(String path, BjoernGeneratorConfig bjoernGeneratorConfig) {
		try {
			Bjoern bjoern = BjoernParser.parseSpec(path);
			new BjoernDocGenerator(bjoernGeneratorConfig).generate(bjoern);
		} catch (Throwable e) {
			throw new BjoernGeneratorException(path, e);
		}
	}

}
