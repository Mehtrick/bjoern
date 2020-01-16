package de.mehtrick.bjoern.generator.builder;

import com.squareup.javapoet.MethodSpec;
import de.mehtrick.bjoern.generator.junitsupport.SupportedJunitVersion;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.lang.model.element.Modifier;


class BjoernBackgroundTestBuilderTest extends AbstractBuilderTest {

    @Test
    public void successJunit4() {
        //when
        MethodSpec mappedBackground = BjoernBackgroundTestBuilder.build(bjoern.getBackground(), SupportedJunitVersion.junit4);

        //then
        Assertions.assertThat(mappedBackground).isNotNull();
        Assertions.assertThat(mappedBackground.annotations).hasSize(1);
        Assertions.assertThat(mappedBackground.modifiers).hasSize(1);
        Assertions.assertThat(mappedBackground.modifiers.iterator().next()).isEqualByComparingTo(Modifier.PUBLIC);
        Assertions.assertThat(mappedBackground.exceptions).hasSize(1);
        Assertions.assertThat(mappedBackground.exceptions.get(0).toString()).isEqualTo(Exception.class.getCanonicalName());
        Assertions.assertThat(mappedBackground.annotations.get(0).type.toString()).isEqualTo(Before.class.getCanonicalName());
        Assertions.assertThat(mappedBackground.code.toString()).isEqualToIgnoringWhitespace("given_EinTypDerWasTrinkenWill(); ");
    }

    @Test
    public void successJunit5() {
        //when
        MethodSpec mappedBackground = BjoernBackgroundTestBuilder.build(bjoern.getBackground(), SupportedJunitVersion.junit5);

        //then
        Assertions.assertThat(mappedBackground).isNotNull();
        Assertions.assertThat(mappedBackground.annotations).hasSize(1);
        Assertions.assertThat(mappedBackground.annotations.get(0).type.toString()).isEqualTo(BeforeEach.class.getCanonicalName());
    }

}
