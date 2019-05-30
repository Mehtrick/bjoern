package de.mehtrick.bjoern.parser.validator.validations;

import de.mehtrick.bjoern.parser.validator.BjoernValidationsException;

public abstract class AbstractValidation {

    protected String errorText;

    public AbstractValidation(String errorText) {
        this.errorText = errorText;
    }

    public abstract void validate(String[] lines, int index) throws BjoernValidationsException;

    protected String getTrimmedLine(String[] lines, int index) {
        return lines[index].trim();
    }

    protected void checkIndentationOfKeyWord(BjoernKeywords bjoernKeyword, String errorText, int expectedSpaces, String[] lines, int index) throws BjoernValidationsException {
        if(getTrimmedLine(lines,index).startsWith(bjoernKeyword.keyword)&& bjoernKeyword != BjoernKeywords.STATEMENT){
            if(!lines[index].startsWith(createEmptySpaces(expectedSpaces)+bjoernKeyword.keyword)){
                int spacesCount = lines[index].indexOf(bjoernKeyword.keyword);
                throw new BjoernValidationsException(index,errorText,bjoernKeyword.keyword,expectedSpaces+"",String.valueOf(spacesCount));
            }
        }
    }

    protected void checkIndentationOfStatement(String errorText,int expectedSpaces, String[] lines, int index) throws BjoernValidationsException {
        String trimmedLine = getTrimmedLine(lines, index);
        if(trimmedLine.startsWith(BjoernKeywords.STATEMENT.keyword)&& !trimmedLine.startsWith(BjoernKeywords.SCENARIO.keyword)){
            if(!lines[index].startsWith(createEmptySpaces(expectedSpaces)+BjoernKeywords.STATEMENT.keyword)){
                int spacesCount = lines[index].indexOf(BjoernKeywords.STATEMENT.keyword);
                throw new BjoernValidationsException(index,errorText,BjoernKeywords.STATEMENT.keyword,expectedSpaces+"",String.valueOf(spacesCount));
            }
        }
    }

    protected String createEmptySpaces(int count){
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < count; i++) {
            builder.append(" ");
        }
        return builder.toString();
    }
}
