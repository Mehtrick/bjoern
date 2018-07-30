package de.mehtrick.jvior.parser.reader;

class JviorYMLReaderException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * @param path
	 * @param message
	 * @param cause
	 */
	public JviorYMLReaderException(String path, Throwable cause) {
		super(String.format("The File under %s could not be read", path), cause);
	}

}
