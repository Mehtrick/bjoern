package de.mehtrick.bjoern.generator.builder;

import com.squareup.javapoet.MethodSpec;
import de.mehtrick.bjoern.base.SupportedJunitVersion;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.lang.model.element.Modifier;
import java.util.ArrayList;
import java.util.List;

class BjoernScenarioTestMethodBuilderTest extends AbstractBuilderTest {


    @Test
    void testNoScenariosFound() {
        //given
        bjoern.setScenarios(new ArrayList<>());

        //when
        List<MethodSpec> buildBjoernScenarios = BjoernScenarioTestMethodBuilder.build(bjoern, SupportedJunitVersion.junit4);

        //then
        Assertions.assertThat(buildBjoernScenarios).hasSize(0);
    }

    @Test
    void testScenariosMappedJUnit4() {
        //when
        List<MethodSpec> buildBjoernScenarios = BjoernScenarioTestMethodBuilder.build(bjoern, SupportedJunitVersion.junit4);

        //then
        Assertions.assertThat(buildBjoernScenarios).hasSize(2);
        MethodSpec mappedScenario = buildBjoernScenarios.get(0);

        Assertions.assertThat(mappedScenario.name).isEqualTo("getraenkNichtVorhanden");
        Assertions.assertThat(mappedScenario.annotations).hasSize(1);
        Assertions.assertThat(mappedScenario.annotations.get(0).type.toString()).isEqualTo(org.junit.Test.class.getCanonicalName());
        Assertions.assertThat(mappedScenario.modifiers).hasSize(1);
        Assertions.assertThat(mappedScenario.modifiers.iterator().next()).isEqualByComparingTo(Modifier.PUBLIC);
        Assertions.assertThat(mappedScenario.exceptions).hasSize(1);
        Assertions.assertThat(mappedScenario.exceptions.get(0).toString()).isEqualTo(Exception.class.getCanonicalName());
        Assertions.assertThat(mappedScenario.code.toString()).isEqualToIgnoringWhitespace("given_EinAutomat();\n" +
                "given_MitFlaschenCola(\"2\");\n" +
                "given_MitFlaschenSprite(\"0\");\n" +
                "when_KaufeSprite(\"1\");\n" +
                "then_AutomatSagt(\"alle\");\n" +
                "then_EsExistierenColaImAutomaten(\"2\");");
    }

    @Test
    void testScenariosMappedJUnit5() {
        //when
        List<MethodSpec> buildBjoernScenarios = BjoernScenarioTestMethodBuilder.build(bjoern, SupportedJunitVersion.junit5);

        //then
        MethodSpec mappedScenario = buildBjoernScenarios.get(0);

        Assertions.assertThat(mappedScenario.annotations).hasSize(2);
        Assertions.assertThat(mappedScenario.annotations.get(0).type.toString()).isEqualTo(Test.class.getCanonicalName());
    }
}
