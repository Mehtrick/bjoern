package de.mehtrick.bjoern.parser.validator.validations;

import de.mehtrick.bjoern.parser.validator.BjoernValidationsException;

public class ScenariosIndentationValidation extends AbstractValidation {
    public ScenariosIndentationValidation(String errorText) {
        super(errorText);
    }

    @Override
    public void validate(String[] lines, int index) throws BjoernValidationsException {
        checkIndentationOfKeyWord(BjoernKeywords.SCENARIOS,errorText,0,lines,index);
        StringBuilder linesBeforeCurrentLine = new StringBuilder();
        for (int i = 0; i < index; i++) {
            linesBeforeCurrentLine.append(lines[i]);
        }
        if (linesBeforeCurrentLine.toString().contains(BjoernKeywords.SCENARIOS.keyword)) {
            checkIndentationOfStatement(errorText,6,lines,index);
            checkIndentationOfKeyWord(BjoernKeywords.GIVEN,errorText,4,lines,index);
            checkIndentationOfKeyWord(BjoernKeywords.WHEN,errorText,4,lines,index);
            checkIndentationOfKeyWord(BjoernKeywords.THEN,errorText,4,lines,index);
        }
    }
}
