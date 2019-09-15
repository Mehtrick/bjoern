package de.mehtrick.bjoern.parser.validator.validations;

import de.mehtrick.bjoern.parser.validator.BjoernValidationError;
import de.mehtrick.bjoern.parser.validator.BjoernValidationsException;

import java.util.List;

/**
 * @author mehtrick
 * <p>
 * This class contains all validations for a bjoern file.
 */
public enum BjoernValidations {
    INVALID_KEYWORD("The line starts with an invalid Keyword. Found \"%s\". Allowed Keywords are: %s. This check is case-sensitive!") {
        @Override
        protected void validate(String[] lines, int index) throws BjoernValidationsException {
            new InvalidKeywordValidation(errorText).validate(lines,index);
        }
    }, WRONSTARTINGKEYWORD("A bjoern file must start with the Keyword \"" + BjoernKeywords.FEATURE.keyword + "\", but starts with \"%s\". Please remove any indentation. This check is case-sensitive!") {
        @Override
        protected void validate(String[] lines, int index) throws BjoernValidationsException {
            new FeatureKeywordValidation(errorText).validate(lines,index);
        }
    }, WRONG_INDENTATION_BACKGROUND("The keyword %s must have an indentation of %s but found %s") {
        @Override
        protected void validate(String[] lines, int index) throws BjoernValidationsException {
            new BackgroundIndentationValidation(errorText).validate(lines,index);
        }
    }, WRONG_INDENTATION_SCENARIOS("The keyword %s must have an indentation of %s but found %s") {
        @Override
        protected void validate(String[] lines, int index) throws BjoernValidationsException {
            new ScenariosIndentationValidation(errorText).validate(lines,index);
        }
    }, MISSINGNAMES("The keyword needs a value e.g. \"Feature: This is a Feature\" or \"Scenario: This is a Scneario\"") {
        @Override
        protected void validate(String[] lines, int index) throws BjoernValidationsException {
            new MissingNameValidation(errorText).validate(lines,index);
        }
    }, MISSING_STATEMENT_CONTENT("A statement shall not be empty") {
        @Override
        protected void validate(String[] lines, int index) throws BjoernValidationsException {
            new MissingStatementContent(errorText).validate(lines,index);
        }
    };

    final String errorText;

    BjoernValidations(String errorText) {
        this.errorText = errorText;
    }
    /**
     * Validates a line of a bjoernspec. This method needs both the index and all of the lines, because validation can depend on previous lines
     *
     * @param lines All lines of the spec
     * @param index to run over every line of the spec
     * @param errors - containing the previous errors
     * @return a list of Error for that specific line
     * @author mehtrick
     */
    public List<BjoernValidationError> validateLine(String[] lines, int index, List<BjoernValidationError> errors) {
        try {
            if (lineIsNotEmpty(lines, index)) {
                this.validate(lines, index);
            }
        } catch (BjoernValidationsException e) {
            errors.add(e.getBjoernValidationError());
        }
        return errors;
    }

    private boolean lineIsNotEmpty(String[] line, int index) {
        return line[index].trim().length() > 0;
    }

    protected abstract void validate(String[] lines, int index) throws BjoernValidationsException;

}
