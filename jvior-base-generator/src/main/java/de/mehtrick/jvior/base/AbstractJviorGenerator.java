package de.mehtrick.jvior.base;

import java.io.File;
import java.io.FileNotFoundException;

public abstract class AbstractJviorGenerator {
	protected static File[] getFilesFromFolder(String folder) throws FileNotFoundException {
		File file = new File(folder);
		File[] ymlFiles = file.listFiles(new JviorFileNameFilter());
		if (ymlFiles.length == 0) {
			throw new FileNotFoundException("No yaml files Found in Folder " + folder
					+ ". The file has to end with .yml or .yaml to be detected");
		}
		return file.listFiles(new JviorFileNameFilter());
	}
}
