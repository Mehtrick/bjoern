package de.mehtrick.bjoern.base;

import java.io.File;

/**
 * This class is the Base for all the generators, and provides functionality to load the correct files
 */
public abstract class AbstractBjoernGenerator extends BjoernGeneratorConfigProvided {


	/**
	 * @param bjoernGeneratorConfig the default conif
	 * @throws BjoernMissingPropertyException - Shows that a property is missing
	 */
	public AbstractBjoernGenerator(BjoernGeneratorConfig bjoernGeneratorConfig) throws BjoernMissingPropertyException {
		super(bjoernGeneratorConfig);
		bjoernGeneratorConfig.validate();
	}

	/**
	 * Loads all bjoern files from an specific folder.
	 *
	 * @param folder the folder to search (non recursive)
	 * @return the found bjoern files
	 */
	public static File[] getFilesFromFolder(String folder) {

		File file = new File(folder);
		if (!file.exists()) {
			System.out.println("WARNING: The folder " + folder + " could not be found. Nothing has been generated.");
			return new File[0];
		}
		return file.listFiles(new BjoernFileNameFilter());
	}
}
