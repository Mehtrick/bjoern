package de.mehtrick.bjoern.generator.builder;

import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.MethodSpec.Builder;
import de.mehtrick.bjoern.base.BjoernGeneratorConfig;
import de.mehtrick.bjoern.parser.modell.Bjoern;
import de.mehtrick.bjoern.parser.modell.BjoernScenario;
import org.apache.commons.lang3.StringUtils;

import javax.lang.model.element.Modifier;
import java.util.List;
import java.util.stream.Collectors;

public class BjoernScenarioTestMethodBuilder {

	public static List<MethodSpec> build(Bjoern bjoern, BjoernGeneratorConfig.SupportedJunitVersion junitVersion) {
		return bjoern.getScenarios().stream().map(scenario -> parseJviroScenario(scenario, junitVersion))
				.collect(Collectors.toList());
	}

	private static MethodSpec parseJviroScenario(BjoernScenario scenario, BjoernGeneratorConfig.SupportedJunitVersion junitVersion) {
		Builder main = MethodSpec.methodBuilder(StringUtils.uncapitalize(scenario.getNameFormatted()))
				.addModifiers(Modifier.PUBLIC).addException(Exception.class).addJavadoc(scenario.getName());
		main.addAnnotation(junitVersion.getTestAnnotationClass());
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
