package de.mehtrick.jvior.generator.builder;

import java.util.List;
import java.util.Set;

import javax.lang.model.element.Modifier;

import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

import de.mehtrick.jvior.parser.modell.Jvior;

public class JviorFeatureTestClassBuilder {

	public static TypeSpec build(Jvior jvior, List<MethodSpec> scenarios,
			Set<MethodSpec> abstractMethods) {
		return TypeSpec.classBuilder(jvior.getFeatureNameFormatted()).addModifiers(Modifier.PUBLIC, Modifier.ABSTRACT)
				.addMethods(scenarios).addMethods(abstractMethods).addJavadoc(jvior.getFeature()).build();
	}

}
