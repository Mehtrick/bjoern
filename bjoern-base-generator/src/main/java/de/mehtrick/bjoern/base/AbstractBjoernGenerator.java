package de.mehtrick.bjoern.base;

import java.io.File;
import java.io.FileNotFoundException;

public abstract class AbstractBjoernGenerator {
	protected static File[] getFilesFromFolder(String folder) throws FileNotFoundException {
		File file = new File(folder);
		return file.listFiles(new BjoernFileNameFilter());
	}
}
