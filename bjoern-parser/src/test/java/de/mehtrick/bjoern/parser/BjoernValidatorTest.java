package de.mehtrick.bjoern.parser;

import org.junit.Before;
import org.junit.Test;

public class BjoernValidatorTest {
    private BjoernValidator bjoernValidator;

    @Before
    public void setup() {
        bjoernValidator = new BjoernValidator();
    }

    @Test
    public void testEmptyLines() {
        //WHEN
        bjoernValidator.validate("       \r\n      ");

        //THEN
        //2 leere Zeilen sind angegeben
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
