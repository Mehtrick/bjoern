package de.mehtrick.bjoern.base;

import java.io.File;
import java.io.FileNotFoundException;

public abstract class AbstractBjoernGenerator {
	protected static File[] getFilesFromFolder(String folder) throws FileNotFoundException {
		
		File file = new File(folder);
		if(!file.exists()) {
			System.out.println("WARNING: Der Ordner "+folder+" konnte nicht gefunden werden. Es wurde nichts generiert.");
			return new File[0];
		}
		return file.listFiles(new BjoernFileNameFilter());
	}
}
