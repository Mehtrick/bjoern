package de.mehtrick.bjoern.generator;

import de.mehtrick.bjoern.base.BjoernMissingPropertyException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

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
    }

}
