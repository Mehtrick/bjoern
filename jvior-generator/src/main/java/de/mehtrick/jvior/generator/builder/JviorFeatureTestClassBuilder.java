package de.mehtrick.jvior.generator.builder;

import java.util.List;
import java.util.Set;

import javax.lang.model.element.Modifier;

import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

import de.mehtrick.jvior.parser.modell.Jvior;

public class JviorFeatureTestClassBuilder {

	private static final String ABSTRACT_CLASS_PREFIX = "Abstract";

	public static TypeSpec build(Jvior jvior, List<MethodSpec> scenarios, Set<MethodSpec> abstractMethods) {
		return TypeSpec.classBuilder(ABSTRACT_CLASS_PREFIX + jvior.getFeatureNameFormatted())
				.addModifiers(Modifier.PUBLIC, Modifier.ABSTRACT).addMethods(scenarios).addMethods(abstractMethods)
				.addJavadoc(jvior.getFeature()).build();
	}

}
