package de.mehtrick.bjoern.parser;

import de.mehtrick.bjoern.parser.validator.BjoernValidator;
import de.mehtrick.bjoern.parser.validator.BjoernValidatorException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BjoernValidatorTest {
    private BjoernValidator bjoernValidator;

    @BeforeEach
    public void setup() {
        bjoernValidator = new BjoernValidator();
    }


    @Test
    public void testInvalidKeyword() {
        //WHEN
        Assertions.assertThatExceptionOfType(BjoernValidatorException.class).isThrownBy(() -> {
                    bjoernValidator.validate("  WrongKeyword     \r\n      \r\n anotherWrongOne", "defaultpath");
                }
        ).withMessageContaining("ValidationError at line 1: The line starts with an invalid Keyword. Found \"  WrongKeyword     \". Allowed Keywords are: Given:,When:,Then:,Background:,Feature:,- Scenario:,Scenarios:,-. This check is case-sensitive!")
                .withMessageContaining("ValidationError at line 3: The line starts with an invalid Keyword. Found \" anotherWrongOne\". Allowed Keywords are: Given:,When:,Then:,Background:,Feature:,- Scenario:,Scenarios:,-. This check is case-sensitive!");
    }

    @Test
    public void testEmptyDocument() {
        bjoernValidator.validate("     \r\n      \r\n ", "defaultpath");

        //THEN
        //Nothing should happen because a File can be empty, but nothing will be generated.
    }


    @Test
    public void testWrongStartingKeyword() {
        //WHEN
        Assertions.assertThatExceptionOfType(BjoernValidatorException.class).isThrownBy(() -> {
                    bjoernValidator.validate("  FeATure     \r\n      \r\nScenarios:", "default");
                }
        ).withMessageContaining("ValidationError at line 1: A bjoern file must start with the Keyword \"Feature:\", but starts with \"  FeATure     \". Please remove any indentation. This check is case-sensitive!");
    }


    @Test
    public void wrongIndentationInBackground() {
        //when
        Assertions.assertThatExceptionOfType(BjoernValidatorException.class).isThrownBy(() -> {
                    bjoernValidator.validate("Feature: Test\r\nBackground:\r\n  Given:\r\n- A tree\r\n" +
                            " - A tree\r\n" +
                            "  - A tree\r\n" +
                            "    - A tree\r\n" +
                            "   - A tree", "defaultpath");
                }
        ).withMessageContaining("ValidationError at line 4: The keyword - must have an indentation of 4 but found 0")
                .withMessageContaining("ValidationError at line 5: The keyword - must have an indentation of 4 but found 1")
                .withMessageContaining("ValidationError at line 6: The keyword - must have an indentation of 4 but found 2")
                .withMessageContaining("ValidationError at line 8: The keyword - must have an indentation of 4 but found 3");
    }

    @Test
    public void wrongIndentationsInScenario() {
        String zgr = "Feature: Test eines Getraenkeautomaten\r\n" +
                "Background:\r\n" +
                "  Given: \r\n" +
                "    - Ein Automat\r\n" +
                "    - Ein KassenSystem\r\n" +
                "Scenarios: \r\n" +
                "  - Scenario: Getraenk nicht vorhanden\r\n" +
                "    Given: \r\n" +
                "       - Mit  Flaschen Cola\r\n" +
                "     - Mit  Flaschen Cola\r\n" +
                "      - Mit  Flaschen Sprite";

        //when
        Assertions.assertThatExceptionOfType(BjoernValidatorException.class).isThrownBy(() -> {
                    bjoernValidator.validate(zgr, "default");
                }
        ).withMessageContaining("ValidationError at line 9: The keyword - must have an indentation of 6 but found 7")
                .withMessageContaining("ValidationError at line 10: The keyword - must have an indentation of 6 but found 5");

    }


    @Test
    public void missingNames() {
        String zgr = "Feature:\r\n" +
                "Background:\r\n" +
                "  Given: \r\n" +
                "    - Ein Automat\r\n" +
                "    - Ein KassenSystem\r\n" +
                "Scenarios: \r\n" +
                "  - Scenario: \r\n" +
                "    Given: \r\n" +
                "      - Mit  Flaschen Sprite";

        //when
        Assertions.assertThatExceptionOfType(BjoernValidatorException.class).isThrownBy(() -> {
                    bjoernValidator.validate(zgr, "default");
                }
        ).withMessageContaining("ValidationError at line 1: The keyword needs a value e.g. \"Feature: This is a Feature\" or \"Scenario: This is a Scneario\"")
                .withMessageContaining("ValidationError at line 7: The keyword needs a value e.g. \"Feature: This is a Feature\" or \"Scenario: This is a Scneario\"");

    }

    @Test
    public void emptyStatement() {
        String zgr = "Feature: A Feature\r\n" +
                "Background:\r\n" +
                "  Given: \r\n" +
                "    - Ein Automat\r\n" +
                "    - \r\n" +
                "Scenarios: \r\n" +
                "  - Scenario: A Scenario\r\n" +
                "    Given: \r\n" +
                "      -";
        //when
        Assertions.assertThatExceptionOfType(BjoernValidatorException.class).isThrownBy(() -> {
                    bjoernValidator.validate(zgr, "default");
                }
        ).withMessageContaining("ValidationError at line 5: A statement shall not be empty")
                .withMessageContaining("ValidationError at line 9: A statement shall not be empty");
    }

    @Test
    public void correctsmaple() {
        String zgr = "Feature: Feature\r\n" +
                "Background:\r\n" +
                "  Given: \r\n" +
                "    - Ein Automat\r\n" +
                "    - Ein KassenSystem\r\n" +
                "Scenarios: \r\n" +
                "  - Scenario: Scenario \r\n" +
                "    Given: \r\n" +
                "      - Mit  Flaschen Sprite";


        //WHEN
        bjoernValidator.validate(zgr, "default");

        //THEN
        //No Error
    }
}
