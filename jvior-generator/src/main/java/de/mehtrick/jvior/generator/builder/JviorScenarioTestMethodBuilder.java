package de.mehtrick.jvior.generator.builder;

import java.util.List;
import java.util.stream.Collectors;

import javax.lang.model.element.Modifier;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.MethodSpec.Builder;

import de.mehtrick.jvior.parser.modell.Jvior;
import de.mehtrick.jvior.parser.modell.JviorScenario;

public class JviorScenarioTestMethodBuilder {

	public static List<MethodSpec> build(Jvior jvior) {
		return jvior.getScenarios().stream().map(JviorScenarioTestMethodBuilder::parseJviroScenario)
				.collect(Collectors.toList());
	}

	private static MethodSpec parseJviroScenario(JviorScenario scenario) {
		Builder main = MethodSpec.methodBuilder(StringUtils.uncapitalize(scenario.getNameFormatted())).addAnnotation(Test.class)
				.addModifiers(Modifier.PUBLIC).addException(Exception.class).addJavadoc(scenario.getName());
		if (scenario.getGiven() != null) {
			scenario.getGiven().stream()
					.forEach(given -> main.addStatement(JviorStatementParser.parseStatement(given)));
		}
		if (scenario.getWhen() != null) {
			scenario.getWhen().stream().forEach(when -> main.addStatement(JviorStatementParser.parseStatement(when)));
		}
		if (scenario.getThen() != null) {
			scenario.getThen().stream().forEach(then -> main.addStatement(JviorStatementParser.parseStatement(then)));
		}

		return main.build();
	}

}
