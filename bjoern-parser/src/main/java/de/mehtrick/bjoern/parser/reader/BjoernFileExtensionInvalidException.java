package de.mehtrick.bjoern.parser.reader;

import org.apache.commons.lang3.StringUtils;

public class BjoernFileExtensionInvalidException extends RuntimeException {

	public static final String MESSAGE = "File extension must be any of:" + StringUtils.join(BjoernFileExtensions.getValuesAsString(),", ");
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public BjoernFileExtensionInvalidException() {
		super(MESSAGE);
	}
}
