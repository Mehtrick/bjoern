package de.mehtrick.bjoern.parser.validator.validations;

import de.mehtrick.bjoern.parser.validator.BjoernValidationsException;

public class DeprecatedValidation extends AbstractValidation {

    private static final int EXPECTED_INDENTATION = 4;

    public DeprecatedValidation(String errorText) {
        super(errorText);
    }

    @Override
    public void validate(String[] lines, int index) throws BjoernValidationsException {
        String trimmedLine = getTrimmedLine(lines, index);
        if (!trimmedLine.startsWith(BjoernKeywords.DEPRECATED.keyword)) {
            return;
        }

        boolean scenariosSeen = false;
        for (int i = 0; i < index; i++) {
            if (getTrimmedLine(lines, i).startsWith(BjoernKeywords.SCENARIOS.keyword)) {
                scenariosSeen = true;
                break;
            }
        }
        if (!scenariosSeen) {
            throw new BjoernValidationsException(index, errorText,
                    "Deprecated: is only allowed inside a Scenarios block, but was found at root level");
        }

        if (!lines[index].startsWith(createEmptySpaces(EXPECTED_INDENTATION) + BjoernKeywords.DEPRECATED.keyword)) {
            int spacesCount = lines[index].indexOf(BjoernKeywords.DEPRECATED.keyword);
            throw new BjoernValidationsException(index, errorText,
                    "Deprecated: must be indented with " + EXPECTED_INDENTATION + " spaces but found " + spacesCount);
        }

        String afterColon = trimmedLine.substring(trimmedLine.indexOf(':') + 1).trim();
        if (afterColon.isEmpty()) {
            throw new BjoernValidationsException(index, errorText,
                    "Deprecated: requires a boolean value (true or false) but none was provided");
        }
        if (!"true".equals(afterColon) && !"false".equals(afterColon)) {
            throw new BjoernValidationsException(index, errorText,
                    "Deprecated: value must be true or false but found \"" + afterColon + "\"");
        }
    }
}
