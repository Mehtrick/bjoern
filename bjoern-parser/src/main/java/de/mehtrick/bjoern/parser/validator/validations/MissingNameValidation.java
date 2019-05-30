package de.mehtrick.bjoern.parser.validator.validations;

import de.mehtrick.bjoern.parser.validator.BjoernValidationsException;

public class MissingNameValidation extends AbstractValidation {
    public MissingNameValidation(String errorText) {
        super(errorText);
    }

    @Override
    public void validate(String[] lines, int index) throws BjoernValidationsException {
        String trimmedLine = getTrimmedLine(lines, index);
        if(trimmedLine.startsWith(BjoernKeywords.SCENARIO.keyword)||trimmedLine.startsWith(BjoernKeywords.FEATURE.keyword)){
            if(trimmedLine.indexOf(":")+1 == trimmedLine.length()){
                throw new BjoernValidationsException(index,errorText);
            }
        }
    }
}
