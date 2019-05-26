package de.mehtrick.bjoern.parser.validator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public enum BjoernValidations {


    INVALID_KEYWORD("The line starts with an invalid Keyword. Found \"%s\". Allowed Keywords are: %s. This check is case-sensitive!") {
        @Override
        List<BjoernValidationError> validate(String[] lines, int index, List<BjoernValidationError> errors) {
            String trimmedLine = getTrimmedLine(lines, index);
            boolean lineStartsWithKnownKeyword = BjoernKeywords.getKeywordValues().stream().anyMatch(trimmedLine::startsWith);
            if (trimmedLine.length()>0 && !lineStartsWithKnownKeyword) {
                errors.add(createNewBjoernValidationError(lines, index, errorText,lines[index], BjoernKeywords.getKeywordsAsSingleString()));
            }
            return errors;
        }
    },
    EMPTYLINES("") {
        @Override
        List<BjoernValidationError> validate(String[] lines, int index, List<BjoernValidationError> errors) {
            return errors;
        }
    }, WRONSTARTINGKEYWORD("") {
        @Override
        List<BjoernValidationError> validate(String[] lines, int index, List<BjoernValidationError> errors) {
            return errors;
        }
    }, WRONG_INDENTATION_BACKGROUND("") {
        @Override
        List<BjoernValidationError> validate(String[] lines, int index, List<BjoernValidationError> errors) {
            return errors;
        }
    }, WRONG_INDENTATION_SCENARIOS("") {
        @Override
        List<BjoernValidationError> validate(String[] lines, int index, List<BjoernValidationError> errors) {
            return errors;
        }
    }, WRONG_INDENTATION_SINGLE_SCENARIO("") {
        @Override
        List<BjoernValidationError> validate(String[] lines, int index, List<BjoernValidationError> errors) {
            return errors;
        }
    }, WRONG_INDENATION_IN_SCENARIO("") {
        @Override
        List<BjoernValidationError> validate(String[] lines, int index, List<BjoernValidationError> errors) {
            return errors;
        }
    }, MISSING_SCENARIO_NAME("") {
        @Override
        List<BjoernValidationError> validate(String[] lines, int index, List<BjoernValidationError> errors) {
            return errors;
        }
    };

    private static final Logger log = LoggerFactory.getLogger(BjoernValidations.class);

    final String errorText;

    BjoernValidations(String errorText) {
        this.errorText = errorText;
    }

    private static BjoernValidationError createNewBjoernValidationError(String[] lines, int index, String errorText, String... stringforformat) {
        String formattedErrorText = String.format(errorText, stringforformat);
        return new BjoernValidationError(index + 1, formattedErrorText);
    }

    private static String getTrimmedLine(String[] lines, int index) {
        return lines[index].trim();
    }

    abstract List<BjoernValidationError> validate(String[] lines, int index, List<BjoernValidationError> errors);

}
