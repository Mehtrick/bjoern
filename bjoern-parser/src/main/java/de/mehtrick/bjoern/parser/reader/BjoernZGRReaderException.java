package de.mehtrick.bjoern.parser.reader;

public class BjoernZGRReaderException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public BjoernZGRReaderException(String path, Throwable cause) {
		super(String.format("The File under %s could not be read. " + cause.getMessage(), path), cause);
	}

}
