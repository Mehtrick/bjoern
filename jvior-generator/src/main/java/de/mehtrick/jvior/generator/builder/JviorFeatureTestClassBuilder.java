package de.mehtrick.jvior.generator.builder;

import java.util.List;
import java.util.Set;

import javax.lang.model.element.Modifier;

import org.apache.commons.lang3.StringUtils;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;
import com.squareup.javapoet.TypeSpec.Builder;

import de.mehtrick.jvior.base.JviorGeneratorConfig;
import de.mehtrick.jvior.parser.modell.Jvior;

public class JviorFeatureTestClassBuilder {

	private static final String ABSTRACT_CLASS_PREFIX = "Abstract";

	public static TypeSpec build(Jvior jvior, List<MethodSpec> scenarios, Set<MethodSpec> abstractMethods) {

		Builder featureClassBuilder = TypeSpec.classBuilder(ABSTRACT_CLASS_PREFIX + jvior.getFeatureNameFormatted())
				.addModifiers(Modifier.PUBLIC, Modifier.ABSTRACT);
		if (jvior.getBackground() != null) {
			MethodSpec background = JviorBackgroundTestBuilder.build(jvior.getBackground());
			featureClassBuilder.addMethod(background);
		}

		featureClassBuilder.addMethods(scenarios).addMethods(abstractMethods).addJavadoc(jvior.getFeature());

		if (StringUtils.isNotBlank(JviorGeneratorConfig.getExtendedTestclass())) {
			String className = StringUtils.substringAfterLast(JviorGeneratorConfig.getExtendedTestclass(), ".");
			String packageName = StringUtils.substringBeforeLast(JviorGeneratorConfig.getExtendedTestclass(), ".");

			ClassName superClass = ClassName.get(packageName, className);

			featureClassBuilder.superclass(superClass);
		}
		return featureClassBuilder.build();
	}

}
