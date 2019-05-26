package de.mehtrick.bjoern.parser;

import de.mehtrick.bjoern.parser.validator.BjoernValidator;
import de.mehtrick.bjoern.parser.validator.BjoernValidatorException;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class BjoernValidatorTest {
    private BjoernValidator bjoernValidator;

    @Rule public ExpectedException exception = ExpectedException.none();

    @Before
    public void setup() {
        bjoernValidator = new BjoernValidator();
    }


    @Test
    public void testInvalidKeyword() {
        //GIVEN THEN
        exception.expect(BjoernValidatorException.class);
        exception.expectMessage("\r\n\tValidationError at line 1 : The line starts with an invalid Keyword. Found \"  WrongKeyword     \". Allowed Keywords are: Given:,When:,Then:,Background:,Feature:,- Scenario:,Scenarios:,-. This check is case-sensitive!");
        exception.expectMessage("\r\n\tValidationError at line 3 : The line starts with an invalid Keyword. Found \" anotherWrongOne\". Allowed Keywords are: Given:,When:,Then:,Background:,Feature:,- Scenario:,Scenarios:,-. This check is case-sensitive!");
        //WHEN
        bjoernValidator.validate("  WrongKeyword     \r\n      \r\n anotherWrongOne", "defaultpath");
    }

    @Test
    public void testEmptyDocument() {
    }

    @Test
    public void testWrongStartingKeyword() {
    }

    @Test
    public void wrongIndentationInBackground() {

    }

    @Test
    public void wrongIndentationOfScenarios() {

    }

    @Test
    public void wrongIndentationOfScenario() {

    }

    @Test
    public void wrongIndentationOfGivenInBackground() {

    }

    @Test
    public void wrongIndentationOfGivenInScenario() {

    }

    @Test
    public void missingScenarioName() {

    }

    @Test
    public void correctsmaple() {

    }
}
