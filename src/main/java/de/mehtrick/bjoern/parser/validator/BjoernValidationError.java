package de.mehtrick.bjoern.parser.validator;

public class BjoernValidationError {

    private final int line;
    private final String message;

    public BjoernValidationError(int line, String message) {
        this.line = line;
        this.message = message;
    }

    public int getLine() {
        return this.line;
    }

    public String getMessage() {
        return this.message;
    }

    public String toString() {
        return "BjoernValidationError(line=" + this.getLine() + ", message=" + this.getMessage() + ")";
    }
}
