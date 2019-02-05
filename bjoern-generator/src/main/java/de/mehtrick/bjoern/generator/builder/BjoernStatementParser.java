package de.mehtrick.bjoern.generator.builder;

import java.util.stream.Collectors;

import de.mehtrick.bjoern.parser.modell.BjoernStatement;

public class BjoernStatementParser {

	public static String parseStatement(BjoernStatement statement) {
		String parameters = statement.getParameters().stream().map(p -> String.format("\"%s\"", p))
				.collect(Collectors.joining(", "));
		return statement.getStatementWithoutParameters() + String.format("(%s)", parameters);
	}
}
