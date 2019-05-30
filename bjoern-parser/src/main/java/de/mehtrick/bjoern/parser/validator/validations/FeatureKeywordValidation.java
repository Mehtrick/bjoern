package de.mehtrick.bjoern.parser.validator.validations;

import de.mehtrick.bjoern.parser.validator.BjoernValidationsException;

public class FeatureKeywordValidation extends AbstractValidation {
    public FeatureKeywordValidation(String errorText) {
        super(errorText);
    }

    @Override
    public void validate(String[] lines, int index) throws BjoernValidationsException {
        if (index == 0 && !lines[index].startsWith(BjoernKeywords.FEATURE.keyword)) {
            throw new BjoernValidationsException(index, errorText, lines[index]);
        }
    }
}
