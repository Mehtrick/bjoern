package de.mehtrick.bjoern.generator.builder;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;
import com.squareup.javapoet.TypeSpec.Builder;
import de.mehtrick.bjoern.generator.BjoernCodeGeneratorConfig;
import de.mehtrick.bjoern.generator.junitsupport.JunitGenerationStrategy;
import de.mehtrick.bjoern.parser.modell.Bjoern;
import org.apache.commons.lang3.StringUtils;

import javax.lang.model.element.Modifier;
import java.util.Set;

/**
 * The feature contains the tests as well the abstract test methods used in the test
 */
public class BjoernFeatureTestClassBuilder {

    BjoernCodeGeneratorConfig bjoernCodeGeneratorConfig;

    public BjoernFeatureTestClassBuilder(BjoernCodeGeneratorConfig bjoernGeneratorConfig) {
        bjoernCodeGeneratorConfig = bjoernGeneratorConfig;
    }

    /**
     * Creates the test class containing the test methods, backgrounds and the abstract test methods, which need to be implemented by the developer
     *
     * @param bjoern
     * @return
     */
    public TypeSpec build(Bjoern bjoern) {
        Builder featureClassBuilder = TypeSpec.classBuilder(StringUtils.capitalize(Modifier.ABSTRACT + bjoern.getFeatureNameFormatted()))
                .addModifiers(Modifier.PUBLIC, Modifier.ABSTRACT);
        addAbstractMethods(bjoern, featureClassBuilder);
        addScenarios(bjoern, featureClassBuilder);
        addBackground(bjoern, featureClassBuilder);
        addJavaDoc(bjoern, featureClassBuilder);
        addExtendedClass(featureClassBuilder);
        return featureClassBuilder.build();
    }

    private void addJavaDoc(Bjoern bjoern, Builder featureClassBuilder) {
        featureClassBuilder.addJavadoc(bjoern.getFeature());
    }

    private void addAbstractMethods(Bjoern bjoern, Builder featureClassBuilder) {
        Set<MethodSpec> abstractMethods = BjoernAbstractTestMethodBuilder.build(bjoern.getBackground(),
                bjoern.getScenarios());
        featureClassBuilder.addMethods(abstractMethods);
    }

    private void addScenarios(Bjoern bjoern, Builder featureClassBuilder) {
        JunitGenerationStrategy junitStrategy = bjoernCodeGeneratorConfig.getJunitVersion().getJunitStrategy();
        junitStrategy.generateScenarios(bjoern, featureClassBuilder);
    }

    private void addBackground(Bjoern bjoern, Builder featureClassBuilder) {
        if (bjoern.getBackground() != null) {
            MethodSpec background = BjoernBackgroundTestBuilder.build(bjoern.getBackground(), bjoernCodeGeneratorConfig.getJunitVersion());
            featureClassBuilder.addMethod(background);
        }
    }

    private void addExtendedClass(Builder featureClassBuilder) {
        if (StringUtils.isNotBlank(bjoernCodeGeneratorConfig.getExtendedTestclass())) {
            String className = StringUtils.substringAfterLast(bjoernCodeGeneratorConfig.getExtendedTestclass(), ".");
            String packageName = StringUtils.substringBeforeLast(bjoernCodeGeneratorConfig.getExtendedTestclass(), ".");

            ClassName superClass = ClassName.get(packageName, className);

            featureClassBuilder.superclass(superClass);
        }
    }

}
