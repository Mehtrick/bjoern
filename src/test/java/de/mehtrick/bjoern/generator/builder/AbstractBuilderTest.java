package de.mehtrick.bjoern.generator.builder;

import de.mehtrick.bjoern.generator.BjoernCodeGeneratorConfig;
import de.mehtrick.bjoern.parser.BjoernParser;
import de.mehtrick.bjoern.parser.modell.Bjoern;
import org.junit.jupiter.api.BeforeEach;

import static java.nio.charset.StandardCharsets.UTF_8;

public abstract class AbstractBuilderTest {

    Bjoern bjoern;
    BjoernCodeGeneratorConfig bjoernCodeGeneratorConfig;

    @BeforeEach
    public void setup() {
        bjoern = getBjoern("src/test/resources/test.zgr");
        bjoernCodeGeneratorConfig = new BjoernCodeGeneratorConfig();
        bjoernCodeGeneratorConfig.setPckg("de.test");
    }

    protected Bjoern getBjoern(String path) {
        return new BjoernParser().parseSpec(path, UTF_8);
    }
}
