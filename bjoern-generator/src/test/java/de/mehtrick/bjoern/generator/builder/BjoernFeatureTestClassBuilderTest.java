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
        TypeSpec mappedFeature = new BjoernFeatureTestClassBuilder(bjoernCodeGeneratorConfig).build(bjoern);
        //then
        Assertions.assertThat(mappedFeature).isNotNull();
        Assertions.assertThat(mappedFeature.name).isEqualTo("AbstractTestEinesKassenautomaten");
        Assertions.assertThat(mappedFeature.modifiers).hasSize(2);
        Iterator<Modifier> modifierIterator = mappedFeature.modifiers.iterator();
        Assertions.assertThat(modifierIterator.next()).isEqualTo(Modifier.PUBLIC);
        Assertions.assertThat(modifierIterator.next()).isEqualTo(Modifier.ABSTRACT);
        Assertions.assertThat(mappedFeature.superclass.toString()).isEqualTo(Object.class.getCanonicalName());

        //1 The Backgroundmethod
        //1 The given in the background
        //4 given abstract methods
        //1 when abstract method
        //2 then abstract method
        //2 scenarios
        Assertions.assertThat(mappedFeature.methodSpecs).hasSize(11);
    }

    @Test
    void testWithExtendedClass() {
        //given
        bjoernCodeGeneratorConfig.setExtendedTestclass("de.test.Abstractclass");
        //when
        TypeSpec mappedFeature = new BjoernFeatureTestClassBuilder(bjoernCodeGeneratorConfig).build(bjoern);
        //then
        Assertions.assertThat(mappedFeature).isNotNull();
        Assertions.assertThat(mappedFeature.name).isEqualTo("AbstractTestEinesKassenautomaten");
        Assertions.assertThat(mappedFeature.modifiers).hasSize(2);
        Iterator<Modifier> modifierIterator = mappedFeature.modifiers.iterator();
        Assertions.assertThat(modifierIterator.next()).isEqualTo(Modifier.PUBLIC);
        Assertions.assertThat(modifierIterator.next()).isEqualTo(Modifier.ABSTRACT);
        Assertions.assertThat(mappedFeature.superclass.toString()).isEqualTo("de.test.Abstractclass");

        //1 The Backgroundmethod
        //1 The given in the background
        //4 given abstract methods
        //1 when abstract method
        //2 then abstract method
        //2 scenarios
        Assertions.assertThat(mappedFeature.methodSpecs).hasSize(11);
    }

}
