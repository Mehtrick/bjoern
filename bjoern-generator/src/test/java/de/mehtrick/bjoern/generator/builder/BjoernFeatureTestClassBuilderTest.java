package de.mehtrick.bjoern.generator.builder;

import com.squareup.javapoet.TypeSpec;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.lang.model.element.Modifier;
import java.util.Iterator;


class BjoernFeatureTestClassBuilderTest extends AbstractBuilderTest {

    @Test
    void success() {
        //when
        TypeSpec mappedFeature = new BjoernFeatureTestClassBuilder(bjoernCodeGeneratorConfig).build(bjoern).getFeatureClass();
        TypeSpec mappedInterface = new BjoernFeatureTestClassBuilder(bjoernCodeGeneratorConfig).build(bjoern).getFeatureInterface();
        //then
        Assertions.assertThat(mappedFeature).isNotNull();
        Assertions.assertThat(mappedFeature.name).isEqualTo("AbstractTestEinesKassenautomaten");
        Assertions.assertThat(mappedFeature.modifiers).hasSize(2);
        Iterator<Modifier> modifierIterator = mappedFeature.modifiers.iterator();
        Assertions.assertThat(modifierIterator.next()).isEqualTo(Modifier.PUBLIC);
        Assertions.assertThat(modifierIterator.next()).isEqualTo(Modifier.ABSTRACT);
        Assertions.assertThat(mappedFeature.superclass.toString()).isEqualTo(Object.class.getCanonicalName());


        Assertions.assertThat(mappedInterface).isNotNull();
        Assertions.assertThat(mappedInterface.name).isEqualTo("TestEinesKassenautomatenInterface");
        Assertions.assertThat(mappedInterface.kind).isEqualTo(TypeSpec.Kind.INTERFACE);
        //1 The Backgroundmethod
        //2 scenarios
        Assertions.assertThat(mappedFeature.methodSpecs).hasSize(3);
        //1 The given in the background
        //4 given abstract methods
        //1 when abstract method
        //2 then abstract method
        Assertions.assertThat(mappedInterface.methodSpecs).hasSize(8);
    }

    @Test
    void testWithExtendedClass() {
        //given
        bjoernCodeGeneratorConfig.setExtendedTestclass("de.test.Abstractclass");
        bjoernCodeGeneratorConfig.setPckg("de.test");
        //when
        TypeSpec mappedFeature = new BjoernFeatureTestClassBuilder(bjoernCodeGeneratorConfig).build(bjoern).getFeatureClass();
        TypeSpec mappedInterface = new BjoernFeatureTestClassBuilder(bjoernCodeGeneratorConfig).build(bjoern).getFeatureInterface();
        //then
        Assertions.assertThat(mappedFeature).isNotNull();
        Assertions.assertThat(mappedFeature.name).isEqualTo("AbstractTestEinesKassenautomaten");
        Assertions.assertThat(mappedFeature.modifiers).hasSize(2);
        Iterator<Modifier> modifierIterator = mappedFeature.modifiers.iterator();
        Assertions.assertThat(modifierIterator.next()).isEqualTo(Modifier.PUBLIC);
        Assertions.assertThat(modifierIterator.next()).isEqualTo(Modifier.ABSTRACT);
        Assertions.assertThat(mappedFeature.superclass.toString()).isEqualTo("de.test.Abstractclass");

        Assertions.assertThat(mappedInterface).isNotNull();
        Assertions.assertThat(mappedInterface.name).isEqualTo("TestEinesKassenautomatenInterface");
        Assertions.assertThat(mappedInterface.kind).isEqualTo(TypeSpec.Kind.INTERFACE);

        //1 The Backgroundmethod
        //2 scenarios
        Assertions.assertThat(mappedInterface.methodSpecs).hasSize(8);
        //1 The given in the background
        //4 given abstract methods
        //1 when abstract method
        //2 then abstract method
        Assertions.assertThat(mappedFeature.methodSpecs).hasSize(3);
    }

}
