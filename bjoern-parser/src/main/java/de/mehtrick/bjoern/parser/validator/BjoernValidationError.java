package de.mehtrick.bjoern.parser.validator;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BjoernValidationError {

    private final int line;
    private final String message;
}
