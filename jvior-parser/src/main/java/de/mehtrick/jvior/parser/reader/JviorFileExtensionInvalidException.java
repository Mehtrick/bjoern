package de.mehtrick.jvior.parser.reader;

import org.apache.commons.lang3.StringUtils;

public class JviorFileExtensionInvalidException extends RuntimeException {

	public static final String MESSAGE = "File extension must be any of:" + StringUtils.join(JviorFileExtensions.getValuesAsString(),", ");
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public JviorFileExtensionInvalidException() {
		super(MESSAGE);
	}
}
