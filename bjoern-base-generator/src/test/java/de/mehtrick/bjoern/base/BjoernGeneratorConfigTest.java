package de.mehtrick.bjoern.base;

import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static java.nio.charset.StandardCharsets.UTF_16;
import static java.nio.charset.StandardCharsets.UTF_8;
import static org.junit.jupiter.api.Assertions.assertThrows;

class BjoernGeneratorConfigTest {

    @Test
    void emptyConfigException() {
        //given
        BjoernGeneratorConfig bjoernGeneratorConfig = new BjoernGeneratorConfig();
        //when //then
        assertThrows(BjoernMissingPropertyException.class, bjoernGeneratorConfig::validate);
    }

    @Test
    void fullConfigMapperTest() throws NotSupportedJunitVersionException, BjoernMissingPropertyException {
        //given
        BjoernGeneratorConfig bjoernGeneratorConfig = new BjoernGeneratorConfig(new String[]{"path=src/test/resources/specification/bjoern.zgr",
                "folder=src/test/resources/", "package=de.mehtrick.bjoern", "gendir=src/gen/java", "docdir=src/doc/resources", "docExtension=txt",
                "extendedTestclass=de.mehtrick.bjoern.AbstractTestclass", "junitVersion=4", "encoding=UTF-16", "template=bla.html", "templateFolder=src/test/templates", "someStupidProperty=fjsa"});
        //when
        bjoernGeneratorConfig.validate();
        // then
        Assertions.assertThat(bjoernGeneratorConfig.getDocdir()).isEqualTo("src/doc/resources");
        Assertions.assertThat(bjoernGeneratorConfig.getDocExtension()).isEqualTo("txt");
        Assertions.assertThat(bjoernGeneratorConfig.getEncoding()).isEqualByComparingTo(UTF_16);
        Assertions.assertThat(bjoernGeneratorConfig.getExtendedTestclass()).isEqualTo("de.mehtrick.bjoern.AbstractTestclass");
        Assertions.assertThat(bjoernGeneratorConfig.getFolder()).isEqualTo("src/test/resources/");
        Assertions.assertThat(bjoernGeneratorConfig.getGendir()).isEqualTo("src/gen/java");
        Assertions.assertThat(bjoernGeneratorConfig.getJunitVersion()).isEqualByComparingTo(BjoernGeneratorConfig.SupportedJunitVersion.junit4);
        Assertions.assertThat(bjoernGeneratorConfig.getPath()).isEqualTo("src/test/resources/specification/bjoern.zgr");
        Assertions.assertThat(bjoernGeneratorConfig.getPckg()).isEqualTo("de.mehtrick.bjoern");
        Assertions.assertThat(bjoernGeneratorConfig.getTemplate()).isEqualTo("bla.html");
        Assertions.assertThat(bjoernGeneratorConfig.getTemplateFolder()).isEqualTo("src/test/templates");
        Assertions.assertThat(bjoernGeneratorConfig.isFoldersSet()).isTrue();
    }

    @Test
    void minConfigMapperTest() throws NotSupportedJunitVersionException, BjoernMissingPropertyException {
        //given
        BjoernGeneratorConfig bjoernGeneratorConfig = new BjoernGeneratorConfig(new String[]{"path=src/test/resources/specification/bjoern.zgr"});
        //when
        bjoernGeneratorConfig.validate();
        // then
        Assertions.assertThat(bjoernGeneratorConfig.getDocdir()).isNullOrEmpty();
        Assertions.assertThat(bjoernGeneratorConfig.getDocExtension()).isEqualTo("adoc");
        Assertions.assertThat(bjoernGeneratorConfig.getEncoding()).isEqualByComparingTo(UTF_8);
        Assertions.assertThat(bjoernGeneratorConfig.getExtendedTestclass()).isNullOrEmpty();
        Assertions.assertThat(bjoernGeneratorConfig.getFolder()).isNullOrEmpty();
        Assertions.assertThat(bjoernGeneratorConfig.getGendir()).isNullOrEmpty();
        Assertions.assertThat(bjoernGeneratorConfig.getJunitVersion()).isEqualByComparingTo(BjoernGeneratorConfig.SupportedJunitVersion.junit4);
        Assertions.assertThat(bjoernGeneratorConfig.getPath()).isEqualTo("src/test/resources/specification/bjoern.zgr");
        Assertions.assertThat(bjoernGeneratorConfig.getPckg()).isNullOrEmpty();
        Assertions.assertThat(bjoernGeneratorConfig.getTemplate()).isEqualTo("/asciidoc.ftlh");
        Assertions.assertThat(bjoernGeneratorConfig.getTemplateFolder()).isNullOrEmpty();
        Assertions.assertThat(bjoernGeneratorConfig.isFoldersSet()).isFalse();
    }

    @Test
    void unsupportedJunitVersion() {
        //given
        assertThrows(NotSupportedJunitVersionException.class, () -> {
            new BjoernGeneratorConfig(new String[]{"path=src/test/resources/specification/bjoern.zgr", "junitVersion=a"});
        });
    }

    @Test
    void correctJunitClasses() {
        //given
        BjoernGeneratorConfig.SupportedJunitVersion[] supportedJunitVersions = BjoernGeneratorConfig.SupportedJunitVersion.values();
        //then
        Assertions.assertThat(BjoernGeneratorConfig.SupportedJunitVersion.junit4.getBeforeAnnotationClass()).isAnnotation().isEqualTo(Before.class);
        Assertions.assertThat(BjoernGeneratorConfig.SupportedJunitVersion.junit4.getTestAnnotationClass()).isAnnotation().isEqualTo(org.junit.Test.class);

        Assertions.assertThat(BjoernGeneratorConfig.SupportedJunitVersion.junit5.getBeforeAnnotationClass()).isAnnotation().isEqualTo(BeforeEach.class);
        Assertions.assertThat(BjoernGeneratorConfig.SupportedJunitVersion.junit5.getTestAnnotationClass()).isAnnotation().isEqualTo(Test.class);

    }
}
