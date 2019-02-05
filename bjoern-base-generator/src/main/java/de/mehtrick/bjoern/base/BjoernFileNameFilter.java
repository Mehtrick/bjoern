package de.mehtrick.bjoern.base;

import java.io.File;
import java.io.FilenameFilter;

import de.mehtrick.bjoern.parser.reader.BjoernFileExtensions;

public class BjoernFileNameFilter implements FilenameFilter {

	private static final String FILENAMEEXTENSION = "."+BjoernFileExtensions.zgr;

	@Override
	public boolean accept(File dir, String name) {
		String lowercaseName = name.toLowerCase();
		return (lowercaseName.endsWith(FILENAMEEXTENSION));
	}
}
