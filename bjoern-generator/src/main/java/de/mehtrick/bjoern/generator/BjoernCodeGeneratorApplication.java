package de.mehtrick.bjoern.generator;

import de.mehtrick.bjoern.base.*;
import de.mehtrick.bjoern.parser.BjoernParser;
import de.mehtrick.bjoern.parser.modell.Bjoern;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

public class BjoernCodeGeneratorApplication extends AbstractBjoernGenerator {


	public BjoernCodeGeneratorApplication(BjoernGeneratorConfig bjoernGeneratorConfig) {
		super(bjoernGeneratorConfig);
	}

	public static void main(String[] args) throws BjoernMissingPropertyException, IOException, NotSupportedJunitVersionException {
		BjoernGeneratorConfig bjoernGeneratorConfig = new BjoernGeneratorConfig(args);
		new BjoernCodeGeneratorApplication(bjoernGeneratorConfig).generateBjoernClasses();
	}

	public void generateBjoernClasses() throws BjoernMissingPropertyException, IOException {
		bjoernGeneratorConfig.validate();
		if (StringUtils.isAllBlank(bjoernGeneratorConfig.getPckg())) {
			throw new BjoernMissingPropertyException(
					"Please configure the package name by setting the \"pckg\" property");
		}
		if (StringUtils.isAllBlank(bjoernGeneratorConfig.getGendir())) {
			throw new BjoernMissingPropertyException("Please configure the gendir where the classes will be generated");
		}
		File file = new File(bjoernGeneratorConfig.getGendir());
		cleanGenDir(file);
		if (bjoernGeneratorConfig.isFoldersSet()) {
			File[] files = getFilesFromFolder(bjoernGeneratorConfig.getFolder());
			Arrays.asList(files).forEach(f -> generateSingleBjoern(f.getPath()));
		} else {
			generateSingleBjoern(bjoernGeneratorConfig.getPath());
		}
	}

	private void cleanGenDir(File file) throws IOException {
		if(file.exists()) {
			FileUtils.forceDelete(file);
		}
	}

	private void generateSingleBjoern(String path) {
		try {
			Bjoern bjoern = new BjoernParser().parseSpec(path, bjoernGeneratorConfig.getEncoding());
			new BjoernCodeGenerator(bjoernGeneratorConfig).generate(bjoern);
		} catch (Throwable e) {
			throw new BjoernGeneratorException(path, e);
		}
	}

}
