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

	public static void main(String[] args) throws BjoernMissingPropertyException, FileNotFoundException {
		BjoernGeneratorConfig.init(args);
		generateBjoernDocs();
	}

	public static void generateBjoernDocs() throws BjoernMissingPropertyException, FileNotFoundException {
		BjoernGeneratorConfig.validate();
		if (StringUtils.isAllBlank(BjoernGeneratorConfig.getDocdir())) {
			throw new BjoernMissingPropertyException("Please configure the docDir where the documentation will be generated");
		}
		if (BjoernGeneratorConfig.isFoldersSet()) {
			File[] files = getFilesFromFolder(BjoernGeneratorConfig.getFolder());
			Arrays.asList(files).forEach(f -> generateSingleBjoernDocs(f.getPath()));
		} else {
			generateSingleBjoernDocs(BjoernGeneratorConfig.getPath());
		}
	}

	private static void generateSingleBjoernDocs(String path) {
		try {
			Bjoern bjoern = BjoernParser.parseSpec(path);
			new BjoernDocGenerator().generate(bjoern);
		} catch (Throwable e) {
			throw new BjoernGeneratorException(path, e);
		}
	}

}
