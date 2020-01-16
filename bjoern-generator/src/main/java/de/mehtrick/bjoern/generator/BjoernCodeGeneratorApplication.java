package de.mehtrick.bjoern.generator;

import de.mehtrick.bjoern.base.AbstractBjoernGenerator;
import de.mehtrick.bjoern.base.BjoernGeneratorException;
import de.mehtrick.bjoern.base.BjoernMissingPropertyException;
import de.mehtrick.bjoern.generator.junitsupport.NotSupportedJunitVersionException;
import de.mehtrick.bjoern.parser.BjoernParser;
import de.mehtrick.bjoern.parser.modell.Bjoern;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

/**
 * Main class to generate java-test based on bjoern files
 */
public class BjoernCodeGeneratorApplication extends AbstractBjoernGenerator {

	/**
	 * Creates a new CodeGenerator Instance and validates the configuration.
	 *
	 * @param bjoernGeneratorConfig
	 * @throws BjoernMissingPropertyException
	 */
	public BjoernCodeGeneratorApplication(BjoernCodeGeneratorConfig bjoernGeneratorConfig) throws BjoernMissingPropertyException {
		super(bjoernGeneratorConfig);
	}

	public static void main(String[] args) throws BjoernMissingPropertyException, IOException, NotSupportedJunitVersionException {
		BjoernCodeGeneratorConfig bjoernGeneratorConfig = new BjoernCodeGeneratorConfig(args);
		new BjoernCodeGeneratorApplication(bjoernGeneratorConfig).generateBjoernClasses();
	}

	public void generateBjoernClasses() throws IOException {
		File file = new File(bjoernGeneratorConfig.getGendir());
		cleanGenDir(file);
		if (bjoernGeneratorConfig.isFoldersSet()) {
			File[] files = getFilesFromFolder(bjoernGeneratorConfig.getFolder());
			Arrays.asList(files).stream().forEach(f -> generateSingleBjoern(f.getPath()));
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
			new BjoernCodeGenerator((BjoernCodeGeneratorConfig) bjoernGeneratorConfig).generateAndWriteToSystem(bjoern);
		} catch (Throwable e) {
			throw new BjoernGeneratorException(path, e);
		}
	}

}
