package de.mehtrick.bjoern.generator.builder;

import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.MethodSpec.Builder;
import de.mehtrick.bjoern.base.SupportedJunitVersion;
import de.mehtrick.bjoern.parser.modell.Bjoern;
import de.mehtrick.bjoern.parser.modell.BjoernScenario;
import org.apache.commons.lang3.StringUtils;

import javax.lang.model.element.Modifier;
import java.util.List;
import java.util.stream.Collectors;

public class BjoernScenarioTestMethodBuilder {

    public static List<MethodSpec> build(Bjoern bjoern, SupportedJunitVersion junitVersion) {
        return bjoern.getScenarios().stream().map(scenario -> parseBjoernScenario(scenario, junitVersion))
                .collect(Collectors.toList());
    }

    /**
     * Generates a single Test method
     *
     * @param scenario     a single scenario of a bjoern file
     * @param junitVersion the used junit version determines the import of the @Test annotation
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
    private static MethodSpec parseBjoernScenario(BjoernScenario scenario, SupportedJunitVersion junitVersion) {
        Builder main = MethodSpec.methodBuilder(StringUtils.uncapitalize(scenario.getNameFormatted()))
                .addModifiers(Modifier.PUBLIC).addException(Exception.class).addJavadoc(scenario.getName());
        main.addAnnotation(junitVersion.getTestAnnotationClass());
        junitVersion.addExtraAnnotations(main,scenario);
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

}
