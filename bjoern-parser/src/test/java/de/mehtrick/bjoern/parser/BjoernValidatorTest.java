package de.mehtrick.bjoern.parser;

import de.mehtrick.bjoern.parser.validator.BjoernValidator;
import de.mehtrick.bjoern.parser.validator.BjoernValidatorException;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class BjoernValidatorTest {
    @Rule
    public ExpectedException exception = ExpectedException.none();
    private BjoernValidator bjoernValidator;

    @Before
    public void setup() {
        bjoernValidator = new BjoernValidator();
    }


    @Test
    public void testInvalidKeyword() {
        //GIVEN THEN
        exception.expect(BjoernValidatorException.class);
        exception.expectMessage("\r\n\tValidationError at line 1: The line starts with an invalid Keyword. Found \"  WrongKeyword     \". Allowed Keywords are: Given:,When:,Then:,Background:,Feature:,- Scenario:,Scenarios:,-. This check is case-sensitive!");
        exception.expectMessage("\r\n\tValidationError at line 3: The line starts with an invalid Keyword. Found \" anotherWrongOne\". Allowed Keywords are: Given:,When:,Then:,Background:,Feature:,- Scenario:,Scenarios:,-. This check is case-sensitive!");
        //WHEN
        bjoernValidator.validate("  WrongKeyword     \r\n      \r\n anotherWrongOne", "defaultpath");
    }

    @Test
    public void testEmptyDocument() {
        bjoernValidator.validate("     \r\n      \r\n ", "defaultpath");

        //THEN
        //Nothing should happen because a File can be empty, but nothing will be generated.
    }

    @Test
    public void testWrongStartingKeyword() {
        //GIVEN THEN
        exception.expect(BjoernValidatorException.class);
        exception.expectMessage("\r\n\tValidationError at line 1: A bjoern file must start with the Keyword \"Feature:\", but starts with \"  FeATure     \". Please remove any indentation. This check is case-sensitive!");

        //WHEN
        bjoernValidator.validate("  FeATure     \r\n      \r\nScenarios:", "default");

    }

    @Test
    public void wrongIndentationInBackground() {
        //GIVEN THEN
        exception.expect(BjoernValidatorException.class);
        exception.expectMessage("ValidationError at line 4: The keyword - must have an indentation of 4 but found 0");
        exception.expectMessage("ValidationError at line 5: The keyword - must have an indentation of 4 but found 1");
        exception.expectMessage("ValidationError at line 6: The keyword - must have an indentation of 4 but found 2");
        exception.expectMessage("ValidationError at line 8: The keyword - must have an indentation of 4 but found 3");

        //WHEN
        bjoernValidator.validate("Feature: Test\r\nBackground:\r\n  Given:\r\n- A tree\r\n" +
                " - A tree\r\n" +
                "  - A tree\r\n" +
                "    - A tree\r\n" +
                "   - A tree", "defaultpath");
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

        exception.expect(BjoernValidatorException.class);
        exception.expectMessage("ValidationError at line 9: The keyword - must have an indentation of 6 but found 7");
        exception.expectMessage("ValidationError at line 10: The keyword - must have an indentation of 6 but found 5");

        //WHEN
        bjoernValidator.validate(zgr,"default");

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

        exception.expect(BjoernValidatorException.class);
        exception.expectMessage("ValidationError at line 1: The keyword needs a value e.g. \"Feature: This is a Feature\" or \"Scenario: This is a Scneario\"");
        exception.expectMessage("ValidationError at line 7: The keyword needs a value e.g. \"Feature: This is a Feature\" or \"Scenario: This is a Scneario\"");

        //WHEN
        bjoernValidator.validate(zgr,"default");
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

        exception.expect(BjoernValidatorException.class);
        exception.expectMessage("ValidationError at line 5: A statement shall not be empty");
        exception.expectMessage("ValidationError at line 9: A statement shall not be empty");

        //WHEN
        bjoernValidator.validate(zgr,"default");
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
        bjoernValidator.validate(zgr,"default");


        //THEN
        //No Error
    }
}
