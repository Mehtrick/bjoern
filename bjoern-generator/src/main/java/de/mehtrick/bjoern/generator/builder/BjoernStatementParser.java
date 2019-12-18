package de.mehtrick.bjoern.generator.builder;

import de.mehtrick.bjoern.parser.modell.BjoernStatement;

import java.util.stream.Collectors;

public class BjoernStatementParser {

    /**
     * Creates the java call of the {@link de.mehtrick.bjoern.parser.modell.BjoernStatement} inside a test scenario by extracting the parameters of the statement.
     * e.g.
     * Given a cat with name "meow" => given_ACatWithName("meow");
     *
     * @param statement
     * @return
     */
    public static String createMethodCallOutOfStatemet(BjoernStatement statement) {
        String parameters = statement.getParameters().stream().map(p -> String.format("\"%s\"", p))
                .collect(Collectors.joining(", "));
        return statement.getStatementWithoutParameters() + String.format("(%s)", parameters);
    }
}
