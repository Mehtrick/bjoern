package de.mehtrick.bjoern.parser.validator.validations;

import de.mehtrick.bjoern.parser.validator.BjoernValidationsException;

public class MissingStatementContent extends AbstractValidation {
    public MissingStatementContent(String errorText) {
        super(errorText);
    }

    @Override
    public void validate(String[] lines, int index) throws BjoernValidationsException {
        String trimmedLine = getTrimmedLine(lines, index);
        if(trimmedLine.startsWith(BjoernKeywords.STATEMENT.keyword)&&!trimmedLine.startsWith(BjoernKeywords.SCENARIO.keyword)){
            if(trimmedLine.indexOf(BjoernKeywords.STATEMENT.keyword)+1 == trimmedLine.length()){
                throw new BjoernValidationsException(index,errorText);
            }
        }
    }
}
