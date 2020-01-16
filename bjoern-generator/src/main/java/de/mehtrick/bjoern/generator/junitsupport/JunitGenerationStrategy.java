package de.mehtrick.bjoern.generator.junitsupport;

import com.squareup.javapoet.TypeSpec;
import de.mehtrick.bjoern.parser.modell.Bjoern;

/**
 * <p></p>Strategy Pattern Interface for generating different junit-test-styles based on the used Junit version</p>
 *
 * @author mehtrick
 */
public interface JunitGenerationStrategy {

    void generateScenarios(Bjoern bjoern, TypeSpec.Builder featureClassBuilder);
}
