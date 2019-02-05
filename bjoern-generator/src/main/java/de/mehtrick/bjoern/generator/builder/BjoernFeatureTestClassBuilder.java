package de.mehtrick.bjoern.generator.builder;

import java.util.List;
import java.util.Set;

import javax.lang.model.element.Modifier;

import org.apache.commons.lang3.StringUtils;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;
import com.squareup.javapoet.TypeSpec.Builder;

import de.mehtrick.bjoern.base.BjoernGeneratorConfig;
import de.mehtrick.bjoern.parser.modell.Bjoern;

public class BjoernFeatureTestClassBuilder {

	private static final String ABSTRACT_CLASS_PREFIX = "Abstract";

	public static TypeSpec build(Bjoern bjoern, List<MethodSpec> scenarios, Set<MethodSpec> abstractMethods) {

		Builder featureClassBuilder = TypeSpec.classBuilder(ABSTRACT_CLASS_PREFIX + bjoern.getFeatureNameFormatted())
				.addModifiers(Modifier.PUBLIC, Modifier.ABSTRACT);
		if (bjoern.getBackground() != null) {
			MethodSpec background = BjoernBackgroundTestBuilder.build(bjoern.getBackground());
			featureClassBuilder.addMethod(background);
		}

		featureClassBuilder.addMethods(scenarios).addMethods(abstractMethods).addJavadoc(bjoern.getFeature());

		if (StringUtils.isNotBlank(BjoernGeneratorConfig.getExtendedTestclass())) {
			String className = StringUtils.substringAfterLast(BjoernGeneratorConfig.getExtendedTestclass(), ".");
			String packageName = StringUtils.substringBeforeLast(BjoernGeneratorConfig.getExtendedTestclass(), ".");

			ClassName superClass = ClassName.get(packageName, className);

			featureClassBuilder.superclass(superClass);
		}
		return featureClassBuilder.build();
	}

}
