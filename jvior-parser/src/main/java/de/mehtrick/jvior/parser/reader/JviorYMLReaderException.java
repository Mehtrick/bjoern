package de.mehtrick.jvior.parser.reader;

public class JviorYMLReaderException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * @param message
	 * @param cause
	 */
	public JviorYMLReaderException(Throwable cause) {
		super("The File could not be read", cause);
	}

}
