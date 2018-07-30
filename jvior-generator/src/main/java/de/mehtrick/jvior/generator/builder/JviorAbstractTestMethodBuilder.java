package de.mehtrick.jvior.generator.builder;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.lang.model.element.Modifier;

import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterSpec;

import de.mehtrick.jvior.parser.modell.JviorBackground;
import de.mehtrick.jvior.parser.modell.JviorScenario;
import de.mehtrick.jvior.parser.modell.JviorStatement;

public class JviorAbstractTestMethodBuilder {

	private static final String PARAM_NAME = "param";

	public static Set<MethodSpec> build(JviorBackground jviorBackground, List<JviorScenario> list) {
		Set<JviorStatement> statements = new HashSet<>();

		if (jviorBackground != null) {
			statements.addAll(jviorBackground.getGiven());
		}

		for (JviorScenario scenario : list) {

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

		return statements.stream().map(JviorAbstractTestMethodBuilder::parseToMethodSpec).collect(Collectors.toSet());
	}

	private static MethodSpec parseToMethodSpec(JviorStatement statement) {

		List<ParameterSpec> parameterSpecs = new ArrayList<>();

		for (int i = 0; i < statement.getParameters().size(); i++) {
			ParameterSpec parameter = ParameterSpec.builder(String.class, PARAM_NAME + (i + 1)).build();
			parameterSpecs.add(parameter);
		}

		return MethodSpec.methodBuilder(statement.getStatementWithoutParameters())
				.addModifiers(Modifier.PUBLIC, Modifier.ABSTRACT).addParameters(parameterSpecs).build();

	}

}
