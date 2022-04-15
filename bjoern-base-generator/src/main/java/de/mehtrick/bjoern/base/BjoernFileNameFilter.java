package de.mehtrick.bjoern.base;

import de.mehtrick.bjoern.parser.reader.BjoernFileExtensions;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.FilenameFilter;

/**
 * only certain files will be recognized. The list of supported file extension can be found in
 * <p>
 * {@link de.mehtrick.bjoern.parser.reader.BjoernFileExtensions}
 */
public class BjoernFileNameFilter implements FilenameFilter {


    private static final String FILENAMEEXTENSION = "." + BjoernFileExtensions.zgr;

    @Override
    public boolean accept(File dir, String name) {
        String lowercaseName = name.toLowerCase();
        return (StringUtils.endsWith(lowercaseName, FILENAMEEXTENSION));
    }
}
