package de.mehtrick.bjoern.parser.validator;

import de.mehtrick.bjoern.parser.validator.validations.BjoernValidations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class BjoernValidator {
    private static final Logger log = LoggerFactory.getLogger(BjoernValidator.class);

    public void validate(String bjoernSpec, String path) {
        String[] lines = splitBjoernSpecIntoLines(bjoernSpec);
        checkIfDocumentIsEmpty(lines,path);
        List<BjoernValidationError> validationErrors = new ArrayList<>();
        validationErrors = validateLines(lines, validationErrors);
        checkIfValidationErrorsHaveBeenDetected(validationErrors);
    }

    private void checkIfValidationErrorsHaveBeenDetected(List<BjoernValidationError> validationErrors) {
        if (!validationErrors.isEmpty()) {
            throw new BjoernValidatorException(validationErrors);
        }
    }

    private List<BjoernValidationError> validateLines(String[] lines, List<BjoernValidationError> validationErrors) {
        for (int i = 0; i < lines.length; i++) {
            for (BjoernValidations bjoernValidation : BjoernValidations.values()){
                validationErrors = bjoernValidation.validateLine(lines,i,validationErrors);
            }
        }
        return validationErrors;
    }

    private void checkIfDocumentIsEmpty(String[] lines, String path) {
        if (lines== null || lines.length == 0){
            log.warn(String.format("The file at %s is empty, so nothing will be generated!",path));
        }
    }

    private String[] splitBjoernSpecIntoLines(String bjoernSpec) {
        return bjoernSpec.split("\\r?\\n");
    }





}
