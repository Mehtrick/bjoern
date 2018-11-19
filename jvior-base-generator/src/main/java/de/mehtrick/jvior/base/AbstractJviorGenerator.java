package de.mehtrick.jvior.base;

import java.io.File;
import java.io.FileNotFoundException;

public abstract class AbstractJviorGenerator {
	protected static File[] getFilesFromFolder(String folder) throws FileNotFoundException {
		File file = new File(folder);
		return file.listFiles(new JviorFileNameFilter());
	}
}
