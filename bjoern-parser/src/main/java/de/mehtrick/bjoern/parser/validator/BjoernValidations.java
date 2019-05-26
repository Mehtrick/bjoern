package de.mehtrick.bjoern.parser.validator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * @author mehtrick
 *
 * This class contains all validations for a bjoern file.
 */
public enum BjoernValidations {
    INVALID_KEYWORD("The line starts with an invalid Keyword. Found \"%s\". Allowed Keywords are: %s. This check is case-sensitive!") {
        @Override
        protected BjoernValidationError validate(String[] lines, int index) {
            String trimmedLine = getTrimmedLine(lines, index);
            boolean lineStartsWithKnownKeyword = BjoernKeywords.getKeywordValues().stream().anyMatch(trimmedLine::startsWith);
            if (!lineStartsWithKnownKeyword) {
                return createNewBjoernValidationError(index, errorText,lines[index], BjoernKeywords.getKeywordsAsSingleString());
            }
            return null;
        }
    },
    EMPTYLINES("") {
        @Override
        protected BjoernValidationError validate(String[] lines, int index) {
            return null;
        }
    }, WRONSTARTINGKEYWORD("") {
        @Override
        protected BjoernValidationError validate(String[] lines, int index) {
            return null;
        }
    }, WRONG_INDENTATION_BACKGROUND("") {
        @Override
        protected BjoernValidationError validate(String[] lines, int index) {
            return null;
        }
    }, WRONG_INDENTATION_SCENARIOS("") {
        @Override
        protected BjoernValidationError validate(String[] lines, int index) {
            return null;
        }
    }, WRONG_INDENTATION_SINGLE_SCENARIO("") {
        @Override
        protected BjoernValidationError validate(String[] lines, int index) {
            return null;
        }
    }, WRONG_INDENATION_IN_SCENARIO("") {
        @Override
        protected BjoernValidationError validate(String[] lines, int index) {
            return null;
        }
    }, MISSING_SCENARIO_NAME("") {
        @Override
        protected BjoernValidationError validate(String[] lines, int index) {
            return null;
        }
    };

    private static final Logger log = LoggerFactory.getLogger(BjoernValidations.class);

    final String errorText;

    BjoernValidations(String errorText) {
        this.errorText = errorText;
    }

    private static BjoernValidationError createNewBjoernValidationError(int index, String errorText, String... stringforformat) {
        String formattedErrorText = String.format(errorText, stringforformat);
        return new BjoernValidationError(index + 1, formattedErrorText);
    }

    /**
     * Validates a line of a bjoernspec. This method needs both the index and all of the lines, because validation can depend on previous lines
     *
     * @author mehtrick
     * @param lines
     * @param index
     * @param errors - containing the previous errors
     * @return a list of Error for that specific line
     */
    public List<BjoernValidationError> validateLine(String[] lines, int index, List<BjoernValidationError> errors){
        if(lineIsNotEmpty(lines[index])){
            BjoernValidationError bjoernValidationError = this.validate(lines, index);
            if(bjoernValidationError!=null){
                errors.add(bjoernValidationError);
            }
        }
        return errors;
    }

    private boolean lineIsNotEmpty(String line) {
        return line.trim().length()>0;
    }

    private static String getTrimmedLine(String[] lines, int index) {
        return lines[index].trim();
    }

    protected abstract BjoernValidationError validate(String[] lines, int index);

}
