package de.mehtrick.bjoern.generator.builder;

import javax.lang.model.element.Modifier;

import org.junit.Before;

import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.MethodSpec.Builder;

import de.mehtrick.bjoern.parser.modell.BjoernBackground;

public class BjoernBackgroundTestBuilder {

	public static MethodSpec build(BjoernBackground background) {

		Builder backgroundMethodBuilder = MethodSpec.methodBuilder("background").addAnnotation(Before.class)
				.addModifiers(Modifier.PUBLIC).addException(Exception.class);

		background.getGiven().stream()
				.forEach(given -> backgroundMethodBuilder.addStatement(BjoernStatementParser.parseStatement(given)));
		return backgroundMethodBuilder.build();
	}
}
