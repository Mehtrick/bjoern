package de.mehtrick.jvior.base;

import java.io.File;
import java.io.FilenameFilter;

public class JviorFileNameFilter implements FilenameFilter {

	@Override
	public boolean accept(File dir, String name) {
		String lowercaseName = name.toLowerCase();
		return (lowercaseName.endsWith(".yml") || lowercaseName.endsWith(".yaml"));
	}
}
