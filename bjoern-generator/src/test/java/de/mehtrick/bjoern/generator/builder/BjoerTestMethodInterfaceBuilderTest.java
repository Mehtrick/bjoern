package de.mehtrick.bjoern.generator.builder;

import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Collectors;

class BjoerTestMethodInterfaceBuilderTest extends AbstractBuilderTest {

    @Test
    void testAbstractMethodsGenerated() {
        //given
        bjoern = getBjoern("src/test/resources/abstract-methods.zgr");
        //when
        TypeSpec bjoernMethodInterface = BjoerTestMethodInterfaceBuilder.build(bjoern);
        List<MethodSpec> generatedAbstractMethods = bjoernMethodInterface.methodSpecs;
        //then
        List<String> abstractMethodsString = generatedAbstractMethods.stream().map(method -> method.toString().trim()).collect(Collectors.toList());
        Assertions.assertThat(abstractMethodsString).hasSize(8);
        Assertions.assertThat(abstractMethodsString).contains("public abstract void given_EinAutomat() throws java.lang.Exception;");
        Assertions.assertThat(abstractMethodsString).contains("public abstract void given_EinAutomat(java.lang.String param1) throws java.lang.Exception;");


    }

}
