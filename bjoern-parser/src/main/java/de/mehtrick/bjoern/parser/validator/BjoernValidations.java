package de.mehtrick.bjoern.parser.validator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
            String trimmedLine = getTrimmedLine(lines, index);
            boolean lineDoesNotStartWithKnownKeyword = BjoernKeywords.getKeywordValues().stream().noneMatch(trimmedLine::startsWith);
            if (lineDoesNotStartWithKnownKeyword) {
                throw new BjoernValidationsException(index, errorText, lines[index], BjoernKeywords.getKeywordsAsSingleString());
            }
        }
    },
    EMPTYLINES("") {
        @Override
        protected void validate(String[] lines, int index) throws BjoernValidationsException {
        }
    }, WRONSTARTINGKEYWORD("") {
        @Override
        protected void validate(String[] lines, int index) throws BjoernValidationsException {
        }
    }, WRONG_INDENTATION_BACKGROUND("") {
        @Override
        protected void validate(String[] lines, int index) throws BjoernValidationsException {
        }
    }, WRONG_INDENTATION_SCENARIOS("") {
        @Override
        protected void validate(String[] lines, int index) throws BjoernValidationsException {
        }
    }, WRONG_INDENTATION_SINGLE_SCENARIO("") {
        @Override
        protected void validate(String[] lines, int index) throws BjoernValidationsException {
        }
    }, WRONG_INDENATION_IN_SCENARIO("") {
        @Override
        protected void validate(String[] lines, int index) throws BjoernValidationsException {
        }
    }, MISSING_SCENARIO_NAME("") {
        @Override
        protected void validate(String[] lines, int index) throws BjoernValidationsException {
        }
    };

    private static final Logger log = LoggerFactory.getLogger(BjoernValidations.class);

    final String errorText;

    BjoernValidations(String errorText) {
        this.errorText = errorText;
    }


    /**
     * Validates a line of a bjoernspec. This method needs both the index and all of the lines, because validation can depend on previous lines
     *
     * @param lines
     * @param index
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
        return getTrimmedLine(line, index).length() > 0;
    }

    private static String getTrimmedLine(String[] lines, int index) {
        return lines[index].trim();
    }

    protected abstract void validate(String[] lines, int index) throws BjoernValidationsException;

}
