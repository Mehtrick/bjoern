package de.mehtrick.bjoern.generator.junitsupport;

import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;
import de.mehtrick.bjoern.generator.builder.BjoernStatementParser;
import de.mehtrick.bjoern.parser.modell.Bjoern;
import de.mehtrick.bjoern.parser.modell.BjoernScenario;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

import javax.lang.model.element.Modifier;
import java.util.List;
import java.util.stream.Collectors;

public class Junit4GenerationStrategy implements JunitGenerationStrategy {

    public static List<MethodSpec> build(Bjoern bjoern) {
        return bjoern.getScenarios().stream().map(scenario -> parseBjoernScenario(scenario))
                .collect(Collectors.toList());
    }

    /**
     * Generates a single Test method
     *
     * @param scenario a single scenario of a bjoern file
     * @return A method spec wich contains the test flow. The generated java code would look like this
     * <p>
     * /* @Test
     * public void FooIsNotHappy() {
     * given_ThereAreBottlesOfWine("2");
     * given_ThereAreBottlesOfBeer("0");
     * when_FooWantsToDrinkBottleOfBeer("1");
     * then_FooSays("why is my beer empty");
     * }
     */
    private static MethodSpec parseBjoernScenario(BjoernScenario scenario) {
        MethodSpec.Builder main = MethodSpec.methodBuilder(StringUtils.uncapitalize(scenario.getNameFormatted()))
                .addModifiers(Modifier.PUBLIC).addException(Exception.class).addJavadoc(scenario.getName());
        main.addAnnotation(Test.class);
        if (scenario.getGiven() != null) {
            scenario.getGiven()
                    .forEach(given -> main.addStatement(BjoernStatementParser.createMethodCallOutOfStatemet(given)));
        }
        if (scenario.getWhen() != null) {
            scenario.getWhen().forEach(when -> main.addStatement(BjoernStatementParser.createMethodCallOutOfStatemet(when)));
        }
        if (scenario.getThen() != null) {
            scenario.getThen().forEach(then -> main.addStatement(BjoernStatementParser.createMethodCallOutOfStatemet(then)));
        }
        return main.build();
    }

    @Override
    public void generateScenarios(Bjoern bjoern, TypeSpec.Builder featureClassBuilder) {
        List<MethodSpec> scenarios = build(bjoern);
        featureClassBuilder.addMethods(scenarios);
    }
}
