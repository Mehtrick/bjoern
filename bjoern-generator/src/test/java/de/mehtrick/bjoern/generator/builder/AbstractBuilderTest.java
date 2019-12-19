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
        bjoern = new BjoernParser().parseSpec("src/test/resources/test.zgr", UTF_8);
        bjoernCodeGeneratorConfig = new BjoernCodeGeneratorConfig();

    }
}
