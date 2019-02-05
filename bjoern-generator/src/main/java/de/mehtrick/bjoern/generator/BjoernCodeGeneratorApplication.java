package de.mehtrick.bjoern.generator;

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

public class BjoernCodeGeneratorApplication extends AbstractBjoernGenerator {

	public static void main(String[] args) throws BjoernMissingPropertyException, FileNotFoundException {
		BjoernGeneratorConfig.init(args);
		generateBjoernClasses();
	}

	public static void generateBjoernClasses() throws BjoernMissingPropertyException, FileNotFoundException {
		BjoernGeneratorConfig.validate();
		if (StringUtils.isAllBlank(BjoernGeneratorConfig.getPckg())) {
			throw new BjoernMissingPropertyException(
					"Please configure the package name by setting the \"pckg\" property");
		}
		if (StringUtils.isAllBlank(BjoernGeneratorConfig.getGendir())) {
			throw new BjoernMissingPropertyException("Please configure the gendir where the classes will be generated");
		}

		if (BjoernGeneratorConfig.isFoldersSet()) {
			File[] files = getFilesFromFolder(BjoernGeneratorConfig.getFolder());
			Arrays.asList(files).forEach(f -> generateSingleBjoern(f.getPath()));
		} else {
			generateSingleBjoern(BjoernGeneratorConfig.getPath());
		}
	}

	private static void generateSingleBjoern(String path) {
		try {
			Bjoern bjoern = BjoernParser.parseSpec(path);
			new BjoernCodeGenerator().generate(bjoern);
		} catch (Throwable e) {
			throw new BjoernGeneratorException(path, e);
		}
	}

}
