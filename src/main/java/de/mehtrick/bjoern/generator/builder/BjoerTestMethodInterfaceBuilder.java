package de.mehtrick.bjoern.generator.builder;

import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterSpec;
import com.squareup.javapoet.TypeSpec;
import de.mehtrick.bjoern.parser.modell.Bjoern;
import de.mehtrick.bjoern.parser.modell.BjoernScenario;
import de.mehtrick.bjoern.parser.modell.BjoernStatement;
import org.apache.commons.lang3.StringUtils;

import javax.lang.model.element.Modifier;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class BjoerTestMethodInterfaceBuilder {

    private static final String PARAM_NAME = "param";

    /**
     * Creates an Interface of the implementing methods
     *
     * @param bjoern The Bjoern file
     * @return e.g.
     * <p>
     * <code>
     * public abstract void given_ThereAreBottlesOfBeer(String param1);
     * public abstract void given_ABar();
     * public abstract void when_FooWantsToDrinkBottleOfBeer(String param1);
     * public abstract void given_ThereAreBottlesOfWine(String param1);
     * public abstract void given_AFoo();
     * public abstract void then_FooSays(String param1);
     * </code>
     */
    public static TypeSpec build(Bjoern bjoern) {
        TypeSpec.Builder bjoernMethodsInterface = TypeSpec.interfaceBuilder(StringUtils.capitalize(bjoern.getFeatureNameFormatted() + "Interface"))
                .addModifiers(Modifier.PUBLIC);
        Set<BjoernStatement> statements = new HashSet<>();

        if (bjoern.getBackground() != null) {
            statements.addAll(bjoern.getBackground().getGiven());
        }

        for (BjoernScenario scenario : bjoern.getScenarios()) {

            if (scenario.getGiven() != null) {
                statements.addAll(scenario.getGiven());
            }
            if (scenario.getWhen() != null) {
                statements.addAll(scenario.getWhen());
            }
            if (scenario.getThen() != null) {
                statements.addAll(scenario.getThen());
            }

        }
        Set<MethodSpec> testMethods = statements.stream().map(BjoerTestMethodInterfaceBuilder::parseToMethodSpec).collect(Collectors.toSet());
        bjoernMethodsInterface.addMethods(testMethods);
        return bjoernMethodsInterface.build();
    }

    private static MethodSpec parseToMethodSpec(BjoernStatement statement) {

        List<ParameterSpec> parameterSpecs = new ArrayList<>();

        for (int i = 0; i < statement.getParameters().size(); i++) {
            ParameterSpec parameter = ParameterSpec.builder(String.class, PARAM_NAME + (i + 1)).build();
            parameterSpecs.add(parameter);
        }

        return MethodSpec.methodBuilder(statement.getStatementWithoutParameters())
                .addModifiers(Modifier.PUBLIC, Modifier.ABSTRACT).addParameters(parameterSpecs).addException(Exception.class).build();

    }

}
