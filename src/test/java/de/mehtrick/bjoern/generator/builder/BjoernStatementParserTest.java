package de.mehtrick.bjoern.generator.builder;

import de.mehtrick.bjoern.parser.modell.BDDKeyword;
import de.mehtrick.bjoern.parser.modell.BjoernStatement;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class BjoernStatementParserTest {

    @Test
    void testWithStatementAndParameter() {
        //given
        BjoernStatement bjoernStatement = new BjoernStatement("ein haus mit \"1\" Katze", BDDKeyword.GIVEN);

        String methodCallOutOfStatemet = BjoernStatementParser.createMethodCallOutOfStatemet(bjoernStatement);
        Assertions.assertThat(methodCallOutOfStatemet).isEqualToIgnoringWhitespace("given_EinHausMitKatze(\"1\")");
    }

    @Test
    void testWithNoParameters() {
        //given
        BjoernStatement bjoernStatement = new BjoernStatement("ein haus mit Katze", BDDKeyword.THEN);

        String methodCallOutOfStatemet = BjoernStatementParser.createMethodCallOutOfStatemet(bjoernStatement);
        Assertions.assertThat(methodCallOutOfStatemet).isEqualToIgnoringWhitespace("then_EinHausMitKatze()");
    }

}
