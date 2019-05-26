package de.mehtrick.bjoern.parser.validator;

import java.util.List;
import java.util.stream.Collectors;

public class BjoernValidatorException extends RuntimeException {

    private static final String errorTemplate = "ValidationError at line %d : %s";

    public BjoernValidatorException(List<BjoernValidationError> errors) {
        super(System.lineSeparator()+"\t"+errors.stream().map(e -> String.format(errorTemplate, e.getLine(), e.getMessage())).collect(Collectors.joining(System.lineSeparator()+"\t")));
    }
}
