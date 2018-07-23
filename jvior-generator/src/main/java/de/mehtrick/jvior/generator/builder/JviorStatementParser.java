package de.mehtrick.jvior.generator.builder;

import java.util.stream.Collectors;

import de.mehtrick.jvior.parser.modell.JviorStatement;

public class JviorStatementParser {

	public static String parseStatement(JviorStatement statement) {
		String parameters = statement.getParameters().stream().map(p -> String.format("\"%s\"", p))
				.collect(Collectors.joining(", "));
		return statement.getStatementWithoutParameters() + String.format("(%s)", parameters);
	}
}
