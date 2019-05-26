package de.mehtrick.bjoern.parser.validator;

import java.util.List;
import java.util.stream.Collectors;

public class EmptyStatementException extends RuntimeException {

	public EmptyStatementException(List<Integer> emptyStatementLines) {
		super(String.format("Error in line(s): %s The statement(s) must not be empty",
				emptyStatementLines.stream().map(Object::toString).collect(Collectors.joining(","))));
	}

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

}
