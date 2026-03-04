package de.mehtrick.bjoern.parser.validator.validations;

import de.mehtrick.bjoern.parser.validator.BjoernValidationsException;

public class InvalidKeywordValidation extends AbstractValidation {
    public InvalidKeywordValidation(String errorText) {
        super(errorText);
    }

    @Override
    public void validate(String[] lines, int index) throws BjoernValidationsException {
        if (isInsideChangelogBlock(lines, index)) {
            return;
        }
        String trimmedLine = getTrimmedLine(lines, index);
        boolean lineDoesNotStartWithKnownKeyword = BjoernKeywords.getKeywordValues().stream().noneMatch(trimmedLine::startsWith);
        if (lineDoesNotStartWithKnownKeyword) {
            throw new BjoernValidationsException(index, errorText, lines[index], BjoernKeywords.getKeywordsAsSingleString());
        }
    }

    /**
     * Returns true when the current line is an indented continuation of a YAML block scalar Changelog value
     * (i.e. the Changelog key was followed by a block scalar indicator such as | or >).
     */
    private boolean isInsideChangelogBlock(String[] lines, int index) {
        if (lines[index].isEmpty() || !Character.isWhitespace(lines[index].charAt(0))) {
            return false;
        }
        for (int i = index - 1; i >= 0; i--) {
            if (lines[i].trim().isEmpty()) {
                continue;
            }
            if (!Character.isWhitespace(lines[i].charAt(0))) {
                // Found the last root-level non-empty line
                int colonIndex = lines[i].indexOf(':');
                if (colonIndex < 0) {
                    return false;
                }
                String afterKey = lines[i].substring(colonIndex + 1).trim();
                return lines[i].trim().startsWith(BjoernKeywords.CHANGELOG.keyword)
                        && (afterKey.startsWith("|") || afterKey.startsWith(">"));
            }
        }
        return false;
    }
}
