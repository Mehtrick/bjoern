package de.mehtrick.bjoern.generator.builder;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.lang.model.element.Modifier;

import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterSpec;

import de.mehtrick.bjoern.parser.modell.BjoernBackground;
import de.mehtrick.bjoern.parser.modell.BjoernScenario;
import de.mehtrick.bjoern.parser.modell.BjoernStatement;

public class BjoernAbstractTestMethodBuilder {

	private static final String PARAM_NAME = "param";

	public static Set<MethodSpec> build(BjoernBackground bjoernBackground, List<BjoernScenario> list) {
		Set<BjoernStatement> statements = new HashSet<>();

		if (bjoernBackground != null) {
			statements.addAll(bjoernBackground.getGiven());
		}

		for (BjoernScenario scenario : list) {

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

		return statements.stream().map(BjoernAbstractTestMethodBuilder::parseToMethodSpec).collect(Collectors.toSet());
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
