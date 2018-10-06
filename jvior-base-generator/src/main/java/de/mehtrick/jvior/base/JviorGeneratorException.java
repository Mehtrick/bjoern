package de.mehtrick.jvior.base;

public class JviorGeneratorException extends RuntimeException {

	public JviorGeneratorException(String path, Throwable e) {
		super("Error generating file from " + path, e);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
