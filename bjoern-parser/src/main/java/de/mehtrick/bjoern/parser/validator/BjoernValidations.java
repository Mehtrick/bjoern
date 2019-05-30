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
    }, WRONSTARTINGKEYWORD("A bjoern file must start with the Keyword \"" + BjoernKeywords.FEATURE.keyword + "\", but starts with \"%s\". Please remove any indentation. This check is case-sensitive!") {
        @Override
        protected void validate(String[] lines, int index) throws BjoernValidationsException {
            if (index == 0 && !lines[index].startsWith(BjoernKeywords.FEATURE.keyword)) {
                throw new BjoernValidationsException(index, errorText, lines[index]);
            }
        }
    }, WRONG_INDENTATION_BACKGROUND("The keyword %s must have an indentation of %s but found %s") {
        @Override
        protected void validate(String[] lines, int index) throws BjoernValidationsException {
            StringBuilder linesBeforeCurrentLine = new StringBuilder();
            for (int i = 0; i < index; i++) {
                linesBeforeCurrentLine.append(lines[i]);
            }
            if (linesBeforeCurrentLine.toString().contains(BjoernKeywords.BACKGROUND.keyword) && !linesBeforeCurrentLine.toString().contains(BjoernKeywords.SCENARIOS.keyword)) {
                BjoernValidations.checkIndentationOfStatement(errorText,4,lines,index);
                BjoernValidations.checkIndentationOfKeyWord(BjoernKeywords.GIVEN,errorText,2,lines,index);
            }
        }
    }, WRONG_INDENTATION_SCENARIOS("The keyword %s must have an indentation of %s but found %s") {
        @Override
        protected void validate(String[] lines, int index) throws BjoernValidationsException {
            BjoernValidations.checkIndentationOfKeyWord(BjoernKeywords.BACKGROUND,errorText,0,lines,index);
            BjoernValidations.checkIndentationOfKeyWord(BjoernKeywords.SCENARIOS,errorText,0,lines,index);
        }

    }, WRONG_INDENTATION_SINGLE_SCENARIO("") {
        @Override
        protected void validate(String[] lines, int index) throws BjoernValidationsException {
        }
    }, WRONG_INDENATION_IN_SCENARIO("The keyword %s must have an indentation of %s but found %s") {
        @Override
        protected void validate(String[] lines, int index) throws BjoernValidationsException {
            StringBuilder linesBeforeCurrentLine = new StringBuilder();
            for (int i = 0; i < index; i++) {
                linesBeforeCurrentLine.append(lines[i]);
            }
            if (linesBeforeCurrentLine.toString().contains(BjoernKeywords.SCENARIOS.keyword)) {
                BjoernValidations.checkIndentationOfStatement(errorText,6,lines,index);
                BjoernValidations.checkIndentationOfKeyWord(BjoernKeywords.GIVEN,errorText,4,lines,index);
                BjoernValidations.checkIndentationOfKeyWord(BjoernKeywords.WHEN,errorText,4,lines,index);
                BjoernValidations.checkIndentationOfKeyWord(BjoernKeywords.THEN,errorText,4,lines,index);
            }
        }
    }, MISSINGNAMES("The keyword needs a value e.g. \"Feature: This is a Feature\" or \"Scenario: This is a Scneario\"") {
        @Override
        protected void validate(String[] lines, int index) throws BjoernValidationsException {
            String trimmedLine = BjoernValidations.getTrimmedLine(lines, index);
            if(trimmedLine.startsWith(BjoernKeywords.SCENARIO.keyword)||trimmedLine.startsWith(BjoernKeywords.FEATURE.keyword)){
                if(trimmedLine.indexOf(":")+1 == trimmedLine.length()){
                    throw new BjoernValidationsException(index,errorText);
                }
            }


        }
    }, MISSING_STATEMENT_CONTENT("A statement shall not be empty") {
        @Override
        protected void validate(String[] lines, int index) throws BjoernValidationsException {
            String trimmedLine = BjoernValidations.getTrimmedLine(lines, index);
            if(trimmedLine.startsWith(BjoernKeywords.STATEMENT.keyword)&&!trimmedLine.startsWith(BjoernKeywords.SCENARIO.keyword)){
                if(trimmedLine.indexOf(BjoernKeywords.STATEMENT.keyword)+1 == trimmedLine.length()){
                    throw new BjoernValidationsException(index,errorText);
                }
            }
        }
    };

    private static final Logger log = LoggerFactory.getLogger(BjoernValidations.class);

    final String errorText;

    BjoernValidations(String errorText) {
        this.errorText = errorText;
    }

    private static String getTrimmedLine(String[] lines, int index) {
        return lines[index].trim();
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

    private static void checkIndentationOfKeyWord(BjoernKeywords bjoernKeyword,String errorText,int expectedSpaces, String[] lines, int index) throws BjoernValidationsException {
        if(getTrimmedLine(lines,index).startsWith(bjoernKeyword.keyword)&& bjoernKeyword != BjoernKeywords.STATEMENT){
            if(!lines[index].startsWith(createEmptySpaces(expectedSpaces)+bjoernKeyword.keyword)){
                int spacesCount = lines[index].indexOf(bjoernKeyword.keyword);
                throw new BjoernValidationsException(index,errorText,bjoernKeyword.keyword,expectedSpaces+"",String.valueOf(spacesCount));
            }
        }
    }

    private static void checkIndentationOfStatement(String errorText,int expectedSpaces, String[] lines, int index) throws BjoernValidationsException {
        String trimmedLine = getTrimmedLine(lines, index);
        if(trimmedLine.startsWith(BjoernKeywords.STATEMENT.keyword)&& !trimmedLine.startsWith(BjoernKeywords.SCENARIO.keyword)){
            if(!lines[index].startsWith(createEmptySpaces(expectedSpaces)+BjoernKeywords.STATEMENT.keyword)){
                int spacesCount = lines[index].indexOf(BjoernKeywords.STATEMENT.keyword);
                throw new BjoernValidationsException(index,errorText,BjoernKeywords.STATEMENT.keyword,expectedSpaces+"",String.valueOf(spacesCount));
            }
        }
    }

    private static String createEmptySpaces(int count){
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < count; i++) {
            builder.append(" ");
        }
        return builder.toString();
    }

    protected abstract void validate(String[] lines, int index) throws BjoernValidationsException;

}
