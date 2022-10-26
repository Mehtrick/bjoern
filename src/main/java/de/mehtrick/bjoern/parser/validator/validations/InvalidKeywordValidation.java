package de.mehtrick.bjoern.parser.validator.validations;

import de.mehtrick.bjoern.parser.validator.BjoernValidationsException;

public class InvalidKeywordValidation extends AbstractValidation {
    public InvalidKeywordValidation(String errorText) {
        super(errorText);
    }

    @Override
    public void validate(String[] lines, int index) throws BjoernValidationsException {
        String trimmedLine = getTrimmedLine(lines, index);
        boolean lineDoesNotStartWithKnownKeyword = BjoernKeywords.getKeywordValues().stream().noneMatch(trimmedLine::startsWith);
        if (lineDoesNotStartWithKnownKeyword) {
            throw new BjoernValidationsException(index, errorText, lines[index], BjoernKeywords.getKeywordsAsSingleString());
        }
    }
}
