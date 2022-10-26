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

/**
 * The feature contains the tests as well the abstract test methods used in the test
 */
public class BjoernFeatureTestClassBuilder extends BjoernGeneratorConfigProvided {


    public BjoernFeatureTestClassBuilder(BjoernGeneratorConfig bjoernGeneratorConfig) {
        super(bjoernGeneratorConfig);
    }

    /**
     * Creates the test classes containing the test methods and the background. Also it generates an Interface containing the test methods, which need to be implemented by the developer
     *
     * @param bjoern
     * @return
     */
    public BjoernClassesToBuild build(Bjoern bjoern) {
        Builder featureClassBuilder = TypeSpec.classBuilder(StringUtils.capitalize(Modifier.ABSTRACT + bjoern.getFeatureNameFormatted()))
                .addModifiers(Modifier.PUBLIC, Modifier.ABSTRACT);
        TypeSpec bjoernMehtodInterface = createMethodInterface(bjoern, featureClassBuilder);
        addScenarios(bjoern, featureClassBuilder);
        addBackground(bjoern, featureClassBuilder);
        addJavaDoc(bjoern, featureClassBuilder);
        addExtendedClass(featureClassBuilder);
        return new BjoernClassesToBuild(featureClassBuilder.build(), bjoernMehtodInterface);
    }

    private void addJavaDoc(Bjoern bjoern, Builder featureClassBuilder) {
        featureClassBuilder.addJavadoc(bjoern.getFeature());
    }

    private TypeSpec createMethodInterface(Bjoern bjoern, Builder featureClassBuilder) {
        TypeSpec methodInterface = BjoerTestMethodInterfaceBuilder.build(bjoern);
        ClassName methodInterfaceClass = ClassName.get(bjoernGeneratorConfig.getPckg(), methodInterface.name);
        featureClassBuilder.addSuperinterface(methodInterfaceClass);
        return methodInterface;
    }

    private void addScenarios(Bjoern bjoern, Builder featureClassBuilder) {
        List<MethodSpec> scenarios = BjoernScenarioTestMethodBuilder.build(bjoern, bjoernGeneratorConfig.getJunitVersion());
        featureClassBuilder.addMethods(scenarios);
    }

    private void addBackground(Bjoern bjoern, Builder featureClassBuilder) {
        if (bjoern.getBackground() != null) {
            MethodSpec background = BjoernBackgroundTestBuilder.build(bjoern.getBackground(), bjoernGeneratorConfig.getJunitVersion());
            featureClassBuilder.addMethod(background);
        }
    }

    private void addExtendedClass(Builder featureClassBuilder) {
        if (StringUtils.isNotBlank(bjoernGeneratorConfig.getExtendedTestclass())) {
            String className = StringUtils.substringAfterLast(bjoernGeneratorConfig.getExtendedTestclass(), ".");
            String packageName = StringUtils.substringBeforeLast(bjoernGeneratorConfig.getExtendedTestclass(), ".");

            ClassName superClass = ClassName.get(packageName, className);

            featureClassBuilder.superclass(superClass);
        }
    }

}
