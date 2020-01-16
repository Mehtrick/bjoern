package de.mehtrick.bjoern.generator.builder;

import com.squareup.javapoet.TypeSpec;
import de.mehtrick.bjoern.generator.junitsupport.Junit5GenerationStrategy;
import org.apache.commons.lang3.StringUtils;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.lang.model.element.Modifier;

public class BjoernScenarioJunit5TestMethodBuilderTest extends AbstractBuilderTest {

    @Test
    void testScenariosMappedJUnit5Complete() {
        //given
        TypeSpec.Builder featureClassBuilder = TypeSpec.classBuilder(StringUtils.capitalize(Modifier.ABSTRACT + bjoern.getFeatureNameFormatted()))
                .addModifiers(Modifier.PUBLIC, Modifier.ABSTRACT);
        //when
        new Junit5GenerationStrategy().generateScenarios(bjoern, featureClassBuilder);
        TypeSpec build = featureClassBuilder.build();
        //then
        System.out.println(build);

        String output = build.toString();

        Assertions.assertThat(output).contains("@org.junit.jupiter.api.DisplayName(\"Feature: Test eines KassenAutomaten\")");
        Assertions.assertThat(output).contains("@org.junit.jupiter.api.DisplayName(\"Feature: Test eines KassenAutomaten\")\n" +
                "public abstract class AbstractTestEinesKassenautomaten {\n" +
                "  @org.junit.jupiter.api.Nested\n" +
                "  @org.junit.jupiter.api.DisplayName(\"Scenario: Getraenk nicht vorhanden\")\n" +
                "  @org.junit.jupiter.api.TestMethodOrder(org.junit.jupiter.api.MethodOrderer.OrderAnnotation.class)\n" +
                "  class GetraenkNichtVorhanden {\n" +
                "    @org.junit.jupiter.api.Test\n" +
                "    @org.junit.jupiter.api.DisplayName(\"Given: Ein Automat\")\n" +
                "    @org.junit.jupiter.api.Order(1)\n" +
                "    void given_EinAutomatTest() throws java.lang.Exception {\n" +
                "      given_EinAutomat();\n" +
                "    }\n" +
                "\n" +
                "    @org.junit.jupiter.api.Test\n" +
                "    @org.junit.jupiter.api.DisplayName(\"Given: Mit 2 Flaschen Cola\")\n" +
                "    @org.junit.jupiter.api.Order(2)\n" +
                "    void given_MitFlaschenColaTest() throws java.lang.Exception {\n" +
                "      given_MitFlaschenCola(\"2\");\n" +
                "    }\n" +
                "\n" +
                "    @org.junit.jupiter.api.Test\n" +
                "    @org.junit.jupiter.api.DisplayName(\"Given: Mit 0 Flaschen Sprite\")\n" +
                "    @org.junit.jupiter.api.Order(3)\n" +
                "    void given_MitFlaschenSpriteTest() throws java.lang.Exception {\n" +
                "      given_MitFlaschenSprite(\"0\");\n" +
                "    }\n" +
                "\n" +
                "    @org.junit.jupiter.api.Test\n" +
                "    @org.junit.jupiter.api.DisplayName(\"When: Kaufe 1 Sprite\")\n" +
                "    @org.junit.jupiter.api.Order(4)\n" +
                "    void when_KaufeSpriteTest() throws java.lang.Exception {\n" +
                "      when_KaufeSprite(\"1\");\n" +
                "    }\n" +
                "\n" +
                "    @org.junit.jupiter.api.Test\n" +
                "    @org.junit.jupiter.api.DisplayName(\"Then: Automat sagt alle\")\n" +
                "    @org.junit.jupiter.api.Order(5)\n" +
                "    void then_AutomatSagtTest() throws java.lang.Exception {\n" +
                "      then_AutomatSagt(\"alle\");\n" +
                "    }\n" +
                "\n" +
                "    @org.junit.jupiter.api.Test\n" +
                "    @org.junit.jupiter.api.DisplayName(\"Then: Es existieren 2 Cola im Automaten\")\n" +
                "    @org.junit.jupiter.api.Order(6)\n" +
                "    void then_EsExistierenColaImAutomatenTest() throws java.lang.Exception {\n" +
                "      then_EsExistierenColaImAutomaten(\"2\");\n" +
                "    }\n" +
                "  }\n" +
                "\n" +
                "  @org.junit.jupiter.api.Nested\n" +
                "  @org.junit.jupiter.api.DisplayName(\"Scenario: Getraenk vorhanden\")\n" +
                "  @org.junit.jupiter.api.TestMethodOrder(org.junit.jupiter.api.MethodOrderer.OrderAnnotation.class)\n" +
                "  class GetraenkVorhanden {\n" +
                "    @org.junit.jupiter.api.Test\n" +
                "    @org.junit.jupiter.api.DisplayName(\"Given: Ein Automat\")\n" +
                "    @org.junit.jupiter.api.Order(7)\n" +
                "    void given_EinAutomatTest() throws java.lang.Exception {\n" +
                "      given_EinAutomat();\n" +
                "    }\n" +
                "\n" +
                "    @org.junit.jupiter.api.Test\n" +
                "    @org.junit.jupiter.api.DisplayName(\"Given: Mit 2 Flaschen Cola\")\n" +
                "    @org.junit.jupiter.api.Order(8)\n" +
                "    void given_MitFlaschenColaTest() throws java.lang.Exception {\n" +
                "      given_MitFlaschenCola(\"2\");\n" +
                "    }\n" +
                "\n" +
                "    @org.junit.jupiter.api.Test\n" +
                "    @org.junit.jupiter.api.DisplayName(\"Given: Mit 0 Flaschen Sprite\")\n" +
                "    @org.junit.jupiter.api.Order(9)\n" +
                "    void given_MitFlaschenSpriteTest() throws java.lang.Exception {\n" +
                "      given_MitFlaschenSprite(\"0\");\n" +
                "    }\n" +
                "\n" +
                "    @org.junit.jupiter.api.Test\n" +
                "    @org.junit.jupiter.api.DisplayName(\"When: Kaufe 1 Cola\")\n" +
                "    @org.junit.jupiter.api.Order(10)\n" +
                "    void when_KaufeColaTest() throws java.lang.Exception {\n" +
                "      when_KaufeCola(\"1\");\n" +
                "    }\n" +
                "\n" +
                "    @org.junit.jupiter.api.Test\n" +
                "    @org.junit.jupiter.api.DisplayName(\"Then: Automat sagt ok\")\n" +
                "    @org.junit.jupiter.api.Order(11)\n" +
                "    void then_AutomatSagtTest() throws java.lang.Exception {\n" +
                "      then_AutomatSagt(\"ok\");\n" +
                "    }\n" +
                "\n" +
                "    @org.junit.jupiter.api.Test\n" +
                "    @org.junit.jupiter.api.DisplayName(\"Then: Es existieren 1 Cola im Automaten\")\n" +
                "    @org.junit.jupiter.api.Order(12)\n" +
                "    void then_EsExistierenColaImAutomatenTest() throws java.lang.Exception {\n" +
                "      then_EsExistierenColaImAutomaten(\"1\");\n" +
                "    }\n" +
                "  }\n" +
                "}");
        Assertions.assertThat(build.typeSpecs).hasSize(2);
    }
}
