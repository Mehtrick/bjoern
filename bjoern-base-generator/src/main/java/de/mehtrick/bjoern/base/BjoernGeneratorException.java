package de.mehtrick.bjoern.base;

public class BjoernGeneratorException extends RuntimeException {

	public BjoernGeneratorException(String path, Throwable e) {
		super("Error generating file from " + path, e);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
