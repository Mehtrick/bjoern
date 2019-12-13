package de.mehtrick.bjoern.generator.builder;

import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.MethodSpec.Builder;
import de.mehtrick.bjoern.base.BjoernGeneratorConfig;
import de.mehtrick.bjoern.parser.modell.BjoernBackground;

import javax.lang.model.element.Modifier;

public class BjoernBackgroundTestBuilder {

	public static MethodSpec build(BjoernBackground background, BjoernGeneratorConfig.SupportedJunitVersion junitVersion) {

		Builder backgroundMethodBuilder = MethodSpec.methodBuilder("background").addAnnotation(junitVersion.getBeforeAnnotationClass())
				.addModifiers(Modifier.PUBLIC).addException(Exception.class);

		background.getGiven().stream()
				.forEach(given -> backgroundMethodBuilder.addStatement(BjoernStatementParser.parseStatement(given)));
		return backgroundMethodBuilder.build();
	}
}
