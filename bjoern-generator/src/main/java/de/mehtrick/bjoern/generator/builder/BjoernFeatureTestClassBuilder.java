package de.mehtrick.bjoern.generator.builder;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;
import com.squareup.javapoet.TypeSpec.Builder;
import de.mehtrick.bjoern.base.BjoernGeneratorConfig;
import de.mehtrick.bjoern.base.BjoernGeneratorConfigProvided;
import de.mehtrick.bjoern.parser.modell.Bjoern;
import org.apache.commons.lang3.StringUtils;

import javax.lang.model.element.Modifier;
import java.util.List;
import java.util.Set;

public class BjoernFeatureTestClassBuilder extends BjoernGeneratorConfigProvided{

	private final String ABSTRACT_CLASS_PREFIX = "Abstract";

	public BjoernFeatureTestClassBuilder(BjoernGeneratorConfig bjoernGeneratorConfig) {
		super(bjoernGeneratorConfig);
	}

	public TypeSpec build(Bjoern bjoern, List<MethodSpec> scenarios, Set<MethodSpec> abstractMethods) {

		Builder featureClassBuilder = TypeSpec.classBuilder(ABSTRACT_CLASS_PREFIX + bjoern.getFeatureNameFormatted())
				.addModifiers(Modifier.PUBLIC, Modifier.ABSTRACT);
		if (bjoern.getBackground() != null) {
			MethodSpec background = BjoernBackgroundTestBuilder.build(bjoern.getBackground(), bjoernGeneratorConfig.getJunitVersion());
			featureClassBuilder.addMethod(background);
		}

		featureClassBuilder.addMethods(scenarios).addMethods(abstractMethods).addJavadoc(bjoern.getFeature());

		if (StringUtils.isNotBlank(bjoernGeneratorConfig.getExtendedTestclass())) {
			String className = StringUtils.substringAfterLast(bjoernGeneratorConfig.getExtendedTestclass(), ".");
			String packageName = StringUtils.substringBeforeLast(bjoernGeneratorConfig.getExtendedTestclass(), ".");

			ClassName superClass = ClassName.get(packageName, className);

			featureClassBuilder.superclass(superClass);
		}
		return featureClassBuilder.build();
	}

}
