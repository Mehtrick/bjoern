package de.mehtrick.bjoern.generator;

import de.mehtrick.bjoern.base.BjoernMissingPropertyException;
import de.mehtrick.bjoern.generator.junitsupport.NotSupportedJunitVersionException;
import de.mehtrick.bjoern.generator.junitsupport.SupportedJunitVersion;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

class BjoernCodeGeneratorConfigTest {

    @Test
    void testCodeGeneratorValidation() {
        //when //then
        Assertions.assertThatExceptionOfType(BjoernMissingPropertyException.class).isThrownBy(() -> {
            BjoernCodeGeneratorConfig bjoernGeneratorConfig = new BjoernCodeGeneratorConfig();
            bjoernGeneratorConfig.setFolder("bla");
            bjoernGeneratorConfig.validate();
        }).withMessageContaining("Please configure the package name by setting the \"pckg\" property");

        Assertions.assertThatExceptionOfType(BjoernMissingPropertyException.class).isThrownBy(() -> {
            BjoernCodeGeneratorConfig bjoernGeneratorConfig = new BjoernCodeGeneratorConfig();
            bjoernGeneratorConfig.setFolder("bla");
            bjoernGeneratorConfig.setPckg("de.test");
            bjoernGeneratorConfig.validate();
        }).withMessageContaining("Please configure the gendir where the classes will be generated");
    }

    @Test
    void correctValidation() throws BjoernMissingPropertyException {
        BjoernCodeGeneratorConfig bjoernGeneratorConfig = new BjoernCodeGeneratorConfig();
        bjoernGeneratorConfig.setFolder("bla");
        bjoernGeneratorConfig.setGendir("blub");
        bjoernGeneratorConfig.setPckg("de.test");
        bjoernGeneratorConfig.validate();

        Assertions.assertThat(bjoernGeneratorConfig.getJunitVersion()).isEqualByComparingTo(SupportedJunitVersion.junit4);
    }

    @Test
    void unsupportedJunitVersion() {
        //given
        assertThrows(NotSupportedJunitVersionException.class, () -> {
            new BjoernCodeGeneratorConfig(new String[]{"path=src/test/resources/specification/bjoern.zgr", "junitVersion=a"});
        });
    }

    @Test
    void correctJunitClasses() {
        //given
        SupportedJunitVersion[] supportedJunitVersions = SupportedJunitVersion.values();
        //then
        Assertions.assertThat(SupportedJunitVersion.junit4.getBeforeAnnotationClass()).isAnnotation().isEqualTo(Before.class);
        Assertions.assertThat(SupportedJunitVersion.junit4.getTestAnnotationClass()).isAnnotation().isEqualTo(org.junit.Test.class);

        Assertions.assertThat(SupportedJunitVersion.junit5.getBeforeAnnotationClass()).isAnnotation().isEqualTo(BeforeEach.class);
        Assertions.assertThat(SupportedJunitVersion.junit5.getTestAnnotationClass()).isAnnotation().isEqualTo(Test.class);

    }

}
