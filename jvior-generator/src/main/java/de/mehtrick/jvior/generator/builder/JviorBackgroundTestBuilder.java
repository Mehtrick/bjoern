package de.mehtrick.jvior.generator.builder;

import javax.lang.model.element.Modifier;

import org.junit.Before;

import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.MethodSpec.Builder;

import de.mehtrick.jvior.parser.modell.JviorBackground;

public class JviorBackgroundTestBuilder {

	public static MethodSpec build(JviorBackground background) {

		Builder backgroundMethodBuilder = MethodSpec.methodBuilder("background").addAnnotation(Before.class)
				.addModifiers(Modifier.PUBLIC).addException(Exception.class);

		background.getGiven().stream()
				.forEach(given -> backgroundMethodBuilder.addStatement(JviorStatementParser.parseStatement(given)));
		return backgroundMethodBuilder.build();
	}
}
