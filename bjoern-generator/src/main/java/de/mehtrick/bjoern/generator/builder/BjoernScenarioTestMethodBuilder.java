package de.mehtrick.bjoern.generator.builder;

import java.util.List;
import java.util.stream.Collectors;

import javax.lang.model.element.Modifier;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.MethodSpec.Builder;

import de.mehtrick.bjoern.parser.modell.Bjoern;
import de.mehtrick.bjoern.parser.modell.BjoernScenario;

public class BjoernScenarioTestMethodBuilder {

	public static List<MethodSpec> build(Bjoern bjoern) {
		return bjoern.getScenarios().stream().map(BjoernScenarioTestMethodBuilder::parseJviroScenario)
				.collect(Collectors.toList());
	}

	private static MethodSpec parseJviroScenario(BjoernScenario scenario) {
		Builder main = MethodSpec.methodBuilder(StringUtils.uncapitalize(scenario.getNameFormatted())).addAnnotation(Test.class)
				.addModifiers(Modifier.PUBLIC).addException(Exception.class).addJavadoc(scenario.getName());
		if (scenario.getGiven() != null) {
			scenario.getGiven().stream()
					.forEach(given -> main.addStatement(BjoernStatementParser.parseStatement(given)));
		}
		if (scenario.getWhen() != null) {
			scenario.getWhen().stream().forEach(when -> main.addStatement(BjoernStatementParser.parseStatement(when)));
		}
		if (scenario.getThen() != null) {
			scenario.getThen().stream().forEach(then -> main.addStatement(BjoernStatementParser.parseStatement(then)));
		}

		return main.build();
	}

}
