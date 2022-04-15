package de.mehtrick.bjoern.base;

import de.mehtrick.bjoern.parser.reader.BjoernFileExtensions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FilenameFilter;

/**
 * only certain files will be recognized. The list of supported file extension can be found in
 * <p>
 * {@link de.mehtrick.bjoern.parser.reader.BjoernFileExtensions}
 */
public class BjoernFileNameFilter implements FilenameFilter {
	private static final Logger log = LoggerFactory.getLogger(BjoernFileNameFilter.class);


	private static final String FILENAMEEXTENSION = "."+BjoernFileExtensions.zgr;

	@Override
	public boolean accept(File dir, String name) {
		log.info(name);
		String lowercaseName = name.toLowerCase();
		return (lowercaseName.endsWith(FILENAMEEXTENSION));
	}
}
