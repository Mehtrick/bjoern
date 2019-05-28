package de.mehtrick.bjoern.parser.validator;

public class BjoernValidationsException extends Exception{

    private final BjoernValidationError bjoernValidationError;

    public BjoernValidationsException(int index, String errorText, String... stringforformat) {
        this.bjoernValidationError = createNewBjoernValidationError(index, errorText, stringforformat);
    }

    public BjoernValidationError getBjoernValidationError() {
        return bjoernValidationError;
    }

    private BjoernValidationError createNewBjoernValidationError(int index, String errorText, String... stringforformat) {
        String formattedErrorText = String.format(errorText, stringforformat);
        return new BjoernValidationError(index + 1, formattedErrorText);
    }
}
